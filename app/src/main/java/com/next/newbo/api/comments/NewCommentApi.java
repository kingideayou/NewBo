package com.next.newbo.api.comments;

import com.google.gson.Gson;
import com.next.newbo.BaseApi;
import com.next.newbo.api.Constants;
import com.next.newbo.model.CommentModel;
import com.next.newbo.support.http.WeiboParameters;

import org.json.JSONObject;

/**
 * Created by NeXT on 15/4/19.
 */
public class NewCommentApi extends BaseApi {

    public static boolean commentOn(long id, String comment, boolean commentOrig) {
        WeiboParameters params = new WeiboParameters();
        params.put("comment", comment);
        params.put("id", id);
        params.put("comment_ori", commentOrig ? 1 : 0);

        try {
            JSONObject json = request(Constants.COMMENTS_CREATE, params, HTTP_POST);
            CommentModel commentModel = new Gson().fromJson(json.toString(), CommentModel.class);
            return !(commentModel == null || commentModel.id <= 0);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean replyTo(long id, long cid, String comment, boolean commentOrig) {
        WeiboParameters params = new WeiboParameters();
        params.put("comment", comment);
        params.put("id", id);
        params.put("cid", cid);
        params.put("comment_ori", commentOrig ? 1 : 0);

        try {
            JSONObject json = request(Constants.COMMENTS_REPLY, params, HTTP_POST);
            CommentModel msg = new Gson().fromJson(json.toString(), CommentModel.class);
            return !(msg == null || msg.id <= 0);
        } catch (Exception e) {
            return false;
        }
    }

    public static void deleteComment(long cid) {
        WeiboParameters params = new WeiboParameters();
        params.put("cid", cid);
        try {
            request(Constants.COMMENTS_DESTROY, params, HTTP_POST);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
