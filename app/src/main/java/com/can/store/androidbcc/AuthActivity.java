package com.can.store.androidbcc;

import android.app.Activity;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.can.store.androidbcc.exception.MyException;
import com.can.store.androidbcc.util.CypherUtil;
import com.can.store.androidbcc.util.StackTraceUtil;
import com.can.store.androidbcc.util.StringUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

public class AuthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);


        /**
         * 検索ボタンの押下
         */
        BootstrapButton authButton = (BootstrapButton) findViewById(R.id.authButton);
        authButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BootstrapEditText mailAddress = (BootstrapEditText) findViewById(R.id.mailAddress);
                BootstrapEditText password = (BootstrapEditText) findViewById(R.id.password);

                Log.d("mailAddress", mailAddress.getText().toString());
                Log.d("password", password.getText().toString());

                // TODO: 2016/11/11
                mailAddress.setText("kamui_ace@yahoo.co.jp");
                password.setText("mbmp7566");

                Uri.Builder builder = new Uri.Builder();
                builder.scheme("https");
                builder.authority("store-can.appspot.com");
                builder.path("/api/bcc/auth");
                builder.appendQueryParameter("mailAddress", mailAddress.getText().toString());
                builder.appendQueryParameter("password", password.getText().toString());
                AsyncHttpRequest task = new AsyncHttpRequest(AuthActivity.this);
                task.execute(builder);


            }
        });


    }


    public class AsyncHttpRequest extends AsyncTask<Uri.Builder, Void, String> {

        private Activity mainActivity;

        public AsyncHttpRequest(Activity activity) {

            // 呼び出し元のアクティビティ
            this.mainActivity = activity;
        }

        // このメソッドは必ずオーバーライドする必要があるよ
        // ここが非同期で処理される部分みたいたぶん。
        @Override
        protected String doInBackground(Uri.Builder... builder) {
            try {
                // TODO: 2016/1こで商品検索
                Log.d("builder", builder[0].build().toString());
                String url = builder[0].build().toString();
                if (StringUtil.isEmpty(url)) {
                    return null;
                }
                Connection.Response response = Jsoup.connect(url).execute();

                Log.d("body", response.body());

                ObjectMapper mapper = new ObjectMapper();
                Ret ret = mapper.readValue(response.body(), Ret.class);

                if (ret.getStatus().equals("success")) {
                    // TODO: 2016/11/11 BmMwsPropertyを取得
                    String rmUserId = CypherUtil.decodeBrowfish(ret.getObj());
                    Log.d("rmUserId", rmUserId);

                    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(AuthActivity.this);
                    sp.edit().putString("rmUserId", rmUserId).commit();

                    String aaa = sp.getString("rmUserId", "aaaaa");
                    Log.d("aaa", aaa);

                    return "success";
                } else {
                    return "error";
                }

            } catch (Exception e) {
                e.printStackTrace();
                throw new MyException(StackTraceUtil.toString(e));
            }
        }


        // このメソッドは非同期処理の終わった後に呼び出されます
        @Override
        protected void onPostExecute(String result) {
            // 取得した結果をテキストビューに入れちゃったり
            Log.d("コールバック", result);


            if (result.equals("success")) {
                Toast.makeText(AuthActivity.this, "認証が正常にできました。", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(AuthActivity.this, "正しいメールアドレス、パスワードを入力してください。", Toast.LENGTH_LONG).show();
            }
        }
    }


    private static class Ret {
        private String status;
        private String msg;
        private String obj;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getObj() {
            return obj;
        }

        public void setObj(String obj) {
            this.obj = obj;
        }
    }
}
