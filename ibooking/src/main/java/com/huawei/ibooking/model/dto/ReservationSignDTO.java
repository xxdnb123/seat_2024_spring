package com.huawei.ibooking.model.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class ReservationSignDTO {
    private Integer id;
    private LocalDateTime time;
    @JsonAlias("campus")
    private Integer campusId;
    @JsonAlias("region")
    private Integer regionId;
    @JsonAlias("building")
    private Integer buildingId;
    @JsonAlias("number")
    private Integer studyRoomId;

    public ReservationSignDTO(Integer id, LocalDateTime time, Integer campusId, Integer regionId, Integer buildingId, Integer studyRoomId) {
        this.id = id;
        this.time = time;
        this.campusId = campusId;
        this.regionId = regionId;
        this.buildingId = buildingId;
        this.studyRoomId = studyRoomId;
    }
}
