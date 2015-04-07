package com.next.newbo.utils;

import com.next.newbo.BuildConfig;
import com.orhanobut.logger.Logger;

/**
 * Created by NeXT on 15-4-7.
 */
public class AppLogger {

    private static boolean DEBUG = BuildConfig.LOG_DEBUG;

    private AppLogger(){
    }

    public static void i(String msg){
        if (DEBUG) {
            Logger.i(msg);
        }
    }

    public static void v(String msg){
        if (DEBUG) {
            Logger.v(msg);
        }
    }

    public static void w(String msg){
        if (DEBUG) {
            Logger.w(msg);
        }
    }

    public static void d(String msg){
        if (DEBUG) {
            Logger.d(msg);
        }
    }

    public static void e(Exception e){
        if (DEBUG) {
            Logger.e(e);
        }
    }

    public static void e(String msg){
        if (DEBUG) {
            Logger.e(msg);
        }
    }

    public static void json(String json){
        if (DEBUG) {
            Logger.json(json);
        }
    }

}
