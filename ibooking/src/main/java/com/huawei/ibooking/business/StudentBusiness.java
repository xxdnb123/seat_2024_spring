package com.huawei.ibooking.business;

import com.huawei.ibooking.dao.StudentDao;
import com.huawei.ibooking.model.StudentDO;
import com.huawei.ibooking.model.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class StudentBusiness {
    @Autowired
    private StudentDao studentDao;

    public List<StudentDO> getStudents() {
        return studentDao.getStudents();
    }


    public Map<String, String> loginStudent(final UserDO userDO) {
        List<StudentDO> students = studentDao.getStudentByNumber(userDO.getNumber());
        if (students.isEmpty()) {
            return null;
        }
        StudentDO studentDO = students.get(0);
        if (!studentDO.getPassword().equals(userDO.getPassword())) {
            return null;
        }
        Map<String, String> map = new HashMap<>();
        map.put("number", studentDO.getNumber());
        map.put("name", studentDO.getName());
        map.put("major", studentDO.getMajor());
        map.put("college", studentDO.getCollege());
        return map;
    }


    public Map<String, String> getStudentByNumber(final String number) {
        List<StudentDO> students = studentDao.getStudentByNumber(number);
        if (students.isEmpty()) {
            return null;
        }
        StudentDO studentDO = students.get(0);
        Map<String, String> map = new HashMap<>();
        map.put("number", studentDO.getNumber());
        map.put("name", studentDO.getName());
        map.put("major", studentDO.getMajor());
        map.put("college", studentDO.getCollege());
        map.put("isBlacklisted", String.valueOf(studentDO.getIsBlacklisted()));
        map.put("defaultCount", String.valueOf(studentDO.getDefaultCount()));
        return map;
    }

    public boolean register(final StudentDO stu) {
        if (studentDao.getStudentByNumber(stu.getNumber()).size() == 0) {
            return studentDao.registerStudent(stu);
        }
        return false;
    }

    public boolean blacklistUpdate(boolean isBlacklisted, String number) {
        if (studentDao.getStudentByNumber(number).size() == 0) {
            return false;
        } else {
            return studentDao.blacklistUpdate(isBlacklisted, number);
        }
    }

    private List<String> studentInfoList(StudentDO studentDO) {
        List<String> info = new ArrayList<>();
        StudentDO stu = studentDao.getStudentByNumber(studentDO.getNumber()).get(0);
        info.add(stu.getNumber());
        info.add(stu.getName());
        info.add(stu.getMajor());
        info.add(stu.getCollege());
        info.add(String.valueOf(stu.getIsBlacklisted()));
        info.add(String.valueOf(stu.getDefaultCount()));
        return info;
    }

    public List<List<String>> studentInfo(StudentDO studentDO) {
        List<List<String>> res = new ArrayList<>();
        List<StudentDO> students = studentDao.findStudentInfo(studentDO.getNumber(), studentDO.getName(),
                studentDO.getMajor(), studentDO.getCollege(), studentDO.getIsBlacklisted(), studentDO.getDefaultCount());
        for (StudentDO stu : students) {
            res.add(studentInfoList(stu));
        }
        return res;

    }

    public List<List<String>> blacklistInfo(StudentDO studentDO) {
        List<List<String>> res = new ArrayList<>();
        List<StudentDO> students = studentDao.findStudentInfo(studentDO.getNumber(), studentDO.getName(),
                studentDO.getMajor(), studentDO.getCollege(), true, studentDO.getDefaultCount());
        for (StudentDO stu : students) {
            res.add(studentInfoList(stu));
        }
        return res;
    }

}
