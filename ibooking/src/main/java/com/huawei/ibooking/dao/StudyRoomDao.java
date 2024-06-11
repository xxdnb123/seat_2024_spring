/**
 * @Author chensiyuan
 * @ClassName:StudyRoomDao
 * @Email: trirawchan@gmail.com
 * @Description:
 * @Date: 2023/5/29 14:41
 * @Version: v1.0
 */
package com.huawei.ibooking.dao;

import com.huawei.ibooking.mapper.SeatMapper;
import com.huawei.ibooking.mapper.StudyRoomMapper;
import com.huawei.ibooking.model.SeatDO;
import com.huawei.ibooking.model.StudyRoomDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StudyRoomDao{
    @Autowired
    private  StudyRoomMapper studyRoomMapper;

    @Autowired
    private SeatMapper seatMapper;

    public int insertStudyRoom(StudyRoomDO studyRoom) {
        return studyRoomMapper.insertStudyRoom(studyRoom);
    }

    public StudyRoomDO getStudyRoomById(int id) {
        return studyRoomMapper.getStudyRoomById(id);
    }

    public int updateStudyRoom(StudyRoomDO studyRoom) {
        return studyRoomMapper.updateStudyRoom(studyRoom);
    }

    public int deleteStudyRoom(int id) {
        return studyRoomMapper.deleteStudyRoom(id);
    }

    public List<StudyRoomDO> findStudyRoomInfo(StudyRoomDO studyRoom) {
        return studyRoomMapper.findStudyRoomInfo(studyRoom);
    }

    public int insertSeat(SeatDO seat) {
        return seatMapper.insertSeat(seat);
    }

    public SeatDO getSeatById(int id) {
        return seatMapper.getSeatById(id);
    }

    public int updateSeat(SeatDO seat) {
        return seatMapper.updateSeat(seat);
    }

    public int deleteSeat(int id) {
        return seatMapper.deleteSeat(id);
    }

    public List<SeatDO> findSeatInfo(SeatDO seat) {
        return seatMapper.findSeatInfo(seat);
    }


}