package com.huawei.ibooking.model;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.lang.Nullable;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class ReservationRecordDO {
    @Nullable
    private Integer id;
    @Nullable
    private Integer studentId;
    @Nullable
    private Integer studyRoomId;
    @Nullable
    private Integer seatId;
    @Nullable
    private LocalDateTime reserveTime;
    @Nullable
    private LocalDateTime startTime;
    @Nullable
    private LocalDateTime endTime;
    @Nullable
    private Integer status;
    @Nullable
    private Integer type;
}
