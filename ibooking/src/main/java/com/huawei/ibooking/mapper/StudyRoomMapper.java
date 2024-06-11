/**
 * @Author chensiyuan
 * @ClassName:StudyRoomMapper
 * @Email: trirawchan@gmail.com
 * @Description:
 * @Date: 2023/5/29 14:38
 * @Version: v1.0
 */
package com.huawei.ibooking.mapper;

import com.huawei.ibooking.model.StudentDO;
import com.huawei.ibooking.model.StudyRoomDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StudyRoomMapper {
    int insertStudyRoom(StudyRoomDO studyRoom);

    StudyRoomDO getStudyRoomById(int id);

    int updateStudyRoom(StudyRoomDO studyRoom);

    int deleteStudyRoom(int id);

    List<StudyRoomDO> findStudyRoomInfo(StudyRoomDO studyRoom);
}