package com.next.newbo.api.remind;

import com.alibaba.fastjson.JSON;
import com.next.newbo.BaseApi;
import com.next.newbo.model.UnreadModel;
import com.next.newbo.api.Constants;
import com.next.newbo.support.http.WeiBoParameters;

import org.json.JSONObject;

/**
 * Created by NeXT on 15/4/8.
 */
public class RemindApi extends BaseApi {

    public enum Type {
        Follower("follower"),
        Cmt("cmt"),
        Dm("dm"),
        Mention_Status("mention_status"),
        Group("group"),
        Notice("notice"),
        Invite("invite"),
        Badge("badge"),
        Photo("photo");

        public String str;

        Type(String str) {
            this. str = str;
        }
    }

    public static UnreadModel getUnread(String uid) {
        WeiBoParameters params = new WeiBoParameters();
        params.put("uid", uid);
        params.put("unread_message", 0);
        try {
            JSONObject json = request(HTTP_GET, Constants.REMIND_UNREAD_COUNT, params);
            return JSON.parseObject(json.toString(), UnreadModel.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



}
