package com.huawei.ibooking.mapper;

import com.huawei.ibooking.model.StudentDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StudentMapper {
    List<StudentDO> getStudents();

    List<StudentDO> getStudentByNumber(@Param("number") String number);

    StudentDO getStudentById(@Param("id") int id);

    List<StudentDO> getStudentByName(@Param("name") String name);

    int registerStudent(@Param("student") StudentDO studentDO);

    int blacklistUpdate(@Param("isBlacklisted") Boolean isBlacklisted, @Param("number") String number);

    int updateStudentDefaultCount(@Param("defaultCount") Integer defaultCount, @Param("id") int id);

    List<StudentDO> findStudentInfo(@Param("number") String number, @Param("name") String name,
                                    @Param("major") String major, @Param("college") String college,
                                    @Param("isBlacklisted") Boolean isBlacklisted, @Param("defaultCount") Integer defaultCount);
}
