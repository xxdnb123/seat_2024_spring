/**
 * @Author chensiyuan
 * @ClassName:ReservationRecordDetailDO
 * @Email: trirawchan@gmail.com
 * @Description:
 * @Date: 2023/6/5 15:57
 * @Version: v1.0
 */
package com.huawei.ibooking.model;

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
public class ReservationRecordDetailDO {
        @Nullable
        private Integer id;
        @Nullable
        private Integer campusId;
        @Nullable
        private Integer regionId;
        @Nullable
        private Integer buildingId;
        @Nullable
        private Integer number;
        @Nullable
        private Integer column;
        @Nullable
        private Integer row;
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
