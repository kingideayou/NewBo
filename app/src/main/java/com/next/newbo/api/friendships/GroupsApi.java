package com.next.newbo.api.friendships;

import com.alibaba.fastjson.JSON;
import com.next.newbo.BaseApi;
import com.next.newbo.api.Constants;
import com.next.newbo.model.GroupListModel;
import com.next.newbo.model.MessageListModel;
import com.next.newbo.support.http.WeiboParameters;
import com.next.newbo.utils.AppLogger;

import org.json.JSONObject;

/**
 * Created by NeXT on 15/4/12.
 */
public class GroupsApi extends BaseApi {

    public static GroupListModel getGroups() {
        WeiboParameters params = new WeiboParameters();
        try {
            JSONObject json = request(Constants.FRIENDSHIPS_GROUPS, params, HTTP_GET);
            return JSON.parseObject(json.toString(), GroupListModel.class);
        } catch (Exception e) {
            AppLogger.i("Cannot get groups");
            return null;
        }
    }

    public static MessageListModel fetchGroupTimeLine(String groupId, int count, int page) {
        WeiboParameters params = new WeiboParameters();
        params.put("list_id", groupId);
        params.put("count", count);
        params.put("page", page);
        try{
            JSONObject json = request(Constants.FRIENDSHIPS_GROUPS_TIMELINE, params, HTTP_GET);
            return JSON.parseObject(json.toString(), MessageListModel.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isMember(String uid, String groupId) {
        WeiboParameters params = new WeiboParameters();
        params.put("uid", uid);
        params.put("list_id", groupId);
        try {
            JSONObject json = request(Constants.FRIENDSHIPS_GROUPS_IS_MEMBER, params, HTTP_GET);
            return json.optBoolean("lists");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void addMemberToGroup(String uid, String groupId) {
        WeiboParameters params = new WeiboParameters();
        params.put("uid", uid);
        params.put("list_id", groupId);
        try{
            request(Constants.FRIENDSHIPS_GROUPS_MEMBERS_ADD, params, HTTP_POST);
        } catch (Exception e) {
            AppLogger.e(e);
        }
    }

    public static void removeMemberFromGroup(String uid, String groupId) {
        WeiboParameters params = new WeiboParameters();
        params.put("uid", uid);
        params.put("list_id", groupId);
        try {
            request(Constants.FRIENDSHIPS_GROUPS_MEMBERS_DESTROY, params, HTTP_POST);
        } catch (Exception e){
            AppLogger.e(e);
        }
    }

    public static void createGroup(String name) {
        WeiboParameters params = new WeiboParameters();
        params.put("name", name);
        try {
            request(Constants.FRIENDSHIPS_GROUPS_CREATE, params, HTTP_POST);
        } catch (Exception e) {
            AppLogger.e(e);
        }
    }

    public static void destoryGroup(String groupId) {
        WeiboParameters params = new WeiboParameters();
        params.put("list_id", groupId);
        try {
            request(Constants.FRIENDSHIPS_GROUPS_DESTROY, params, HTTP_POST);
        } catch (Exception e) {
            AppLogger.e(e);
        }
    }

}
