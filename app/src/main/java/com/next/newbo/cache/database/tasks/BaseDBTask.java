package com.next.newbo.cache.database.tasks;

import android.database.sqlite.SQLiteDatabase;

import com.next.newbo.cache.database.DatabaseHelper;

/**
 * Created by NeXT on 15/4/12.
 */
public abstract class BaseDBTask {

    public static SQLiteDatabase getWsd() {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
        return databaseHelper.getWritableDatabase();
    }

    public static SQLiteDatabase getRsd() {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
        return databaseHelper.getReadableDatabase();
    }

}
