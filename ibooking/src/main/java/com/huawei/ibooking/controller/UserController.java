package com.huawei.ibooking.controller;

import com.huawei.ibooking.business.AdminBusiness;
import com.huawei.ibooking.business.StudentBusiness;
import com.huawei.ibooking.model.StudentDO;
import com.huawei.ibooking.model.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class UserController {
    @Autowired
    private StudentBusiness studentBusiness;

    @Autowired
    private AdminBusiness adminBusiness;

    @GetMapping(value = "/student")
    public ResponseEntity<List<StudentDO>> list() {
        final List<StudentDO> students = studentBusiness.getStudents();

        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @PostMapping(value = "/user/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody UserDO user) {
        Map<String, String> res = studentBusiness.loginStudent(user);
        if (res != null) {
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        res = adminBusiness.loginManager(user);
        if (res != null) {
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    @PostMapping(value = "/user/register")
    public ResponseEntity<Boolean> register(@RequestBody StudentDO student) {
        boolean res = studentBusiness.register(student);
        HttpStatus httpStatus = res ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(res, httpStatus);
    }

    @PostMapping(value = "/stu/info")
    public ResponseEntity<Map<String, String>> getInfo(@RequestBody StudentDO student) {
        Map<String, String> res = studentBusiness.getStudentByNumber(student.getNumber());
        if (res == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
    }

    @PostMapping(value = "/admin/blacklistAdd")
    public ResponseEntity<HttpStatus> blacklistAdd(@RequestBody StudentDO student) {
        boolean res = studentBusiness.blacklistUpdate(true, student.getNumber());
        HttpStatus httpStatus = res ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(httpStatus);
    }

    @PostMapping(value = "/admin/blacklistDel")
    public ResponseEntity<HttpStatus> blacklistDel(@RequestBody StudentDO student) {
        boolean res = studentBusiness.blacklistUpdate(false, student.getNumber());
        HttpStatus httpStatus = res ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(httpStatus);
    }

    @PostMapping(value = "/admin/studentInfo")
    public ResponseEntity<List<List<String>>> studentInfo(@RequestBody StudentDO student) {
        List<List<String>> res = studentBusiness.studentInfo(student);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping(value = "/admin/blacklistInfo")
    public ResponseEntity<List<List<String>>> blacklistInfo(@RequestBody StudentDO student) {
        List<List<String>> res = studentBusiness.blacklistInfo(student);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
