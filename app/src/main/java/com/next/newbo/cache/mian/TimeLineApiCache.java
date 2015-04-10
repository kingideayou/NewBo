package com.next.newbo.cache.mian;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.alibaba.fastjson.JSON;
import com.next.newbo.cache.Constants;
import com.next.newbo.cache.database.DatabaseHelper;
import com.next.newbo.cache.database.tables.TimeLineTable;
import com.next.newbo.cache.login.LoginApiCache;
import com.next.newbo.model.MessageListModel;

/**
 * Created by NeXT on 15/4/9.
 */
public class TimeLineApiCache {

    private String mUid;
    private Context mContext;

    public MessageListModel mMessages;
    protected int mCurrentPage = 0;
    protected boolean mFriendOnly = false;

    public TimeLineApiCache(Context context) {
        mContext = context;
        mUid = new LoginApiCache(context).getUid();
    }

    public void loadFromCache() {
        Cursor cursor = query();
        if (cursor.getCount() == 1) {
            cursor.moveToFirst();
            //TODO 解析数据
            mMessages = JSON.parseObject(cursor.getString(1), getListClass());
            mCurrentPage = mMessages.getSize() / Constants.HOME_TIMELINE_ITEM_COUNT;
            mMessages.spanAll(mContext);
            mMessages.timestampAll(mContext);
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

    protected Class<? extends MessageListModel> getListClass() {
        return MessageListModel.class;
    }

}
