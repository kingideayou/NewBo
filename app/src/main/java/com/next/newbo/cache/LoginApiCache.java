package com.next.newbo.cache;

import android.content.Context;
import android.text.TextUtils;

import com.next.newbo.BaseApi;
import com.next.newbo.api.user.AccountApi;
import com.next.newbo.utils.AppLogger;
import com.next.newbo.utils.SettingHelper;

/**
 * Created by NeXT on 15-4-7.
 */
public class LoginApiCache {

    private static final String ACCESS_TOKEN = "access_token";
    private static final String UID = "uid";
    private static final String EXPIRES_IN = "expires_in";

    private String mAccessToken;
    private String mUid;
    private long mExpireDate;
    private Context context;

    public LoginApiCache(Context context) {
        this.context = context;
        mAccessToken = SettingHelper.getSharedPreferences(context, ACCESS_TOKEN, "");
        mUid = SettingHelper.getSharedPreferences(context, UID, "");
        mExpireDate = SettingHelper.getSharedPreferences(context, EXPIRES_IN, Long.MIN_VALUE);
        if (TextUtils.isEmpty(mAccessToken)){
            //TODO 设置全局的 access_token
            AppLogger.i("access_token : " + mAccessToken);
        }
    }

    public void login(String token, String expire) {
        mAccessToken = token;
        //TODO 设置全局的 access_token
        BaseApi.setAccessToken(mAccessToken);
        mExpireDate = System.currentTimeMillis();
        mUid = AccountApi.getUid();
    }

    public String getAccessToken() {
        return mAccessToken;
    }

    public String getUid() {
        return mUid;
    }

    public long getExpireDate() {
        return mExpireDate;
    }

    public void cache() {
        SettingHelper.setEditor(context,
                new String[]{"access_token", "expires_in", "uid"},
                new String[]{mAccessToken, String.valueOf(mExpireDate), mUid});
    }
}
