package com.next.newbo.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.text.TextUtils;

import com.next.newbo.UnreadModel;
import com.next.newbo.api.remind.RemindApi;
import com.next.newbo.cache.LoginApiCache;

/**
 * Created by NeXT on 15-4-8.
 */
public class ReminderService extends IntentService{


    public ReminderService(String name) {
        super(name);
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
            //TODO 获取未读消息，如有未读消息则通知用户
            UnreadModel unreadModel = RemindApi.getUnread(uid);
        }
    }

}
