package com.next.newbo.utils;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by NeXT on 15-4-8.
 */
public class NetworkUtils {

    public static boolean isNetworkConnected(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE);
        if (cm != null && cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isConnected()){
           return true;
        }
        return false;
    }

}
