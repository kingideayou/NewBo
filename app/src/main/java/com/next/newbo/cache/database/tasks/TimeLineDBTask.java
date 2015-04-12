package com.next.newbo.cache.database.tasks;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;
import com.next.newbo.cache.Constants;
import com.next.newbo.cache.database.tables.TimeLineTable;
import com.next.newbo.model.MessageListModel;


/**
 * Created by NeXT on 15/4/12.
 */
public class TimeLineDBTask extends BaseDBTask {

    //TODO 只缓存了当前分组的 TimeLine
    public static void updateTable(int id, MessageListModel messages) {
        SQLiteDatabase db = getWsd();
        db.execSQL(Constants.SQL_DROP_TABLE + TimeLineTable.TABLE_NAME);
        db.execSQL(TimeLineTable.CREATE_TABLE);
        db.beginTransaction();

        ContentValues values = new ContentValues();
        values.put(TimeLineTable.ID, id);
        values.put(TimeLineTable.JSON, new Gson().toJson(messages));

        db.insert(TimeLineTable.TABLE_NAME, null, values);

        db.setTransactionSuccessful();
        db.endTransaction();
    }

}
