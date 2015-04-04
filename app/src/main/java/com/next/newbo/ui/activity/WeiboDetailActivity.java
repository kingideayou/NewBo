package com.next.newbo.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.next.newbo.R;
import com.next.newbo.Utils;
import com.next.newbo.ui.adapter.FeedAdapter;
import com.next.newbo.ui.view.SendCommentButton;

import butterknife.InjectView;

public class WeiboDetailActivity extends BaseActivity implements SendCommentButton.OnSendClickListener {

    public static final String ARG_DRAWING_START_LOCATION = "arg_drawing_start_location";

    @InjectView(R.id.content_root)
    LinearLayout contentRoot;
    @InjectView(R.id.btn_send_comment)
    SendCommentButton btnSendComment;
    @InjectView(R.id.et_comment)
    EditText etComment;
    @InjectView(R.id.rv_comment)
    RecyclerView rvComment;
    @InjectView(R.id.ll_add_comment)
    LinearLayout llAddComment;

    private FeedAdapter feedAdapter;
    private int drawingStartLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weibo_detail);
        setupComments();
        setupSendCommentButton();

        drawingStartLocation = getIntent().getIntExtra(ARG_DRAWING_START_LOCATION, 0);
        if (savedInstanceState == null) {
            contentRoot.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    contentRoot.getViewTreeObserver().removeOnPreDrawListener(this);
                    startIntroAnimation();
                    return true;
                }
            });
        }
    }

    private void startIntroAnimation() {
        ViewCompat.setElevation(getToolbar(), 0);
        contentRoot.setScaleY(0.1f);
        contentRoot.setPivotY(drawingStartLocation);
        llAddComment.setTranslationY(200);

        contentRoot.animate()
                .scaleY(1)
                .setDuration(200)
                .setInterpolator(new AccelerateInterpolator())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
//                        ViewCompat.setElevation(getToolbar(), Utils.dpToPx(8));
                        animateContent();
                    }
                }).start();
    }

    private void animateContent() {
        feedAdapter.updateItems(false);
        llAddComment.animate().translationY(0)
                .setInterpolator(new DecelerateInterpolator())
                .setDuration(200)
                .start();
    }

    private void setupComments() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvComment.setLayoutManager(linearLayoutManager);
        rvComment.setHasFixedSize(true);

        feedAdapter = new FeedAdapter(this, new String[]{"", "", "", "", "", ""});
        rvComment.setAdapter(feedAdapter);
        rvComment.setOverScrollMode(View.OVER_SCROLL_NEVER);
        rvComment.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if(newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    //todo 关闭 Adapter 的动画效果
//                    feedAdapter.setAnimationsLocked(true);
                }
            }
        });

    }

    private void setupSendCommentButton() {
        btnSendComment.setOnSendClickListener(this);
    }

    @Override
    public void onBackPressed() {
        ViewCompat.setElevation(getToolbar(), 0);
        contentRoot.animate()
                .translationY(Utils.getScreenHeight(this))
                .setDuration(200)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        WeiboDetailActivity.super.onBackPressed();
                        overridePendingTransition(0, 0);
                    }
                })
                .start();;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_weibo_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSendClickListener(View v) {
        if (validateComment()){

        }
    }

    private boolean validateComment() {
        if(TextUtils.isEmpty(etComment.getText())) {
            btnSendComment.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake_error));
            return false;
        }
        return true;
    }
}
