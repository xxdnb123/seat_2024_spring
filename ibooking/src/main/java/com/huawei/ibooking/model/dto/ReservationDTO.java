/**
 * @Author chensiyuan
 * @ClassName:ReservationDTO
 * @Email: trirawchan@gmail.com
 * @Description:
 * @Date: 2023/6/5 10:21
 * @Version: v1.0
 */
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
public class ReservationDTO {
    @JsonAlias({"seatId", "id"})
    private Integer seatId;
//    @JsonAlias({"studentId"})
//    private Integer studentId;
    @JsonAlias({"number"})
    private String studentNumber;
    private LocalDateTime openTime;
    private LocalDateTime closeTime;
    @Nullable
    private Integer type;

    public ReservationDTO(Integer seatId, String studentNumber, LocalDateTime openTime, LocalDateTime closeTime, @Nullable Integer type) {
        this.seatId = seatId;
        this.studentNumber = studentNumber;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.type = type;
    }
}
