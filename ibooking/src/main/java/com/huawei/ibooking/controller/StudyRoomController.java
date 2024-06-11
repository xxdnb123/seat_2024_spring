package com.huawei.ibooking.controller;

import com.huawei.ibooking.business.StudyRoomBusiness;
import com.huawei.ibooking.model.SeatDO;
import com.huawei.ibooking.model.StudentDO;
import com.huawei.ibooking.model.StudyRoomDO;
import com.huawei.ibooking.model.dto.StudyRoomDetailQueryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class StudyRoomController {
    @Autowired
    private StudyRoomBusiness studyRoomBusiness;

    @GetMapping(value = "/test")
    public String test_point(){
        return "gooood";
    }

    @PostMapping(value = "/stu/studyRoomList")
    public ResponseEntity<List<Map<String, String>>> stu_studyRoomList(@RequestBody StudyRoomDO studyRoomDO) {
        List<Map<String, String>> empty = new ArrayList<>();
        List<Map<String, String>> maps = studyRoomBusiness.studyRoomList(studyRoomDO);
        if ( maps == null ){
            maps = empty;
        }
        return new ResponseEntity<>(maps, HttpStatus.OK);
    }

    @PostMapping(value = "/admin/studyRoomList")
    public ResponseEntity<List<Map<String, String>>> admin_studyRoomList(@RequestBody StudyRoomDO studyRoomDO) {
        List<Map<String, String>> empty = new ArrayList<>();
        List<Map<String, String>> maps = studyRoomBusiness.studyRoomList(studyRoomDO);
        if ( maps == null ){
            maps = empty;
        }
        return new ResponseEntity<>(maps, HttpStatus.OK);
    }

    @PostMapping(value = "/admin/changeStudyRoomStatus")
    public ResponseEntity<HttpStatus> admin_changeStudyRoomStatus(@RequestBody StudyRoomDO studyRoomDO) {
        StudyRoomDO targetStudyRoom= studyRoomBusiness.getByID(studyRoomDO.getId());
        if(targetStudyRoom==null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        targetStudyRoom.setStatus(studyRoomDO.getStatus());
        int succ = studyRoomBusiness.updateStudyRoom(targetStudyRoom);
        HttpStatus httpStatus = succ > 0 ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(httpStatus);
    }

    @PostMapping(value = "/admin/studyRoomAdd")
    public ResponseEntity<HttpStatus> admin_studyRoomAdd(@RequestBody StudyRoomDO studyRoomDO) {
        int succ = studyRoomBusiness.newStudyRoom(studyRoomDO);
        HttpStatus httpStatus = succ > 0 ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(httpStatus);
    }
    @PostMapping(value = "/stu/studyRoomDetail")
    public ResponseEntity<List<Map<String, String>>> stu_studyRoomDetail(@RequestBody StudyRoomDetailQueryDTO studyRoomDetailQueryDTO) {
        List<Map<String, String>> empty = new ArrayList<>();
        Integer id = studyRoomDetailQueryDTO.getId();
        List<Map<String, String>> maps = studyRoomBusiness.getSeatDOWithReservationFiltered(studyRoomDetailQueryDTO);
        if ( maps == null ){
            maps = empty;
        }
        return new ResponseEntity<>(maps,HttpStatus.OK);
    }
}
