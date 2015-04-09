package com.next.newbo.cache.mian;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.next.newbo.cache.database.DatabaseHelper;
import com.next.newbo.cache.database.tables.TimeLineTable;
import com.next.newbo.cache.login.LoginApiCache;

/**
 * Created by NeXT on 15/4/9.
 */
public class TimeLineApiCache {

    private String mUid;
    private Context mContext;

    public TimeLineApiCache(Context context) {
        mContext = context;
        mUid = new LoginApiCache(context).getUid();
    }

    public void loadFromCache() {
        Cursor cursor = query();
        if (cursor.getCount() == 1) {
            cursor.moveToFirst();
            //TODO 解析数据
        }
    }

    protected Cursor query() {
        return getRsd().query(TimeLineTable.TABLE_NAME, null, null, null, null, null, null);
    }


    private SQLiteDatabase getRsd() {
        return DatabaseHelper.getInstance().getReadableDatabase();
    }

    private SQLiteDatabase getWsd() {
        return DatabaseHelper.getInstance().getWritableDatabase();
    }

}
