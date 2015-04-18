package com.next.newbo.cache.database.tables;

/**
 * Created by NeXT on 15/4/18.
 */
public class StatusCommentTable {

    public static final String TABLE_NAME     = "status_comment";
    public static final String MSGID    = "msgId";
    public static final String JSON     = "json";

    public static final String CREATE_TABLE = "create table " + TABLE_NAME
            + "("
            + MSGID + " integer primary key autoincrement,"
            + JSON + " text"
            + ");";

}
