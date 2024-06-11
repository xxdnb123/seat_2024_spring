package com.huawei.ibooking;

import com.huawei.ibooking.business.StudyRoomBusiness;
import com.huawei.ibooking.model.StudyRoomDO;
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

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookingApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@WebAppConfiguration
public class ResourceServiceTest {

    @Autowired
    private StudyRoomBusiness studyRoomBusiness;

    @Before
    public void setUp() throws Exception {
        List<Map<String, String>> seatsInfo = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 5; j++) {
                Map<String, String> seat = new HashMap<>();
                seat.put("column", Integer.toString(i));
                seat.put("row", Integer.toString(j));
                seat.put("hasSocket", "TRUE");
                seatsInfo.add(seat);
            }
        }
        StudyRoomDO studyRoom = new StudyRoomDO(
                6, 601, 601001, 101, 0,
                LocalTime.parse("08:00:00"),
                LocalTime.parse("22:00:00"),
                50,
                seatsInfo);
        studyRoomBusiness.newStudyRoom(studyRoom);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testStudyRoomList() {
        //查询所有
        List<Map<String, String>> res = studyRoomBusiness.studyRoomList(new StudyRoomDO());
        Assert.assertNotEquals(0, res.size());

        //查询某个存在的自习室
        StudyRoomDO studyRoomDO = new StudyRoomDO();
        studyRoomDO.setCampusId(6);
        res = studyRoomBusiness.studyRoomList(studyRoomDO);
        Assert.assertEquals(1, res.size());
        Assert.assertEquals("6", res.get(0).get("campus"));

        //查询一个不存在的自习室
        studyRoomDO.setCampusId(7);
        res = studyRoomBusiness.studyRoomList(studyRoomDO);
        Assert.assertNull(res);

    }
    @Test
    public void testNewStudyRoom(){
        //添加新教室，信息不完整，失败
        StudyRoomDO studyRoom = new StudyRoomDO(
                6, 601, 601001, 101, 0,
                LocalTime.parse("08:00:00"),
                LocalTime.parse("22:00:00"),
                50,
                null);
        Assert.assertEquals(-1,studyRoomBusiness.newStudyRoom(studyRoom));

        //添加一个已经存在的教室，失败
        List<Map<String, String>> seatsInfo = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 5; j++) {
                Map<String, String> seat = new HashMap<>();
                seat.put("column", Integer.toString(i));
                seat.put("row", Integer.toString(j));
                seat.put("hasSocket", "TRUE");
                seatsInfo.add(seat);
            }
        }
        studyRoom = new StudyRoomDO(
                6, 601, 601001, 101, 0,
                LocalTime.parse("08:00:00"),
                LocalTime.parse("22:00:00"),
                50,
                seatsInfo);
        Assert.assertEquals(-2,studyRoomBusiness.newStudyRoom(studyRoom));

        //成功添加教室
        studyRoom.setNumber(102);
        Assert.assertEquals(1,studyRoomBusiness.newStudyRoom(studyRoom));
    }

    @Test
    public void testGetSeats(){
        List<Map<String, String>> seatsInfo = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 2; j++) {
                Map<String, String> seat = new HashMap<>();
                seat.put("column", Integer.toString(i));
                seat.put("row", Integer.toString(j));
                seat.put("hasSocket", "TRUE");
                seatsInfo.add(seat);
            }
        }
        StudyRoomDO studyRoom = new StudyRoomDO(
                6, 602, 602001, 102, 0,
                LocalTime.parse("08:00:00"),
                LocalTime.parse("22:00:00"),
                20,
                seatsInfo);
        studyRoomBusiness.newStudyRoom(studyRoom);
        List<Map<String, String>> res = studyRoomBusiness.studyRoomList(new StudyRoomDO());
        for (Map<String, String> room:res){
            List<Map<String, String>> seats = studyRoomBusiness.getSeatDOByStudyRoomID(Integer.parseInt(room.get("id")));
            Assert.assertNotEquals(0,seats.size());
        }
    }




}