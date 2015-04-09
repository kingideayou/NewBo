package com.next.newbo.cache.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by NeXT on 15/4/9.
 */
public class DatabaseManager {

    private static DatabaseManager singletion = null;

    private SQLiteDatabase wsd = null;
    private SQLiteDatabase rsd = null;
    private DatabaseHelper databaseHelper = null;

    private DatabaseManager(){}

    public synchronized static DatabaseManager getInstance() {
        if (singletion == null) {
            DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
            SQLiteDatabase wsd = databaseHelper.getWritableDatabase();
            SQLiteDatabase rsd = databaseHelper.getReadableDatabase();

            singletion = new DatabaseManager();
            singletion.wsd = wsd;
            singletion.rsd = rsd;
            singletion.databaseHelper = databaseHelper;
        }
        return singletion;
    }

    public static void close() {
        if (singletion != null) {
            singletion.databaseHelper.close();
        }
    }

}
