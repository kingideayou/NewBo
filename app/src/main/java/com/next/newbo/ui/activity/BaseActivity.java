package com.next.newbo.ui.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.next.newbo.R;
import com.next.newbo.utils.AppLogger;
import com.next.newbo.utils.Utils;
import com.next.newbo.support.ShakeDetector;
import com.next.newbo.ui.utils.DrawerLayoutInstaller;
import com.next.newbo.ui.view.GlobalMenuView;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

/**
 * Created by NeXT on 15-3-27.
 */
public class BaseActivity extends ActionBarActivity implements GlobalMenuView.OnHeaderClickListener, AdapterView.OnItemClickListener {

    @Optional
    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @Optional
    @InjectView(R.id.ivLogo)
    ImageView ivLogo;

    private DrawerLayout drawerLayout;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.inject(this);
        setupToolbar();
        setupDrawer();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }

        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.style_color_primary_dark);

        tintManager.setNavigationBarTintEnabled(true);
        tintManager.setNavigationBarTintColor(Color.parseColor("#00000000"));

        /*
        int actionBarHeight = getActionBarHeight(this);
        AppLogger.i("mActionBar Height : " + actionBarHeight);
        tintManager.getConfig().setActionBarHeight(actionBarHeight);
        */
        super.onCreate(savedInstanceState);
    }

    private int getActionBarHeight(Context context) {
        int result = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            TypedValue tv = new TypedValue();
            context.getTheme().resolveAttribute(R.attr.actionBarSize, tv, true);
//                context.getTheme().resolveAttribute(android.support.v7.appcompat.R.attr.actionBarSize, tv, true);
            result = TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
        }
        return result;
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    protected void setupDrawer() {
        GlobalMenuView menuView = new GlobalMenuView(this);
        menuView.setOnItemClickListener(this);
        menuView.setOnHeaderClickListener(this);
        drawerLayout = DrawerLayoutInstaller.from(this)
                .drawerRoot(R.layout.drawer_root)
                .drawerLeftView(menuView)
                .drawerLeftWidth(Utils.dpToPx(300))
                .withNavigationIconToggler(getToolbar())
                .build();
    }

    protected void setupToolbar() {
        if(toolbar != null){
            setSupportActionBar(toolbar);
            toolbar.setNavigationIcon(R.mipmap.ic_menu_white);
        }
    }

    public Toolbar getToolbar(){
        return  toolbar;
    }

    public ImageView getIvLogo(){
        return ivLogo;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onGlobalMenuHeaderClick(final View v) {
        drawerLayout.closeDrawer(Gravity.START);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                int[] startingLocation = new int[2];
                v.getLocationOnScreen(startingLocation);
                startingLocation[0] += v.getWidth() / 2;
                UserProfileActivity.startUserProfileFromLocation(startingLocation, BaseActivity.this);
                overridePendingTransition(0, 0);
            }
        }, 200);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        drawerLayout.closeDrawer(Gravity.START);
        Crouton.makeText(this, "点击" + i, Style.CONFIRM).show();
    }
}
