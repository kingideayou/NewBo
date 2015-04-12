package com.next.newbo.support;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Toast;

import com.next.newbo.NewBoApplication;
import com.next.newbo.cache.Constants;
import com.next.newbo.model.UserModel;
import com.next.newbo.ui.activity.TopicActivity;
import com.next.newbo.ui.activity.UserTimeLineActivity;
import com.next.newbo.utils.AppLogger;

/**
 * Created by NeXT on 15-4-10.
 */
public class WeiboSpan extends ClickableSpan {

    private String mUrl;
    private Uri mUri;

    public WeiboSpan(String url) {
        mUrl = url;
        mUri = Uri.parse(url);
    }

    public String getUrl() {
        return mUrl;
    }

    @Override
    public void onClick(View view) {
        Context context = view.getContext();
        AppLogger.d("onClick : " + mUri.getScheme());
        if (mUri.getScheme().startsWith("http")) {
            //TODO 打开网页
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(mUri);
            context.startActivity(intent);
        } else {
            if (mUri.getScheme().startsWith(Constants.MENTION_SCHEME)) {
                String userName = mUrl.substring(mUrl.lastIndexOf("@") + 1, mUrl.length());
                AppLogger.i("点击 @ 用户");
                Toast.makeText(NewBoApplication.getInstance(), "点击 : "+userName, Toast.LENGTH_SHORT).show();
                new UserInfoTask().execute(context, userName);
            } else if (mUri.getScheme().startsWith(Constants.TOPIC_SCHEME)) {
                String topicName = mUrl.substring(mUrl.indexOf("#") + 1,
                        mUrl.lastIndexOf("#"));
                Toast.makeText(NewBoApplication.getInstance(), "话题 : "+topicName, Toast.LENGTH_SHORT).show();
                Intent topicIntent = new Intent(context, TopicActivity.class);
                topicIntent.setAction(Intent.ACTION_MAIN);
                topicIntent.putExtra("topic", topicName);
                context.startActivity(topicIntent);
            }
        }
    }

    @Override
    public void updateDrawState(@NonNull TextPaint ds) {
        ds.setColor(ds.linkColor);
        ds.setUnderlineText(false);
    }

    private class UserInfoTask extends AsyncTask<Object, Void, Object[]>{

        @Override
        protected Object[] doInBackground(Object... objects) {
            //TODO 获取  UserTimeLine 信息
            Context context = (Context) objects[0];
            String userName = (String) objects[1];
            return new Object[0];
        }

        @Override
        protected void onPostExecute(Object[] result) {
            super.onPostExecute(result);
            Context context = (Context) result[0];
            UserModel user = (UserModel) result[1];
            if (user != null && user.id != null & !user.id.trim().equals("")) {
                Intent intent = new Intent(context, UserTimeLineActivity.class);
                intent.setAction(Intent.ACTION_MAIN);
                intent.putExtra("user", user);
                context.startActivity(intent);
            }
        }
    }
}
