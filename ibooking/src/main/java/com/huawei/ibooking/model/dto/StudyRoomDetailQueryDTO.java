/**
 * @Author chensiyuan
 * @ClassName:StudyRoomDetailQueryDTO
 * @Email: trirawchan@gmail.com
 * @Description:
 * @Date: 2023/6/5 15:47
 * @Version: v1.0
 */
package com.huawei.ibooking.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@NoArgsConstructor
@Getter
@Setter
public class StudyRoomDetailQueryDTO {
    private Integer id;
    private LocalDateTime openTime;
    private LocalDateTime closeTime;
}
