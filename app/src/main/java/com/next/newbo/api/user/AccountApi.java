package com.next.newbo.api.user;

import com.next.newbo.BaseApi;
import com.next.newbo.api.Constants;
import com.next.newbo.support.http.WeiboParameters;

import org.json.JSONObject;

/**
 * Created by NeXT on 15-4-7.
 */
public class AccountApi extends BaseApi {

    public static String getUid() {
        //TODO BaseApi 从网络获取用户 UID
        try {
            JSONObject json = request(Constants.GET_UID, new WeiboParameters(), HTTP_GET);
            return json.optString("uid");
        } catch (Exception e) {
            return null;
        }
    }

}
