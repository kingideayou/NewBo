package com.next.newbo.utils;

import java.util.concurrent.TimeUnit;

/**
 * Created by NeXT on 15-4-8.
 */
public class DateUtils {

    public static int expireTimeInDays(long time) {
        return (int) TimeUnit.MILLISECONDS.toDays(time - System.currentTimeMillis());
    }

}
