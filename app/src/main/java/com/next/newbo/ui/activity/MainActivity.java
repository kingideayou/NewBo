package com.next.newbo.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.next.newbo.R;
import com.next.newbo.utils.Utils;
import com.next.newbo.support.ShakeDetector;
import com.next.newbo.ui.adapter.FeedAdapter;
import com.next.newbo.utils.Settings;
import com.next.newbo.ui.view.FeedContextMenu;
import com.next.newbo.ui.view.FeedContextMenuManager;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;


public class MainActivity extends BaseActivity implements FeedAdapter.OnFeedItemClickListener, FeedContextMenu.OnFeedContextMenuItemClickListener, ShakeDetector.ShakeListener {

    private static final int ANIM_DURATION_TOOLBAR = 300;

    public static final int HOME = 0, COMMENT = 1, FAV = 2, DM = 3, MENTION = 4, SEARCH = 5;

    @InjectView(R.id.rvFeed)
    RecyclerView rvFeed;

    private FeedAdapter feedAdapter;
    private ShakeDetector shakeDetector;
    private boolean pendingIntroAnimation;

    private Settings settings;
    private boolean isFeedMenuShow = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        setupFeed();



        settings = Settings.getInstance(this);
        shakeDetector = ShakeDetector.getInstance(this);

        if(savedInstanceState == null){
            pendingIntroAnimation = true;
        } else {
            feedAdapter.updateItems(false);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (settings.getBoolean(Settings.SHAKE_TO_TOP, true)){
            shakeDetector.addListeners(this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        shakeDetector.removeListener(this);
    }

    public void setupFeed(){
        //提高性能
//        rvFeed.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this) {
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                return 300;
            }
        };
        rvFeed.setLayoutManager(layoutManager);

        feedAdapter = new FeedAdapter(getApplicationContext(), new String[]{"1","2","3",
                "4","5","6","7","8","9"});
        feedAdapter.setOnFeedClickListener(this);
        rvFeed.setAdapter(feedAdapter);
        /*
        rvFeed.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(),
                        new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // do whatever
                        Toast.makeText(getApplicationContext(), "点击条目" + position,
                                Toast.LENGTH_SHORT).show();
                    }
                })
        );
        */
        rvFeed.setOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                isFeedMenuShow = false;
                FeedContextMenuManager.getInstance().onScrolled(recyclerView, dx, dy);
            }
        });
        rvFeed.setVisibility(View.GONE);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("isFeedMenuShow", isFeedMenuShow);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        isFeedMenuShow = savedInstanceState.getBoolean("isFeedMenuShow");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        if (pendingIntroAnimation) {
            pendingIntroAnimation = false;
            startIntroAnimation();
        } else {
            rvFeed.setVisibility(View.VISIBLE);
        }
        return true;
    }

    private void startIntroAnimation() {
        int toolbarSize = Utils.dpToPx(56);
        getToolbar().setTranslationY(-toolbarSize);
        getIvLogo().setTranslationY(-toolbarSize);

        getToolbar().animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(300);

        getIvLogo().animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(500)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animator) {
                        startContentAnimation();
                    }
                }).start();
    }

    private void startContentAnimation() {
        rvFeed.setVisibility(View.VISIBLE);
        feedAdapter.updateItems(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCommentsClick(View v, int position) {
        dismissFeedMenu();
        Intent commentIntent = new Intent(MainActivity.this, WeiboDetailActivity.class);
        int[] startingLocation = new int[2];
        v.getLocationOnScreen(startingLocation);
        commentIntent.putExtra(WeiboDetailActivity.ARG_DRAWING_START_LOCATION, startingLocation[1]);
        startActivity(commentIntent);
        overridePendingTransition(0, 0);
    }

    @Override
    public void onMoreClick(View v, int position) {
        if(isFeedMenuShow){
            dismissFeedMenu();
            isFeedMenuShow = false;
        } else {
            isFeedMenuShow = true;
            FeedContextMenuManager.getInstance().toggleContextMenuFromView(v, position, this);
            Crouton.showText(this, "点击第" + position + "条更多", Style.INFO);
        }
    }

    @Override
    public void onTranspondClick(View v, int position) {
        Crouton.showText(this, "点击第" + position + "条转发", Style.INFO);
        dismissFeedMenu();
    }

    @Override
    public void onContentClick(View v, int position) {
        Crouton.showText(this, "点击第" + position + "条文本", Style.INFO);
        dismissFeedMenu();
    }

    @Override
    public void onButon1Click(int feedItem) {
        Crouton.showText(this, "点击第" + feedItem + "条的 Button 1", Style.INFO);
        dismissFeedMenu();
    }

    @Override
    public void onButon2Click(int feedItem) {
        Crouton.showText(this, "点击第" + feedItem + "条的 Button 2", Style.INFO);
        dismissFeedMenu();
    }

    private void dismissFeedMenu() {
        if(isFeedMenuShow){
            FeedContextMenuManager.getInstance().hideContextMenu();
            isFeedMenuShow = false;
        }
    }

    @Override
    public void onShake() {
        //TODO 检测到晃动手机
        if (rvFeed != null) {
            rvFeed.scrollToPosition(0);
        }
    }
}
