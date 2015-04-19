package com.next.newbo.api.statuses;

import com.google.gson.Gson;
import com.next.newbo.BaseApi;
import com.next.newbo.api.Constants;
import com.next.newbo.model.AnnotationModel;
import com.next.newbo.model.MessageModel;
import com.next.newbo.support.http.WeiboParameters;

import org.json.JSONObject;

/**
 * Created by NeXT on 15/4/19.
 */
public class PostApi extends BaseApi {

    public static final int EXTRA_NONE = 0;

    public static boolean newPost(String status, String version) {
        WeiboParameters params = new WeiboParameters();
        params.put("status", status);
        params.put("annotations", parseAnnotation(version));

        try {
            JSONObject json = request(Constants.UPDATE, params, HTTP_POST);
            MessageModel msg = new Gson().fromJson(json.toString(), MessageModel.class);
            if (msg == null || msg.idstr == null || msg.idstr.trim().equals("")) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public static boolean newRepost(long id, String status, int extra, String version) {
        WeiboParameters params = new WeiboParameters();
        params.put("status", status);
        params.put("id", id);
        params.put("is_comment", extra);
        params.put("annotations", parseAnnotation(version));

        try {
            JSONObject json = request(Constants.REPOST, params, HTTP_POST);
            MessageModel msg = new Gson().fromJson(json.toString(), MessageModel.class);
            if (msg == null || msg.idstr == null || msg.idstr.trim().equals("")) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private static String parseAnnotation(String version) {
        AnnotationModel annotation = new AnnotationModel();
        annotation.newbo_version = version;
        return "[" + new Gson().toJson(annotation) + "]";
    }

}
