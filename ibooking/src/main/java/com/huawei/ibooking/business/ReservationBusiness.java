/**
 * @Author chensiyuan
 * @ClassName:ReservationBusiness
 * @Email: trirawchan@gmail.com
 * @Description:
 * @Date: 2023/6/4 20:02
 * @Version: v1.0
 */
package com.huawei.ibooking.business;

import com.huawei.ibooking.Constants;
import com.huawei.ibooking.dao.ReservationRecordDao;
import com.huawei.ibooking.dao.StudentDao;
import com.huawei.ibooking.dao.StudyRoomDao;
import com.huawei.ibooking.model.*;
import com.huawei.ibooking.model.dto.ReservationDTO;
import com.huawei.ibooking.model.dto.ReservationSignDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class ReservationBusiness {
    @Autowired
    private ReservationRecordDao reservationRecordDao;
    @Autowired
    private StudyRoomDao studyRoomDao;
    @Autowired
    private StudentDao studentDao;

    @Autowired
    private MailService mailService;


    public int makeReservation(ReservationDTO reservationDTO) {
        return makeReservation(reservationDTO, LocalDateTime.now());
    }

    public int makeReservation(ReservationDTO reservationDTO, LocalDateTime localDateTime) {
        SeatDO seatDo = studyRoomDao.getSeatById(reservationDTO.getSeatId());
        if (Constants.SeatStatus.STATUS_NA == seatDo.getStatus()) {
            return -1;
        }
        StudentDO studentDO = studentDao.getStudentByNumber(reservationDTO.getStudentNumber()).get(0);
        if (studentDO.getIsBlacklisted()) {
            return -1;
        }

        ReservationRecordDO reservationRecordDO = new ReservationRecordDO();
        reservationRecordDO.setSeatId(reservationDTO.getSeatId());
        reservationRecordDO.setReserveTime(localDateTime);
        reservationRecordDO.setStartTime(reservationDTO.getOpenTime());
        reservationRecordDO.setEndTime(reservationDTO.getCloseTime());
        List<ReservationRecordDO> reservationRecordDOS = reservationRecordDao.searchOverLapReservationRecords(reservationRecordDO);
        if (reservationRecordDOS != null) {
            for (ReservationRecordDO recordDO : reservationRecordDOS) {
                if (recordDO.getStatus() == Constants.RRStatus.USING || recordDO.getStatus() == Constants.RRStatus.CONFIRMED) {
                    return -1;
                }
            }
        }
        reservationRecordDO.setStudentId(studentDO.getId());
        reservationRecordDO.setStudyRoomId(seatDo.getStudyRoomId());
        reservationRecordDO.setStatus(Constants.RRStatus.CONFIRMED);
        reservationRecordDO.setType(reservationDTO.getType());
        return reservationRecordDao.createReservationRecord(reservationRecordDO);
    }

    public int cancelReservation(ReservationDTO reservationDTO, int status) {
        return reservationRecordDao.cancelReservationRecord(reservationDTO.getSeatId(), status);
    }

    public List<ReservationRecordDetailDO> getReservationRecordsByStudentId(String studentNumber) {
        StudentDO studentDO = studentDao.getStudentByNumber(studentNumber).get(0);
        return reservationRecordDao.getReservationRecordsByStudentId(studentDO.getId());
    }

    public int makeReservationPre(ReservationDTO reservationDTO) {
        ReservationRecordDO preReservationRecordDO = reservationRecordDao.getReservationRecordById(reservationDTO.getSeatId());
        int seatId = preReservationRecordDO.getSeatId();
        SeatDO seatDO = studyRoomDao.getSeatById(seatId);
        if (Constants.SeatStatus.STATUS_NA == seatDO.getStatus()) {
            return -1;
        }
        int studentId = preReservationRecordDO.getStudentId();
        StudentDO studentDO = studentDao.getStudentById(studentId);
        if (studentDO.getIsBlacklisted()) {
            return -1;
        }
        ReservationRecordDO reservationRecordDO = new ReservationRecordDO();
        reservationRecordDO.setSeatId(seatId);
        reservationRecordDO.setReserveTime(LocalDateTime.now());
        reservationRecordDO.setStartTime(reservationDTO.getOpenTime());
        reservationRecordDO.setEndTime(reservationDTO.getCloseTime());
        List<ReservationRecordDO> reservationRecordDOS = reservationRecordDao.searchOverLapReservationRecords(reservationRecordDO);
        if (reservationRecordDOS != null) {
            for (ReservationRecordDO recordDO : reservationRecordDOS) {
                if (recordDO.getStatus() == Constants.RRStatus.USING || recordDO.getStatus() == Constants.RRStatus.CONFIRMED) {
                    return -1;
                }
            }
        }
        reservationRecordDO.setStudentId(studentId);
        reservationRecordDO.setStudyRoomId(preReservationRecordDO.getStudyRoomId());
        reservationRecordDO.setStatus(Constants.RRStatus.CONFIRMED);
        reservationRecordDO.setType(Constants.RRType.NORMAL);
        return reservationRecordDao.createReservationRecord(reservationRecordDO);
    }

    public int signIn(ReservationSignDTO reservationSignDTO) {
        ReservationRecordDO reservationRecordDO = reservationRecordDao.getReservationRecordById(reservationSignDTO.getId());
        StudyRoomDO studyRoomDO = studyRoomDao.getStudyRoomById(reservationRecordDO.getStudyRoomId());
        if (!reservationSignDTO.getBuildingId().equals(studyRoomDO.getBuildingId())) {
            return -1;
        }
        if (reservationRecordDO.getType() == Constants.RRType.NORMAL) {
            if (reservationSignDTO.getTime().isBefore(reservationRecordDO.getStartTime())) {
                return -1;
            }
            if (reservationSignDTO.getTime().isAfter(reservationRecordDO.getStartTime().plusMinutes(15))) {
                return -1;
            }
        } else {
            if (reservationSignDTO.getTime().isAfter(reservationRecordDO.getReserveTime().plusMinutes(5))) {
                return -1;
            }
        }

        reservationRecordDO.setStatus(Constants.RRStatus.USING);
        return reservationRecordDao.updateReservationRecord(reservationRecordDO);
    }

    @Scheduled(cron = "0 0/1 * * * ?")
    public void updateReservationRecordStatus() {
        List<ReservationRecordDO> allConfirmedReservationRecords = reservationRecordDao.getAllConfirmedReservationRecords();
        LocalDateTime localDateTime = LocalDateTime.now();
        for (ReservationRecordDO recordDO : allConfirmedReservationRecords) {
            if ((recordDO.getType() == Constants.RRType.NORMAL &&
                    localDateTime.isAfter(recordDO.getStartTime().plusMinutes(15))) ||
                    (recordDO.getType() == Constants.RRType.PREEMPTION &&
                            localDateTime.isAfter(recordDO.getReserveTime().plusMinutes(5)))) {
                recordDO.setStatus(Constants.RRStatus.EXPIRED);
                reservationRecordDao.updateReservationRecord(recordDO);
                int id = recordDO.getStudentId();
                StudentDO studentDO = studentDao.getStudentById(id);
                int defaultCount = studentDO.getDefaultCount() + 1;
                String number = studentDO.getNumber();
                studentDao.updateStudentDefaultCount(defaultCount, id);
                if (defaultCount >= 3) {
                    studentDao.blacklistUpdate(true, number);
                }
            }
            if (recordDO.getType() == Constants.RRType.NORMAL &&
                    localDateTime.isAfter(recordDO.getStartTime().plusMinutes(5))) {
                int id = recordDO.getStudentId();
                StudentDO studentDO = studentDao.getStudentById(id);
                String text = "学生" + studentDO.getName() + "，请检查你的座位预约记录，尽快完成签到。";
                mailService.sendTextMailMessage(text);
            }
        }


        List<ReservationRecordDO> allUsingReservationRecords = reservationRecordDao.getAllUsingReservationRecords();

        for (ReservationRecordDO recordDO : allUsingReservationRecords) {
            if (localDateTime.isAfter(recordDO.getEndTime())) {
                recordDO.setStatus(Constants.RRStatus.DONE);
                reservationRecordDao.updateReservationRecord(recordDO);
            }
        }
    }


    public void updateReservationRecordStatusHelper(LocalDateTime localDateTime) {
        List<ReservationRecordDO> allConfirmedReservationRecords = reservationRecordDao.getAllConfirmedReservationRecords();
        for (ReservationRecordDO recordDO : allConfirmedReservationRecords) {
            if ((recordDO.getType() == Constants.RRType.NORMAL &&
                    localDateTime.isAfter(recordDO.getStartTime().plusMinutes(15))) ||
                    (recordDO.getType() == Constants.RRType.PREEMPTION &&
                            localDateTime.isAfter(recordDO.getReserveTime().plusMinutes(5)))) {
                recordDO.setStatus(Constants.RRStatus.EXPIRED);
                reservationRecordDao.updateReservationRecord(recordDO);
                int id = recordDO.getStudentId();
                StudentDO studentDO = studentDao.getStudentById(id);
                int defaultCount = studentDO.getDefaultCount() + 1;
                String number = studentDO.getNumber();
                studentDao.updateStudentDefaultCount(defaultCount, id);
                if (defaultCount >= 3) {
                    studentDao.blacklistUpdate(true, number);
                }
            }
            if (recordDO.getType() == Constants.RRType.NORMAL &&
                    localDateTime.isAfter(recordDO.getStartTime().plusMinutes(5))) {
                int id = recordDO.getStudentId();
                StudentDO studentDO = studentDao.getStudentById(id);
                String text = "学生" + studentDO.getName() + "，请检查你的座位预约记录，尽快完成签到。";
                mailService.sendTextMailMessageTestHelper(text);
            }
        }


        List<ReservationRecordDO> allUsingReservationRecords = reservationRecordDao.getAllUsingReservationRecords();

        for (ReservationRecordDO recordDO : allUsingReservationRecords) {
            if (localDateTime.isAfter(recordDO.getEndTime())) {
                recordDO.setStatus(Constants.RRStatus.DONE);
                reservationRecordDao.updateReservationRecord(recordDO);
            }
        }
    }
}

