package com.huawei.ibooking.dao;

import com.huawei.ibooking.mapper.StudentMapper;
import com.huawei.ibooking.model.StudentDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StudentDao {
    @Autowired
    private StudentMapper studentMapper;

    public List<StudentDO> getStudents() {
        return studentMapper.getStudents();
    }

    public List<StudentDO> getStudentByNumber(final String number) {
        return studentMapper.getStudentByNumber(number);
    }


    public StudentDO getStudentById(final int id) {
        return studentMapper.getStudentById(id);
    }

    public List<StudentDO> getStudentByName(final String name) {
        return studentMapper.getStudentByName(name);
    }


    public boolean registerStudent(final StudentDO stu) {
        return studentMapper.registerStudent(stu) > 0;
    }

    public boolean blacklistUpdate(boolean isBlacklisted, String number) {
        return studentMapper.blacklistUpdate(isBlacklisted, number) > 0;
    }

    public boolean updateStudentDefaultCount(Integer defaultCount, int id) {
        return studentMapper.updateStudentDefaultCount(defaultCount, id) > 0;
    }

    public List<StudentDO> findStudentInfo(final String number, final String name, final String major, final String college,
                                           final Boolean isBlacklisted, final Integer defaultCount) {
        return studentMapper.findStudentInfo(number, name, major, college, isBlacklisted, defaultCount);
    }

}
