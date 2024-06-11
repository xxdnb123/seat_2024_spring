package com.huawei.ibooking.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

@NoArgsConstructor
@Getter
@Setter
public class SeatDO {
    @Nullable
    private Integer id;
    @Nullable
    private Integer studyRoomId;
    @Nullable
    private Integer seatColumn;
    @Nullable
    private Integer seatRow;
    @Nullable
    private Boolean hasSocket;
    @Nullable
    private Integer status;
}
