/**
 * @Author chensiyuan
 * @ClassName:constant
 * @Email: trirawchan@gmail.com
 * @Description:
 * @Date: 2023/5/29 17:10
 * @Version: v1.0
 */
package com.huawei.ibooking;

public class Constants {

    public static class StudyRoomStatus {
        public static final int STATUS_NA = 0; //不可用
        public static final int STATUS_OK = 1; //可用
    }

    public static class SeatStatus {
        public static final int STATUS_NA = 0; //不可用
        public static final int STATUS_OK = 1; //可用
    }

    /**
     * ReservationRecordType
     */
    public static class RRType {
        /**
         * 正常预约
         */
        public static final int NORMAL = 0;
        /**
         * 抢占预约
         */
        public static final int PREEMPTION = 1;
    }

    /**
     * ReservationRecordStatus
     */
    public static class RRStatus {
        /**
         * 预约成功待签到->{已签到使用中，过期取消}
         */
        public static final int CONFIRMED = 0;
        /**
         * 已签到使用中->{完成使用}
         */
        public static final int USING = 1;
        /**
         * 完成使用
         */
        public static final int DONE = 2;
        /**
         * 过期取消
         */
        public static final int EXPIRED = 3;
        /**
         * 学生主动取消
         */
        public static final int CANCELED = 4;

        /**
         * 管理员取消
         */
        public static final int ADMIN_CANCELED = 5;
    }


}
