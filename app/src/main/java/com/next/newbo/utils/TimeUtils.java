package com.next.newbo.utils;

import java.util.concurrent.TimeUnit;

/**
 * Created by NeXT on 15-4-8.
 */
public class TimeUtils {

    public static int expireTimeInDays(long time) {
        return (int) TimeUnit.MILLISECONDS.toDays(time - System.currentTimeMillis());
    }

    /**
     * 接收消息时间间隔
     * @param id
     * @return
     */
    public static int getIntervalTime(int id) {
        switch (id) {
            case 0:
                return 1 * 60 * 1000;
            case 1:
                return 3 * 60 * 1000;
            case 2:
                return 5 * 60 * 1000;
            case 3:
                return 10 * 60 * 1000;
            case 4:
                return 30 * 60 * 1000;
            case 5:
                return -1;
        }
        return -1;
    }

}
