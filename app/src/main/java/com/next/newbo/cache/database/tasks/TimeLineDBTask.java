package com.next.newbo.cache.database.tasks;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.alibaba.fastjson.JSON;
import com.next.newbo.cache.Constants;
import com.next.newbo.cache.database.tables.TimeLineTable;
import com.next.newbo.model.MessageListModel;

import java.sql.Time;

/**
 * Created by NeXT on 15/4/12.
 */
public class TimeLineDBTask extends BaseDBTask {

    //TODO 只缓存了当前分组的 TimeLine
    public static void updateTable(int id, MessageListModel messages) {
        SQLiteDatabase db = getWsd();
        db.execSQL(Constants.SQL_DROP_TABLE + TimeLineTable.TABLE_NAME);
        db.execSQL(TimeLineTable.TABLE_NAME);

        ContentValues values = new ContentValues();
        values.put(TimeLineTable.ID, 1);
        values.put(TimeLineTable.JSON, JSON.toJSONString(messages));

        db.insert(TimeLineTable.TABLE_NAME, null, values);

        db.setTransactionSuccessful();
        db.endTransaction();
    }

}
