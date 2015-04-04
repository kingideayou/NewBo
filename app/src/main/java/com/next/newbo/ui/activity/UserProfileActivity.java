package com.next.newbo.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewTreeObserver;

import com.next.newbo.R;
import com.next.newbo.ui.view.RevealBackgroundView;

import butterknife.InjectView;

public class UserProfileActivity extends BaseActivity implements RevealBackgroundView.OnStateChangeListener {

    public static final String ARG_REVEAL_START_LOCATION = "reveal_start_location";

    @InjectView(R.id.rbv_main)
    RevealBackgroundView rbvMain;

    public static void startUserProfileFromLocation(int[] startingLocation, Activity startingActvity){
        Intent intent = new Intent(startingActvity, UserProfileActivity.class);
        intent.putExtra(ARG_REVEAL_START_LOCATION, startingLocation);
        startingActvity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        setupRevealBackground(savedInstanceState);
    }

    private void setupRevealBackground(Bundle savedInstanceState) {
        rbvMain.setOnStateChangeListener(this);
        if(savedInstanceState == null) {
            final int[] startingLocation = getIntent().getIntArrayExtra(ARG_REVEAL_START_LOCATION);
            rbvMain.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    rbvMain.getViewTreeObserver().removeOnPreDrawListener(this);
                    rbvMain.startFromLocation(startingLocation);
                    return true;
                }
            });
        } else {
            rbvMain.setToFinishedFrame();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_user_profile, menu);
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
    public void onStateChange(int state) {
        if(RevealBackgroundView.STATE_FINISHED == state) {
            //TODO 显示微博详情
        } else {
            //TODO 隐藏微博详情
        }
    }
}
