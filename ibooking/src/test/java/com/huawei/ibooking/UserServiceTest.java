package com.huawei.ibooking;


import com.huawei.ibooking.business.StudentBusiness;
import com.huawei.ibooking.model.StudentDO;
import com.huawei.ibooking.model.UserDO;
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

import java.util.Map;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookingApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@WebAppConfiguration
public class UserServiceTest {

    @Autowired
    private StudentBusiness studentBusiness;

    @Before
    public void setUp() throws Exception {
        StudentDO student = new StudentDO("test1001", "testName", "testMajor", "TestCollege");
        student.setPassword("12345test");
        studentBusiness.register(student);
        StudentDO student1 = new StudentDO("test2001", "testBlacklist", "testMajor", "TestCollege");
        student1.setPassword("12345test");
        studentBusiness.register(student1);
        StudentDO student2 = new StudentDO("test2002", "testInBlacklist", "testMajor", "TestCollege");
        student2.setIsBlacklisted(true);
        student2.setPassword("12345test");
        studentBusiness.register(student2);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testLogin() {
        //成功登录
        UserDO user = new UserDO();
        user.setNumber("test1001");
        user.setPassword("12345test");
        Assert.assertNotNull(studentBusiness.loginStudent(user));

        //密码错误
        user.setPassword("1234");
        Assert.assertNull(studentBusiness.loginStudent(user));

        //用户不存在
        user = new UserDO();
        user.setNumber("test100");
        user.setPassword("12345test");
        Assert.assertNull(studentBusiness.loginStudent(user));
    }

    @Test
    public void testRegister() {
        //注册成功
        StudentDO student = new StudentDO("1002", "testReg", "testMajor", "TestCollege");
        student.setPassword("testReg");
        Assert.assertTrue(studentBusiness.register(student));

        //学号存在，注册失败
        Assert.assertFalse(studentBusiness.register(student));
    }

    @Test
    public void testList() {
        //测试查看学生列表
        Assert.assertNotNull(studentBusiness.getStudents());
        Assert.assertNotEquals(0, studentBusiness.getStudents().size());
    }

    @Test
    public void testStuInfo() {
        //查询某个学生信息
        Map<String, String> student = studentBusiness.getStudentByNumber("test1001");
        Assert.assertEquals("testName", student.get("name"));

        //查询某个不存在的学生信息
        Assert.assertNull(studentBusiness.getStudentByNumber("10000000"));
    }

    @Test
    public void testBlacklistAdd(){
        //添加至黑名单
        studentBusiness.blacklistUpdate(true, "test2001");
        Assert.assertEquals("true",studentBusiness.getStudentByNumber("test2001").get("isBlacklisted"));
    }

    @Test
    public void testBlacklistDel(){
        //从黑名单移除
        studentBusiness.blacklistUpdate(false, "test2002");
        Assert.assertEquals("false",studentBusiness.getStudentByNumber("test2001").get("isBlacklisted"));
    }




}