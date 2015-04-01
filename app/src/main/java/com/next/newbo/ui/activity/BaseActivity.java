package com.next.newbo.ui.activity;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.next.newbo.R;
import com.next.newbo.Utils;
import com.next.newbo.ui.utils.DrawerLayoutInstaller;
import com.next.newbo.ui.view.GlobalMenuView;

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

    protected void setupDrawer() {
        //TODO 添加 DrawerLayout 相关内容
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
    public void onGloableMenuHeaderClick(View v) {
        drawerLayout.closeDrawer(Gravity.START);
        Crouton.makeText(this, "点击用户", Style.CONFIRM).show();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        drawerLayout.closeDrawer(Gravity.START);
        Crouton.makeText(this, "点击" + i, Style.CONFIRM).show();
    }
}
