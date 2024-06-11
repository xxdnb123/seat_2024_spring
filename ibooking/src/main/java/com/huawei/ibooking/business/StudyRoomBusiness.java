/**
 * @Author chensiyuan
 * @ClassName:StudyRoomBusiness
 * @Email: trirawchan@gmail.com
 * @Description:
 * @Date: 2023/5/29 14:29
 * @Version: v1.0
 */
package com.huawei.ibooking.business;

import com.huawei.ibooking.Constants;
import com.huawei.ibooking.dao.ReservationRecordDao;
import com.huawei.ibooking.dao.StudentDao;
import com.huawei.ibooking.dao.StudyRoomDao;
import com.huawei.ibooking.model.*;
import com.huawei.ibooking.model.dto.StudyRoomDetailQueryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class StudyRoomBusiness {

    @Autowired
    private StudentDao studentDao;
    @Autowired
    private ReservationRecordDao reservationRecordDao;

    @Autowired
    private StudyRoomDao studyRoomDao;

    public List<Map<String, String>> studyRoomList(StudyRoomDO studyRoomDO) {
        List<StudyRoomDO> studyRoomDOS = studyRoomDao.findStudyRoomInfo(studyRoomDO);
        if (studyRoomDOS.isEmpty()) {
            return null;
        }
        List<Map<String, String>> res = new ArrayList<Map<String, String>>();
        for (StudyRoomDO studyRoom : studyRoomDOS) {
            Map<String, String> map = new HashMap<>();
            map.put("id", String.valueOf(studyRoom.getId()));
            map.put("campus", String.valueOf(studyRoom.getCampusId()));
            map.put("region", String.valueOf(studyRoom.getRegionId()));
            map.put("building", String.valueOf(studyRoom.getBuildingId()));
            map.put("number", String.valueOf(studyRoom.getNumber()));
            map.put("status", String.valueOf(studyRoom.getStatus()));
            map.put("openTime", studyRoom.getOpenTime().toString());
            map.put("closeTime", studyRoom.getCloseTime().toString());
            map.put("availableSeatNumber", String.valueOf(studyRoom.getAvailableSeatNumber()));
            res.add(map);
        }

        return res;
    }

    public int updateStudyRoom(StudyRoomDO studyRoomDO){
        return studyRoomDao.updateStudyRoom(studyRoomDO);
    }

    public int newStudyRoom(StudyRoomDO studyRoomDO){
        List<Map<String, String>> seatsInfo = studyRoomDO.getSeatsInfo();
        if(seatsInfo==null){
            return -1;
        }

        StudyRoomDO duplicate_check = new StudyRoomDO();
        duplicate_check.setCampusId(studyRoomDO.getCampusId());
        duplicate_check.setBuildingId(studyRoomDO.getBuildingId());
        duplicate_check.setRegionId(studyRoomDO.getRegionId());
        duplicate_check.setNumber(studyRoomDO.getNumber());
        List<StudyRoomDO> studyRoomInfo = studyRoomDao.findStudyRoomInfo(duplicate_check);
        if(!(studyRoomInfo==null || studyRoomInfo.size()==0)){
            return -2;
        }

        studyRoomDO.setStatus(Constants.StudyRoomStatus.STATUS_OK);
        studyRoomDO.setAvailableSeatNumber(seatsInfo.size());
        int succ = studyRoomDao.insertStudyRoom(studyRoomDO);
        if (succ < 1 ){ return succ;}
        Integer id = studyRoomDO.getId();
        for (Map<String, String> seatMap : seatsInfo) {
            SeatDO seatDO = new SeatDO();
            seatDO.setStudyRoomId(id);
            seatDO.setSeatColumn(Integer.parseInt(seatMap.get("column")));
            seatDO.setSeatRow(Integer.parseInt(seatMap.get("row")));
            seatDO.setHasSocket(Boolean.parseBoolean(seatMap.get("hasSocket")));
            seatDO.setStatus(Constants.SeatStatus.STATUS_OK);
            studyRoomDao.insertSeat(seatDO);
        }
        return 1;
    }

    public StudyRoomDO getByID(Integer id){
        return studyRoomDao.getStudyRoomById(id);
    }

    public List<Map<String, String>> getSeatDOByStudyRoomID(Integer id){
        SeatDO seatDO = new SeatDO();
        seatDO.setStudyRoomId(id);
        List<SeatDO> seatInfo = studyRoomDao.findSeatInfo(seatDO);
        List<Map<String, String>> res = new ArrayList<Map<String, String>>();
        for (SeatDO curr_seatDO : seatInfo) {
            Map<String, String> map = new HashMap<>();
            map.put("id",String.valueOf(curr_seatDO.getId()));
            map.put("column", String.valueOf(curr_seatDO.getSeatColumn()));
            map.put("row", String.valueOf(curr_seatDO.getSeatRow()));
            map.put("hasSocket", String.valueOf(curr_seatDO.getHasSocket()));
            map.put("status", String.valueOf(curr_seatDO.getStatus()));
            res.add(map);
        }
        return res;
    }

    public List<Map<String, String>> getSeatDOWithReservationFiltered(StudyRoomDetailQueryDTO studyRoomDetailQueryDTO){
        SeatDO seatDO = new SeatDO();
        seatDO.setStudyRoomId(studyRoomDetailQueryDTO.getId());
        List<SeatDO> seatInfo = studyRoomDao.findSeatInfo(seatDO);

        ReservationRecordDO reservationRecordDO = new ReservationRecordDO();
        reservationRecordDO.setStudyRoomId(studyRoomDetailQueryDTO.getId());
        reservationRecordDO.setStartTime(studyRoomDetailQueryDTO.getOpenTime());
        reservationRecordDO.setEndTime(studyRoomDetailQueryDTO.getCloseTime());

        List<ReservationRecordDO> reservationRecordDOS = reservationRecordDao.searchOverLapReservationRecords(reservationRecordDO);
        HashMap<Integer, Boolean> id2status = new HashMap<Integer, Boolean>();
        for (ReservationRecordDO recordDO : reservationRecordDOS) {
            if  ((recordDO.getStatus() == Constants.RRStatus.CONFIRMED) || (recordDO.getStatus() == Constants.RRStatus.USING)){
                id2status.put(recordDO.getSeatId(),Boolean.FALSE);
            }
        }
        List<Map<String, String>> res = new ArrayList<Map<String, String>>();
        for (SeatDO curr_seatDO : seatInfo) {
            Map<String, String> map = new HashMap<>();
            map.put("id",String.valueOf(curr_seatDO.getId()));
            map.put("column", String.valueOf(curr_seatDO.getSeatColumn()));
            map.put("row", String.valueOf(curr_seatDO.getSeatRow()));
            map.put("hasSocket", String.valueOf(curr_seatDO.getHasSocket()));

            Boolean aBoolean = id2status.get(curr_seatDO.getId());
            if (aBoolean==null){
                map.put("status", String.valueOf(curr_seatDO.getStatus()));
            }else{
                map.put("status", String.valueOf(Constants.SeatStatus.STATUS_NA));
            }

            res.add(map);
        }
        return res;
    }
}
