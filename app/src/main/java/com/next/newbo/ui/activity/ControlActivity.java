package com.next.newbo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.next.newbo.R;
import com.next.newbo.cache.login.LoginApiCache;
import com.next.newbo.receiver.ConnectivityReceiver;
import com.next.newbo.utils.TimeUtils;

public class ControlActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);

        new ConnectivityReceiver();

        LoginApiCache loginApiCache = new LoginApiCache(this);
        if (needLogin(loginApiCache)) {
            loginApiCache.logout();
            Intent loginIntent = new Intent(this, LoginActivity.class);
            loginIntent.setAction(Intent.ACTION_MAIN);
            startActivity(loginIntent);
            finish();
        } else {
            Intent homePageIntent = new Intent(this, MainActivity.class);
            homePageIntent.setAction(Intent.ACTION_MAIN);
            homePageIntent.putExtra(Intent.EXTRA_INTENT, getIntent().
                    getIntExtra(Intent.EXTRA_INTENT, 0));
            startActivity(homePageIntent);
            finish();
        }

    }

    private boolean needLogin(LoginApiCache loginApiCache) {
        return TextUtils.isEmpty(loginApiCache.getAccessToken()) ||
                TimeUtils.isTokenExpire(loginApiCache.getExpireDate());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_control, menu);
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
}
