package com.next.newbo.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by NeXT on 15-4-7.
 */
public class Settings {

    public static final String SHARED_PREFERENCE_NAME = "settings";

    public static final String SHAKE_TO_TOP = "shake_to_top";

    public static final String NOTIFICATIN_SOUND = "notification_sound";
    public static final String NOTIFICATIN_VIBRATE = "notification_vibrate";
    public static final String NOTIFICATIN_INTERVAL = "notification_interval";
    public static final String NOTIFICATIN_ONGOING = "notification_ongoing";

    public static final String AUTO_NO_PIC = "auto_no_pic";

    public static final String THEME_DARK = "theme_dark";

    public static final String CURRENT_GROUP = "current_group";

    public static final String LAST_POSITION = "last_position";

    private static Settings mInstance;

    private SharedPreferences mPrefs;

    public static Settings getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new Settings(context);
        }
        return mInstance;
    }

    private Settings(Context context) {
        mPrefs = context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public Settings putBoolean(String key, boolean value) {
        mPrefs.edit().putBoolean(key, value).apply();
        return this;
    }

    public boolean getBoolean(String key, boolean def) {
        return mPrefs.getBoolean(key, def);
    }

    public Settings putString(String key, String value) {
        mPrefs.edit().putString(key, value).apply();
        return this;
    }

    public String getString(String key, String defValue) {
        return mPrefs.getString(key, defValue);
    }

    public int getInt(String key, int value) {
        return mPrefs.getInt(key, value);
    }
}
