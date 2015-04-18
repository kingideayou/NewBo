package com.next.newbo.api.comments;

import com.google.gson.Gson;
import com.next.newbo.BaseApi;
import com.next.newbo.api.Constants;
import com.next.newbo.model.CommentListModel;
import com.next.newbo.support.http.WeiboParameters;
import com.orhanobut.logger.Logger;

import org.json.JSONObject;

/**
 * Created by NeXT on 15/4/16.
 */
public class StatusCommentApi extends BaseApi {

    public static CommentListModel fetchCommentOfStatus(long msgId, int count, int page) {
        WeiboParameters params = new WeiboParameters();
        params.put("id", msgId);
        params.put("count", count);
        params.put("page", page);
        try {
            JSONObject json = request(Constants.COMMENTS_SHOW, params, HTTP_GET);
            return new Gson().fromJson(json.toString(), CommentListModel.class);
        } catch (Exception e) {
            Logger.i("获取评论列表失败");
            return null;
        }
    }

}
