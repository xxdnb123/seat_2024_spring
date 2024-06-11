/**
 * @Author chensiyuan
 * @ClassName:ReservationRecordMapper
 * @Email: trirawchan@gmail.com
 * @Description:
 * @Date: 2023/6/4 20:41
 * @Version: v1.0
 */
package com.huawei.ibooking.mapper;

import com.huawei.ibooking.model.ReservationRecordDO;
import com.huawei.ibooking.model.ReservationRecordDetailDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ReservationRecordMapper {

    int createReservationRecord(ReservationRecordDO reservationRecord);

    ReservationRecordDO getReservationRecordById(int id);

    int updateReservationRecord(ReservationRecordDO reservationRecord);

    int cancelReservationRecord(@Param("id") int id, @Param("status") int status);

    int deleteReservationRecord(@Param("id") int id, @Param("status") int status);

    List<ReservationRecordDO> searchReservationRecords(ReservationRecordDO reservationRecord);

    List<ReservationRecordDO> getReservationRecords(@Param("status")int status);

    List<ReservationRecordDO> searchOverLapReservationRecords(ReservationRecordDO reservationRecord);

    List<ReservationRecordDetailDO> getReservationRecordsByStudentId(Map<String, String> params);
}