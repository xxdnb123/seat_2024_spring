package com.huawei.ibooking;


import com.huawei.ibooking.business.MailService;
import com.huawei.ibooking.business.ReservationBusiness;
import com.huawei.ibooking.business.StudentBusiness;
import com.huawei.ibooking.dao.ReservationRecordDao;
import com.huawei.ibooking.model.ReservationRecordDetailDO;
import com.huawei.ibooking.model.StudentDO;
import com.huawei.ibooking.model.dto.ReservationDTO;
import com.huawei.ibooking.model.dto.ReservationSignDTO;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookingApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@WebAppConfiguration
public class ReservationServiceTest {

    @Autowired
    private ReservationBusiness reservationBusiness;

    @Autowired
    private StudentBusiness studentBusiness;

    @Autowired
    private MailService mailService;


    @Before
    public void setUp() throws Exception {
        StudentDO student = new StudentDO("res001", "resName", "testMajor", "TestCollege");
        student.setPassword("12345test");
        studentBusiness.register(student);
        StudentDO student2 = new StudentDO("res002", "resName", "testMajor", "TestCollege");
        student2.setPassword("12345test");
        studentBusiness.register(student2);
        studentBusiness.blacklistUpdate(true, "res002");
        StudentDO student3 = new StudentDO("res003", "resName", "testMajor", "TestCollege");
        student3.setPassword("12345test");
        studentBusiness.register(student3);
        StudentDO student4 = new StudentDO("res004", "resName", "testMajor", "TestCollege");
        student4.setPassword("12345test");
        studentBusiness.register(student4);
        StudentDO student5 = new StudentDO("res005", "resName", "testMajor", "TestCollege");
        student5.setPassword("12345test");
        studentBusiness.register(student5);
        StudentDO student6 = new StudentDO("res006", "resName", "testMajor", "TestCollege");
        student6.setPassword("12345test");
        studentBusiness.register(student6);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testReserve() {
        //成功预约
        ReservationDTO reservation = new ReservationDTO(1, "res001",
                LocalDateTime.parse("2023-07-01T10:00:00"),
                LocalDateTime.parse("2023-07-01T11:00:00"), 0);
        Assert.assertEquals(1, reservationBusiness.makeReservation(reservation));

        //学生在黑名单中，无法预约
        reservation = new ReservationDTO(2, "res002",
                LocalDateTime.parse("2023-07-01T10:00:00"),
                LocalDateTime.parse("2023-07-01T11:00:00"), 0);
        Assert.assertEquals(-1, reservationBusiness.makeReservation(reservation));

        //座位不可用，无法预约
        reservation = new ReservationDTO(10, "res002",
                LocalDateTime.parse("2023-07-01T10:00:00"),
                LocalDateTime.parse("2023-07-01T11:00:00"), 0);
        Assert.assertEquals(-1, reservationBusiness.makeReservation(reservation));

    }

    @Test
    public void testCancelReservation() {
        ReservationDTO reservation = new ReservationDTO(1, "res001",
                LocalDateTime.parse("2023-07-02T10:00:00"),
                LocalDateTime.parse("2023-07-02T11:00:00"), 0);
        reservationBusiness.makeReservation(reservation);
        ReservationRecordDetailDO reservationRecordDetailDO = getFirstReservationRecordDetailDO("res001");
        int id = reservationRecordDetailDO.getId();
        reservation.setSeatId(id);
        Assert.assertEquals(1, reservationBusiness.cancelReservation(reservation, Constants.RRStatus.CANCELED));
        reservationRecordDetailDO = getFirstReservationRecordDetailDO("res001");
        int status = reservationRecordDetailDO.getStatus();
        Assert.assertEquals(4, status);
    }

    private ReservationRecordDetailDO getFirstReservationRecordDetailDO(String stuNumber) {
        List<ReservationRecordDetailDO> list = reservationBusiness.getReservationRecordsByStudentId(stuNumber);
        ReservationRecordDetailDO reservationRecordDetailDO = list.get(0);
        return reservationRecordDetailDO;
    }

    @Test
    public void testSignIn() {
        //正常预约，正常签到
        ReservationDTO reservation = new ReservationDTO(1, "res003",
                LocalDateTime.parse("2023-07-01T10:00:00"),
                LocalDateTime.parse("2023-07-01T11:00:00"), 0);
        reservationBusiness.makeReservation(reservation);
        ReservationRecordDetailDO reservationRecordDetailDO = getFirstReservationRecordDetailDO("res003");
        ReservationSignDTO reservationSignDTO = new ReservationSignDTO(
                reservationRecordDetailDO.getId(),
                LocalDateTime.parse("2023-07-01T10:01:00"),
                1, 101, 101001, 103);
        Assert.assertEquals(1, reservationBusiness.signIn(reservationSignDTO));
        reservationRecordDetailDO = getFirstReservationRecordDetailDO("res003");
        int status = reservationRecordDetailDO.getStatus();
        Assert.assertEquals(1, status);

        //抢占预约，正常签到
        reservation = new ReservationDTO(1, "res004",
                LocalDateTime.parse("2023-07-02T10:00:00"),
                LocalDateTime.parse("2023-07-02T11:00:00"), 1);
        reservationBusiness.makeReservation(reservation, LocalDateTime.parse("2023-07-02T11:30:00"));
        reservationRecordDetailDO = getFirstReservationRecordDetailDO("res004");
        reservationSignDTO = new ReservationSignDTO(
                reservationRecordDetailDO.getId(),
                LocalDateTime.parse("2023-07-02T10:31:00"),
                1, 101, 101001, 103);
        Assert.assertEquals(1, reservationBusiness.signIn(reservationSignDTO));
        reservationRecordDetailDO = getFirstReservationRecordDetailDO("res004");
        status = reservationRecordDetailDO.getStatus();
        Assert.assertEquals(1, status);

        //抢占预约，过期取消,无法签到
        reservation = new ReservationDTO(1, "res005",
                LocalDateTime.parse("2023-07-04T10:00:00"),
                LocalDateTime.parse("2023-07-04T11:00:00"), 1);
        reservationBusiness.makeReservation(reservation, LocalDateTime.parse("2023-07-04T10:30:00"));
        reservationBusiness.updateReservationRecordStatusHelper(LocalDateTime.parse("2023-07-04T10:40:00"));
        reservationRecordDetailDO = getFirstReservationRecordDetailDO("res005");
        status = reservationRecordDetailDO.getStatus();
        Assert.assertEquals(3, status);

        //正常预约，过期取消,同时检测邮件是否成功发送,无法签到
        reservation = new ReservationDTO(1, "res006",
                LocalDateTime.parse("2023-07-05T10:00:00"),
                LocalDateTime.parse("2023-07-05T11:00:00"), 0);
        reservationBusiness.makeReservation(reservation);
        int cnt = mailService.testHelperCount;
        reservationBusiness.updateReservationRecordStatusHelper(LocalDateTime.parse("2023-07-05T10:10:00"));
        Assert.assertEquals(cnt+1,mailService.testHelperCount);
        reservationBusiness.updateReservationRecordStatusHelper(LocalDateTime.parse("2023-07-05T10:16:00"));
        reservationRecordDetailDO = getFirstReservationRecordDetailDO("res006");
        status = reservationRecordDetailDO.getStatus();
        Assert.assertEquals(3, status);
    }



}