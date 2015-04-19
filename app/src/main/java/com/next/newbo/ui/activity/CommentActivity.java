package com.next.newbo.ui.activity;

import android.os.Bundle;

import com.next.newbo.api.comments.NewCommentApi;
import com.next.newbo.model.MessageModel;

public class CommentActivity extends InputActivity {

    private MessageModel messageModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        messageModel = getIntent().getParcelableExtra("msg");
    }

    /*
    自定义Menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
     */

    @Override
    protected boolean post() {
        //最后一个参数 : 评论原微博
        return NewCommentApi.commentOn(messageModel.id, etComment.getText().toString(), false);
    }
}
