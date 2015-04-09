package com.next.newbo.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.text.TextUtils;

import com.next.newbo.R;
import com.next.newbo.model.UnreadModel;
import com.next.newbo.api.remind.RemindApi;
import com.next.newbo.cache.LoginApiCache;
import com.next.newbo.ui.activity.ControlActivity;
import com.next.newbo.ui.activity.MainActivity;
import com.next.newbo.utils.AppLogger;
import com.next.newbo.utils.Settings;

/**
 * Created by NeXT on 15-4-8.
 */
public class ReminderService extends IntentService{

    private static final String TAG = ReminderService.class.getSimpleName();

    private static final int ID = 9999;
    private static final int ID_CMT = 10000;
    private static final int ID_METION = 10001;
    private static final int ID_DM = 10002;

    public ReminderService() {
        super(TAG);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //Start
        fetchRemind();
    }

    private void fetchRemind() {
        LoginApiCache loginApiCache = new LoginApiCache(this);
        String uid= loginApiCache.getUid();
        if (!TextUtils.isEmpty(uid)) {
            UnreadModel unreadModel = RemindApi.getUnread(uid);
            updateNotifications(unreadModel);
        }
    }

    private void updateNotifications(UnreadModel unreadModel) {
        Context context = getApplicationContext();
        if (unreadModel != null) {
            Settings settings = Settings.getInstance(context);
            String previous = settings.getString(Settings.NOTIFICATIN_ONGOING, "");
            String now = unreadModel.toString();
            if (now.equals(previous)) {
                AppLogger.i("没有新的未读消息");
                return ;
            } else {
                settings.putString(Settings.NOTIFICATIN_ONGOING, now);
            }
            int defaultNotifyModel = getDefaultsModels(context);
            Intent intent = new Intent(context, ControlActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.setPackage(context.getPackageName());
            PendingIntent pi;
            String clickToView = context.getResources().getString(R.string.click_to_view);
            NotificationManager nm = (NotificationManager) context.getSystemService(
                    Context.NOTIFICATION_SERVICE);
            if (unreadModel.cmt > 0) {
                intent.putExtra(Intent.EXTRA_INTENT, MainActivity.COMMENT);
                pi = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                //TODO 修改提示图标
                Notification n = buildNotification(context,
                        stringFormat(context, R.string.new_cmt, unreadModel.cmt),
                        clickToView, R.drawable.ic_launcher,
                        defaultNotifyModel, pi);
                nm.notify(ID_CMT, n);
            }

            if (unreadModel.mention_status > 0 || unreadModel.mention_cmt > 0) {
                String detail = "";
                int count = 0;

                //新微博
                if (unreadModel.mention_status > 0) {
                    detail += stringFormat(context, R.string.new_at_detail_weibo,
                            unreadModel.mention_status);
                    count += unreadModel.mention_status;
                    intent.putExtra(Intent.EXTRA_INTENT, MainActivity.MENTION);
                }

                //条评论
                if (unreadModel.mention_cmt > 0) {
                    if (count > 0) {
                        detail += context.getString(R.string.new_at_detail_and);
                    }

                    detail += stringFormat(context, R.string.new_at_detail_comment,
                            unreadModel.mention_cmt);
                    count += unreadModel.mention_cmt;

                    if (unreadModel.mention_status == 0) {
                        intent.putExtra(Intent.EXTRA_INTENT, MainActivity.COMMENT);
                    }

                }

                AppLogger.i("新微博/评论："+count);
                pi = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                Notification n = buildNotification(context,
                        stringFormat(context, R.string.new_at, count),
                        detail,
                        R.drawable.ic_launcher,
                        defaultNotifyModel,
                        pi);
                nm.notify(ID_METION, n);
            }

            if (unreadModel.dm > 0) {
                intent.putExtra(Intent.EXTRA_INTENT, MainActivity.DM);
                pi = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                Notification n = buildNotification(context,
                        stringFormat(context, R.string.new_dm, unreadModel.dm),
                        clickToView,
                        R.drawable.ic_launcher,
                        defaultNotifyModel,
                        pi);
                nm.notify(ID_DM, n);
            }

        }
    }

    private static String stringFormat(Context context, int resId, int data){
        return String.format(context.getString(resId), data);
    }

    private static Notification buildNotification(Context context, String title, String content,
                                                  int icon, int defaults, PendingIntent intent) {
        return new Notification.Builder(context)
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(icon)
                .setDefaults(defaults)
                .setAutoCancel(true)
                .setContentIntent(intent)
                .build();
    }

    private int getDefaultsModels(Context context) {
        Settings settings = Settings.getInstance(context);
        return (settings.getBoolean(Settings.NOTIFICATIN_ONGOING, true) ? Notification.DEFAULT_SOUND : 0) |
                (settings.getBoolean(Settings.NOTIFICATIN_VIBRATE, true) ? Notification.DEFAULT_VIBRATE : 0) |
                Notification.DEFAULT_LIGHTS;
    }

}
