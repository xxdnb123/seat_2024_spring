/**
 * @Author chensiyuan
 * @ClassName:SeatMapper
 * @Email: trirawchan@gmail.com
 * @Description:
 * @Date: 2023/5/29 15:46
 * @Version: v1.0
 */
package com.huawei.ibooking.mapper;

import com.huawei.ibooking.model.SeatDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface SeatMapper {
    int insertSeat(SeatDO seat);

    SeatDO getSeatById(int id);

    int updateSeat(SeatDO seat);

    int deleteSeat(int id);

    List<SeatDO> findSeatInfo(SeatDO seat);
}