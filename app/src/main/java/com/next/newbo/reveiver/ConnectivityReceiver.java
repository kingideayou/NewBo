package com.next.newbo.reveiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.next.newbo.utils.NetworkUtils;
import com.next.newbo.utils.ServiceUtils;

/**
 * Created by NeXT on 15-4-8.
 */
public class ConnectivityReceiver extends BroadcastReceiver {

    public static boolean isWIFI = true;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (NetworkUtils.isNetworkConnected(context)) {
            //TODO 开启消息提醒的 Service
            ServiceUtils.startServices(context);
        } else {
            //TODO 关闭消息提醒的 Service

        }
    }
}
