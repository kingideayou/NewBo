package com.next.newbo.ui.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.afollestad.materialdialogs.AlertDialogWrapper;
import com.next.newbo.BaseApi;
import com.next.newbo.R;
import com.next.newbo.api.PrivateKey;
import com.next.newbo.cache.LoginApiCache;
import com.next.newbo.utils.AppLogger;
import com.next.newbo.utils.TimeUtils;

import butterknife.InjectView;

public class LoginActivity extends BaseActivity {

    @InjectView(R.id.login_web)
    WebView webView;

    private LoginApiCache loginApiCache;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginApiCache = new LoginApiCache(this);

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSaveFormData(false);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        webView.setWebViewClient(new MyWebViewClient());
        webView.loadUrl(PrivateKey.getOauthLoginPage());
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (PrivateKey.isUrlRedirected(url)) {
                view.stopLoading();
                handleRedirectedUrl(url);
            } else {
                view.loadUrl(url);
            }
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            if (PrivateKey.isUrlRedirected(url)) {
                view.stopLoading();
                handleRedirectedUrl(url);
            }
            super.onPageStarted(view, url, favicon);
        }
    }

    private void handleRedirectedUrl(String url) {
        if (!url.contains(("error"))) {
            int tokenIndex = url.indexOf("access_token=");
            int expiresIndex = url.indexOf("expires_in=");
            String token = url.substring(tokenIndex + 13, url.indexOf("&", tokenIndex));
            String expiresIn = url.substring(expiresIndex + 11, url.indexOf("&", expiresIndex));
            //TODO 使用 token 和 expiresIn 登录
            new LoginTask().execute(token, expiresIn);
        } else {
            showLoginFailure();
        }
    }

    private void showLoginFailure() {
        new AlertDialogWrapper.Builder(this)
                .setMessage(R.string.message_login_failure)
                .setCancelable(true)
                .setNegativeButton(R.string.OK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private class LoginTask extends AsyncTask<String, Void, Void> {

        private ProgressDialog progDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progDialog = new ProgressDialog(LoginActivity.this);
            progDialog.setMessage(getResources().getString(R.string.please_wait));
            progDialog.setCancelable(false);
            progDialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            loginApiCache.login(params[0], params[1]);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            progDialog.dismiss();
            if(!TextUtils.isEmpty(loginApiCache.getAccessToken())) {
                AppLogger.i("Access Token : " + loginApiCache.getAccessToken());
                AppLogger.i("Expire Date : " + loginApiCache.getExpireDate());
                loginApiCache.cache();
                BaseApi.setAccessToken(loginApiCache.getAccessToken());

                String msg = String.format(getResources().getString(R.string.expires_in),
                        TimeUtils.expireTimeInDays(loginApiCache.getExpireDate()));
                new AlertDialogWrapper.Builder(LoginActivity.this)
                        .setTitle(R.string.notify_title)
                        .setMessage(msg)
                        .setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.setAction(Intent.ACTION_MAIN);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .show();
            } else {
                showLoginFailure();
            }
        }
    }
}
