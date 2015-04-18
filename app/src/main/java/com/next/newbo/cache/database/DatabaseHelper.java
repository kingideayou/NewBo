package com.next.newbo.cache.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.next.newbo.NewBoApplication;
import com.next.newbo.api.comments.StatusCommentApi;
import com.next.newbo.cache.database.tables.StatusCommentTable;
import com.next.newbo.cache.database.tables.TimeLineTable;

/**
 * Created by NeXT on 15/4/9.
 */
public class DatabaseHelper extends SQLiteOpenHelper{

    private static String DATABASE_NAME = "weibo.db";
    private static int DATABASE_VERSION = 1;

    private static DatabaseHelper singleton = null;

    private DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TimeLineTable.CREATE_TABLE);
        db.execSQL(StatusCommentTable.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static synchronized DatabaseHelper getInstance(){
        if (singleton == null) {
            singleton = new DatabaseHelper(NewBoApplication.getInstance());
        }
        return singleton;
    }

    private void deleteAllTable(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXIST " + TimeLineTable.TABLE_NAME);
    }

}
