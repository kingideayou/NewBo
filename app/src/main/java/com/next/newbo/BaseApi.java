package com.next.newbo;

import com.next.newbo.support.http.HttpUtils;
import com.next.newbo.support.http.WeiboParameters;
import com.next.newbo.utils.AppLogger;

import org.json.JSONObject;

/**
 * Created by NeXT on 15-4-7.
 */
public abstract class BaseApi {

    public static final String HTTP_GET = HttpUtils.GET;
    public static final String HTTP_POST = HttpUtils.POST;

    private static String mAccessToken;

    public static String getAccessToken() {
        return mAccessToken;
    }

    public static void setAccessToken(String mAccessToken) {
        BaseApi.mAccessToken = mAccessToken;
    }

    protected  static JSONObject request(String url, WeiboParameters params, String method) {
        return request(method, mAccessToken, url,  params, JSONObject.class);
    }

    protected static<T> T request(String method, String token, String url, WeiboParameters params,
                                  Class<T> jsonClass) {
        if (token == null) {
            AppLogger.i("Token == null");
            return null;
        } else {
            params.put("access_token", token);
            try {
                String jsonData = HttpUtils.doRequest(url, params, method);
                AppLogger.i("jsonData : " + jsonData);
                if (jsonData != null && (jsonData.contains("[") || jsonData.contains("{"))) {
                    return jsonClass.getConstructor(String.class).newInstance(jsonData);
                } else {
                    return null;
                }
            } catch (Exception e) {
                e.printStackTrace();
                AppLogger.e(e);
                return null;
            }
        }

    }

}
