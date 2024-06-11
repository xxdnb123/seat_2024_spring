package com.huawei.ibooking.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@Getter
@Setter

public class StudyRoomDO {
    @Nullable
    private Integer id;
    @JsonAlias({"campus", "campusId"})
    @Nullable
    private Integer campusId;
    @JsonAlias({"region", "regionId"})
    @Nullable
    private Integer regionId;
    @JsonAlias({"building", "buildingId"})
    @Nullable
    private Integer buildingId;
    @Nullable
    private Integer number;
    @Nullable
    private Integer status;
    @Nullable
    private LocalTime openTime;
    @Nullable
    private LocalTime closeTime;
    @Nullable
    private Integer availableSeatNumber;
    @Nullable
    private List<Map<String, String>> seatsInfo;

    public StudyRoomDO(@Nullable Integer campusId, @Nullable Integer regionId, @Nullable Integer buildingId, @Nullable Integer number, @Nullable Integer status, @Nullable LocalTime openTime, @Nullable LocalTime closeTime, @Nullable Integer availableSeatNumber, @Nullable List<Map<String, String>> seatsInfo) {
        this.campusId = campusId;
        this.regionId = regionId;
        this.buildingId = buildingId;
        this.number = number;
        this.status = status;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.availableSeatNumber = availableSeatNumber;
        this.seatsInfo = seatsInfo;
    }
}
