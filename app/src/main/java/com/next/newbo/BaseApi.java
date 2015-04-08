package com.next.newbo;

/**
 * Created by NeXT on 15-4-7.
 */
public abstract class BaseApi {

    private static String mAccessToken;

    public static String getAccessToken() {
        return mAccessToken;
    }

    public static void setAccessToken(String mAccessToken) {
        BaseApi.mAccessToken = mAccessToken;
    }
}
