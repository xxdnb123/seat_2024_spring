/**
 * @Author chensiyuan
 * @ClassName:ReservationDao
 * @Email: trirawchan@gmail.com
 * @Description:
 * @Date: 2023/6/4 20:43
 * @Version: v1.0
 */
package com.huawei.ibooking.dao;

import com.huawei.ibooking.Constants;
import com.huawei.ibooking.mapper.ReservationRecordMapper;
import com.huawei.ibooking.model.ReservationRecordDO;
import com.huawei.ibooking.model.ReservationRecordDetailDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ReservationRecordDao {
    @Autowired
    ReservationRecordMapper reservationRecordMapper;

    public int createReservationRecord(ReservationRecordDO reservationRecord) {
        return reservationRecordMapper.createReservationRecord(reservationRecord);
    }

    public ReservationRecordDO getReservationRecordById(int id) {
        return reservationRecordMapper.getReservationRecordById(id);
    }

    public int updateReservationRecord(ReservationRecordDO reservationRecord) {
        return reservationRecordMapper.updateReservationRecord(reservationRecord);
    }

    public int cancelReservationRecord(int id, int status) {
        return reservationRecordMapper.cancelReservationRecord(id, status);
    }

    public List<ReservationRecordDO> searchReservationRecords(ReservationRecordDO reservationRecord) {
        return reservationRecordMapper.searchReservationRecords(reservationRecord);
    }

    public List<ReservationRecordDO> searchOverLapReservationRecords(ReservationRecordDO reservationRecord) {
        return reservationRecordMapper.searchOverLapReservationRecords(reservationRecord);
    }

    public List<ReservationRecordDetailDO> getReservationRecordsByStudentId(Integer studentID) {
        Map<String, String> param = new HashMap<>();
        param.put("studentId", String.valueOf(studentID));
        return reservationRecordMapper.getReservationRecordsByStudentId(param);
    }

    public List<ReservationRecordDO> getAllConfirmedReservationRecords(){
        return reservationRecordMapper.getReservationRecords(Constants.RRStatus.CONFIRMED);
    }

    public List<ReservationRecordDO> getAllUsingReservationRecords(){
        return reservationRecordMapper.getReservationRecords(Constants.RRStatus.USING);
    }
}
