package com.next.newbo.cache.main;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;
import com.next.newbo.api.friendships.GroupsApi;
import com.next.newbo.api.statuses.BilateralTimeLineApi;
import com.next.newbo.api.statuses.HomeTimeLineApi;
import com.next.newbo.cache.Constants;
import com.next.newbo.cache.database.DatabaseHelper;
import com.next.newbo.cache.database.tables.TimeLineTable;
import com.next.newbo.cache.database.tasks.TimeLineDBTask;
import com.next.newbo.cache.login.LoginApiCache;
import com.next.newbo.model.MessageListModel;
import com.next.newbo.utils.AppLogger;

/**
 * Created by NeXT on 15/4/9.
 */
public class TimeLineApiCache {

    private static final String BILATERAL = "bilateral";
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
            mMessages = new Gson().fromJson(cursor.getString(1), getListClass());
            mCurrentPage = mMessages.getSize() / Constants.HOME_TIMELINE_ITEM_COUNT;
            mMessages.spanAll(mContext);
            mMessages.timestampAll(mContext);
        } else {
            try {
                mMessages = getListClass().newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
                mMessages = new MessageListModel();
            }
        }
    }

    public void load(boolean newWeibo) {
        load(newWeibo, null);
    }

    public void load(boolean newWeibo, String groupId) {
        if (newWeibo) {
            mCurrentPage = 0;
        }
        MessageListModel list = load(groupId);

        AppLogger.i("list size : " + list.getSize());

        if (newWeibo) {
            mMessages.getList().clear();
        }
        mMessages.addAll(false, mFriendOnly, list, mUid);
        mMessages.spanAll(mContext);
        mMessages.timestampAll(mContext);
    }

    private MessageListModel load(String groupId) {
        if (groupId == null) {
            // 全部
            return load();
        } else if (groupId.equals(BILATERAL)) {
            // 互相关注
            return BilateralTimeLineApi.fetchBilateralTimeLine(
                    Constants.HOME_TIMELINE_ITEM_COUNT, ++mCurrentPage);
        } else {
            return GroupsApi.fetchGroupTimeLine(groupId,
                    Constants.HOME_TIMELINE_ITEM_COUNT, ++mCurrentPage);
        }
    }

    protected MessageListModel load() {
        if (!mFriendOnly) {
            mFriendOnly = true;
        }
        return HomeTimeLineApi.fetchHomeTimeLine(Constants.HOME_TIMELINE_ITEM_COUNT, ++mCurrentPage);
    }

    protected Cursor query() {
        return getRsd().query(TimeLineTable.TABLE_NAME, null, null, null, null, null, null);
    }

    public void cache() {
        TimeLineDBTask.updateTable(1, mMessages);
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
