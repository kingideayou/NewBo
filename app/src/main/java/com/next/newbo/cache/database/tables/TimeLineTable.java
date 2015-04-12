package com.next.newbo.cache.database.tables;

/**
 * Created by NeXT on 15/4/9.
 */
public class TimeLineTable {

    public static final String TABLE_NAME = "timeline_table";

    public static final String ID = "id";

    public static final String JSON = "json";

    public static final String CREATE_TABLE = "create table " + TABLE_NAME
            + "("
            + ID + " integer primary key autoincrement ,"
            + JSON + " text"
            + ");";


}
