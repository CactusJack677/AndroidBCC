package com.can.store.androidbcc;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

import com.can.store.androidbcc.exception.MyException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search);

        Log.d("info", "遷移しました");



        final Button searchButton = (Button) findViewById(R.id.searchButton);

        searchButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                System.out.println("クリックされました。");
                final EditText productCode = (EditText) findViewById(R.id.productCode);
                String val = productCode.getText().toString();
                Log.d("debug", "param=" + val);

                final AsyncJob asynctask = new AsyncJob(SearchActivity.this);
                //実行
                asynctask.execute(val,"B","C");

            }
        });

    }


    public class AsyncJob extends AsyncTask<String,String,String> {

        private SearchActivity _main;

        public AsyncJob(SearchActivity main) {
            super();
            _main = main;
        }


        @Override
        protected String doInBackground(String...value) {
            Log.d("debug", "doInBackground");
            String  productCd = value[0];//executeで渡される"A"
            String  arg2 = value[1];//executeで渡される"B"
            String  arg3 = value[2];//executeで渡される"C"
            String  res  = "";

            Log.d("debug", "productCd>" + productCd);

            WebView webView1 = (WebView) findViewById(R.id.webView1);
            String data = "";
            Document doc = null;
            try {
                doc = Jsoup.connect("http://mnrate.com/item/aid/B00457WDCC").get();

            }catch (Exception e){
                e.printStackTrace();
                throw new MyException(e);

            }
            return doc
                .outerHtml()
                .replace("\"￥\"", "\"yen\"")
                .replaceAll("href=\"", "href=\"http:\\/\\/mnrate\\.com\\/s\\/")
                .replaceAll("script src=\"", "script src=\"http:\\/\\/mnrate\\.com\\/s\\/")
                .replace("http://mnrate.com/s///ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js", "http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js")
                .replace("http://mnrate.com/s/http://www.google.com/jsapi", "http://www.google.com/jsapi")
                ;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            Log.d("debug", "onProgressUpdate");
            WebView webView1 = (WebView) findViewById(R.id.webView1);
            webView1.getSettings().setJavaScriptEnabled(true);
            webView1.loadData(values[0], "text/html", "UTF-8");
            //

        }

        @Override
        protected void onPostExecute(String html) {
            Log.d("debug", "onPostExecute");
            Log.d("doc.outerHtml()", html);
            WebView webView1 = (WebView) findViewById(R.id.webView1);
            webView1.getSettings().setJavaScriptEnabled(true);
            webView1.loadData(html, "text/html; charset=utf-8", "utf-8");
        }


    }
}
