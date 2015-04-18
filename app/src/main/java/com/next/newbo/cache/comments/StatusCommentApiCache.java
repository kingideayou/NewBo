package com.next.newbo.cache.comments;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;
import com.next.newbo.api.comments.StatusCommentApi;
import com.next.newbo.cache.Constants;
import com.next.newbo.cache.database.tables.StatusCommentTable;
import com.next.newbo.cache.main.TimeLineApiCache;
import com.next.newbo.model.CommentListModel;
import com.next.newbo.model.MessageListModel;

/**
 * Created by NeXT on 15/4/18.
 */
public class StatusCommentApiCache extends TimeLineApiCache {

    private long mId;

    public StatusCommentApiCache(Context context, long mId) {
        super(context);
        this.mId = mId;
    }

    @Override
    public void cache() {
        SQLiteDatabase db = getWsd();
        db.beginTransaction();

        db.delete(StatusCommentTable.TABLE_NAME,
                StatusCommentTable.MSGID + " =? ",
                new String[]{String.valueOf(mId)});

        ContentValues values = new ContentValues();
        values.put(StatusCommentTable.MSGID, mId);
        values.put(StatusCommentTable.JSON, new Gson().toJson((CommentListModel)mMessages));

        db.insert(StatusCommentTable.TABLE_NAME, null, values);

        db.setTransactionSuccessful();
        db.endTransaction();

    }

    @Override
    protected Cursor query() {
        return getRsd().query(
                StatusCommentTable.TABLE_NAME,
                new String[]{StatusCommentTable.MSGID, StatusCommentTable.JSON},
                StatusCommentTable.MSGID + " =? ",
                new String[]{String.valueOf(mId)},
                null, null, null);
    }

    @Override
    protected MessageListModel load() {
        return StatusCommentApi.fetchCommentOfStatus(
                mId,
                Constants.HOME_TIMELINE_ITEM_COUNT,
                ++mCurrentPage);
    }


    @Override
    protected Class<? extends MessageListModel> getListClass() {
        return CommentListModel.class;
    }

}
