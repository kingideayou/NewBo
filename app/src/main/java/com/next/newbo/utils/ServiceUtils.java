package com.next.newbo.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.next.newbo.service.ReminderService;

/**
 * Created by NeXT on 15-4-8.
 */
public class ServiceUtils {

    private static final int REQUEST_CODE = 100001;

    public static void startServices(Context context) {
        Settings settings = Settings.getInstance(context);
        int interval = getIntervalTime(settings.getInt(
                Settings.NOTIFICATIN_INTERVAL, 1));
        if (interval > -1) {
            startServiceAlarm(context, ReminderService.class, interval);
        }
    }

    private static void startServiceAlarm(Context context, Class<?> service, long interval) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, service);
        PendingIntent pi = PendingIntent.getService(context, REQUEST_CODE, intent,
                PendingIntent.FLAG_CANCEL_CURRENT);
        am.setInexactRepeating(AlarmManager.ELAPSED_REALTIME, 0, interval, pi);
    }

    public static void stopServices(Context context) {
        stopServiceAlarm(context, ReminderService.class);
    }

    public static void stopServiceAlarm(Context context, Class<?> service) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, service);
        PendingIntent pi = PendingIntent.getService(context, REQUEST_CODE, intent,
                PendingIntent.FLAG_CANCEL_CURRENT);
        am.cancel(pi);
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
