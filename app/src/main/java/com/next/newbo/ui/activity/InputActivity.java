package com.next.newbo.ui.activity;

import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.next.newbo.R;
import com.next.newbo.api.statuses.PostApi;

import butterknife.InjectView;

public class InputActivity extends BaseActivity {

    protected String mVersion = "";

    @InjectView(R.id.et_comment)
    EditText etComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        try {
            mVersion = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_input, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_send) {
            send();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void send() {
        if (!TextUtils.isEmpty(etComment.getText())) {
            new Uploader().execute();
        } else {
            Toast.makeText(this, getString(R.string.message_say_some_word), Toast.LENGTH_SHORT).show();
        }
    }

    protected boolean post() {
        return PostApi.newPost(etComment.getText().toString(), mVersion);
    }

    private class Uploader extends AsyncTask<Void, Void, Boolean> {

        private ProgressDialog pb_loading;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pb_loading = new ProgressDialog(InputActivity.this);
            pb_loading.setMessage(getString(R.string.message_please_wait));
            pb_loading.setCancelable(false);
            pb_loading.show();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            return post();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            pb_loading.dismiss();
            if (result) {
                finish();
            } else {
                Toast.makeText(InputActivity.this, getString(R.string.message_try_again), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
