/**
 * @Author chensiyuan
 * @ClassName:ReservationController
 * @Email: trirawchan@gmail.com
 * @Description:
 * @Date: 2023/6/5 10:38
 * @Version: v1.0
 */
package com.huawei.ibooking.controller;

import com.huawei.ibooking.Constants;
import com.huawei.ibooking.business.ReservationBusiness;
import com.huawei.ibooking.model.ReservationRecordDetailDO;
import com.huawei.ibooking.model.dto.ReservationDTO;
import com.huawei.ibooking.model.dto.ReservationSignDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class ReservationController {
    @Autowired
    private ReservationBusiness reservationBusiness;

    @PostMapping(value = "/stu/reserve")
    public ResponseEntity<HttpStatus> stu_reserve(@RequestBody ReservationDTO reservationDTO) {
        reservationDTO.setType(Constants.RRType.NORMAL);
        int succ = reservationBusiness.makeReservation(reservationDTO);
        HttpStatus httpStatus = succ > 0 ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(httpStatus);
    }

    @PostMapping(value = "/stu/reserveCancel")
    public ResponseEntity<HttpStatus> stu_reserveCancel(@RequestBody ReservationDTO reservationDTO) {
        int succ = reservationBusiness.cancelReservation(reservationDTO, Constants.RRStatus.CANCELED);
        HttpStatus httpStatus = succ > 0 ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(httpStatus);
    }

    @PostMapping(value = "/admin/reserveCancel")
    public ResponseEntity<HttpStatus> admin_reserveCancel(@RequestBody ReservationDTO reservationDTO) {
        int succ = reservationBusiness.cancelReservation(reservationDTO, Constants.RRStatus.ADMIN_CANCELED);
        HttpStatus httpStatus = succ > 0 ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(httpStatus);
    }

    @PostMapping(value = "/stu/reservePre")
    public ResponseEntity<HttpStatus> stu_reservePre(@RequestBody ReservationDTO reservationDTO) {
        int succ = reservationBusiness.makeReservationPre(reservationDTO);
        HttpStatus httpStatus = succ > 0 ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(httpStatus);
    }


    @PostMapping(value = "/stu/reserveSeize")
    public ResponseEntity<HttpStatus> stu_reserveSeize(@RequestBody ReservationDTO reservationDTO) {
        reservationDTO.setType(Constants.RRType.PREEMPTION);
        int succ = reservationBusiness.makeReservation(reservationDTO);
        HttpStatus httpStatus = succ > 0 ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(httpStatus);
    }

    @PostMapping(value = "/stu/reservationList")
    public ResponseEntity<List<ReservationRecordDetailDO>> stu_reservationList(@RequestBody Map<String,String> param) {
        String studentNumber = String.valueOf(param.get("number"));
        List<ReservationRecordDetailDO> reservationRecordsByStudentId = reservationBusiness.getReservationRecordsByStudentId(studentNumber);
        return new ResponseEntity<>(reservationRecordsByStudentId,HttpStatus.OK);
    }

    @PostMapping(value = "/stu/signIn")
    public ResponseEntity<HttpStatus> stu_signIn(@RequestBody ReservationSignDTO reservationSignDTO) {
        int succ = reservationBusiness.signIn(reservationSignDTO);
        HttpStatus httpStatus = succ > 0 ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(httpStatus);
    }
}