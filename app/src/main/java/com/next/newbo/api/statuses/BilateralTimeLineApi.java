package com.next.newbo.api.statuses;

import com.google.gson.Gson;
import com.next.newbo.BaseApi;
import com.next.newbo.api.Constants;
import com.next.newbo.model.MessageListModel;
import com.next.newbo.support.http.WeiboParameters;
import com.next.newbo.utils.AppLogger;

import org.json.JSONObject;


/**
 * Created by NeXT on 15/4/12.
 */
public class BilateralTimeLineApi extends BaseApi {

    public static MessageListModel fetchBilateralTimeLine(int count, int page) {
        WeiboParameters params = new WeiboParameters();
        params.put("count", count);
        params.put("page", page);
        try {
            JSONObject json = request(Constants.BILATERAL_TIMELINE, params, HTTP_GET);
            return new Gson().fromJson(json.toString(), MessageListModel.class);
        } catch (Exception e) {
            e.printStackTrace();
            AppLogger.d("Cannot fetch bilateral timeline");
            return null;
        }
    }

}
