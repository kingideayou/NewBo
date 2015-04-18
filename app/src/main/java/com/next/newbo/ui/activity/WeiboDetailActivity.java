package com.next.newbo.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.view.ViewCompat;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.next.newbo.R;
import com.next.newbo.cache.comments.StatusCommentApiCache;
import com.next.newbo.model.MessageModel;
import com.next.newbo.support.SpannableStringUtils;
import com.next.newbo.ui.view.HackyTextView;
import com.next.newbo.utils.AppLogger;
import com.next.newbo.utils.Utils;
import com.next.newbo.ui.adapter.CommentsAdapter;
import com.next.newbo.ui.view.SendCommentButton;

import butterknife.InjectView;

public class WeiboDetailActivity extends BaseActivity implements SendCommentButton.OnSendClickListener, SwipeRefreshLayout.OnRefreshListener {

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
    @InjectView(R.id.swipe_container)
    SwipeRefreshLayout swipeRefreshLayout;

    private MessageModel messageModel;
    private CommentsAdapter commentsAdapter;
    private LinearLayoutManager linearLayoutManager;
    private StatusCommentApiCache mCache;

    private int drawingStartLocation;
    private int mLastCount = 0;

    private boolean mRefreshing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        messageModel = getIntent().getParcelableExtra("msg");
        AppLogger.i("MessageModel : "+messageModel.toString());
        mCache = new StatusCommentApiCache(WeiboDetailActivity.this, messageModel.id);
        mCache.loadFromCache();

        if (mCache.mMessages.getSize() == 0) {
            new Refresher().execute(true);
        }

        setContentView(R.layout.activity_weibo_detail);
        initSwipeRefresh();
        setupComments();
        setupSendCommentButton();

        commentsAdapter.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                /*
                if (!mRefreshing &&
                        linearLayoutManager.findLastVisibleItemPosition() >= commentsAdapter.getItemCount() - 5 &&
                        dy > 0) {
                    new Refresher().execute(false);


                }
                */
            }
        });

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


    private void initSwipeRefresh() {
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
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
        commentsAdapter.updateItems();
        llAddComment.animate().translationY(0)
                .setInterpolator(new DecelerateInterpolator())
                .setDuration(200)
                .start();
    }

    private void setupComments() {
        linearLayoutManager = new LinearLayoutManager(this);
        rvComment.setLayoutManager(linearLayoutManager);
        rvComment.setHasFixedSize(true);

        commentsAdapter = new CommentsAdapter(this, messageModel, mCache.mMessages, rvComment);
        rvComment.setAdapter(commentsAdapter);
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
                        WeiboDetailActivity.this.finish();
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
    protected void onSaveInstanceState(Bundle outState) {

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

        //跳转
        Intent intent = new Intent(WeiboDetailActivity.this, InputActivity.class);
        startActivity(intent);

        if (validateComment()){
            commentsAdapter.addItem();
            commentsAdapter.setAnimationsLocked(false);
            commentsAdapter.setDelayEnterAnimation(false);
            rvComment.smoothScrollBy(0, rvComment.getChildAt(0).getHeight() * commentsAdapter.getItemCount());

            etComment.setText(null);
            btnSendComment.setCurrentState(SendCommentButton.STATE_DONE);
        }
    }

    private boolean validateComment() {
        if(TextUtils.isEmpty(etComment.getText())) {
            btnSendComment.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake_error));
            return false;
        }
        return true;
    }

    @Override
    public void onRefresh() {
        //TODO 刷新数据
        if(!mRefreshing) {
            new Refresher().execute(true);
        }
    }

    private class Refresher extends AsyncTask<Boolean, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Utils.clearOnGoingUnreadCount(WeiboDetailActivity.this);
            mLastCount = mCache.mMessages.getSize();
            mRefreshing = true;
            if (swipeRefreshLayout != null) {
                swipeRefreshLayout.setRefreshing(true);
                swipeRefreshLayout.invalidate();
            }
        }

        @Override
        protected Boolean doInBackground(Boolean... params) {
            load(params[0]);
            return params[0];
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if (result) {
                rvComment.stopScroll();
                commentsAdapter.notifyDataSetChanged();
                mRefreshing = false;
                if (swipeRefreshLayout != null) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        }

    }

    private void load(Boolean param) {
        mCache.load(param);
        mCache.cache();
    }

}
