package com.next.newbo.ui.activity;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.next.newbo.R;
import com.next.newbo.Utils;
import com.next.newbo.ui.utils.DrawerLayoutInstaller;
import com.next.newbo.ui.view.GlobalMenuView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;

/**
 * Created by NeXT on 15-3-27.
 */
public class BaseActivity extends ActionBarActivity implements GlobalMenuView.OnHeaderClickListener {

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

    protected void setupDrawer() {
        //TODO 添加 DrawerLayout 相关内容
        GlobalMenuView menuView = new GlobalMenuView(this);
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
    public void onGloableMenuHeaderClick(View v) {
        Toast.makeText(getApplicationContext(), "点我！", Toast.LENGTH_SHORT).show();
    }
}
