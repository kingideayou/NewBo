package com.next.newbo.ui.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.next.newbo.api.statuses.PostApi;
import com.next.newbo.model.MessageModel;

import static com.next.newbo.utils.Utils.hasSmartBar;

/**
 * Created by NeXT on 15/4/19.
 */
public class RepostActivity extends InputActivity {

    private MessageModel mMessageModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (hasSmartBar()) {
            getWindow().setUiOptions(ActivityInfo.UIOPTION_SPLIT_ACTION_BAR_WHEN_NARROW);
        }
        super.onCreate(savedInstanceState);
        mMessageModel = getIntent().getParcelableExtra("msg");
        if (mMessageModel.retweeted_status != null) {
            etComment.setText("//" +
                    (mMessageModel.user != null ? "@" + mMessageModel.user.getNameNoRemark() + ":" : "") +
                    mMessageModel.text);
        }

    }

    @Override
    protected boolean post() {
        int extra = PostApi.EXTRA_NONE;
        return PostApi.newRepost(mMessageModel.id, etComment.getText().toString(),
                extra, mVersion);
    }
}
