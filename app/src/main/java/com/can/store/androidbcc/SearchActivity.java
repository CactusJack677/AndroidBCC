package com.can.store.androidbcc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.can.store.androidbcc.Const.BaseEnum;
import com.can.store.androidbcc.Const.ParentNode;
import com.can.store.androidbcc.dto.OptimazeResult;
import com.can.store.androidbcc.dto.ProductInfo;
import com.can.store.androidbcc.exception.MyException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SearchActivity extends Activity {

    @BindView(R.id.searchButton) Button button1;
    List<OptimazeResult> optimazeResultList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        Log.d("info", "遷移しました");

        Intent intent = getIntent();
        String result = intent.getStringExtra("result");

        //データ取得
        Gson gson = new GsonBuilder()
            .registerTypeHierarchyAdapter(ParentNode.class, new JsonDeserializer<BaseEnum>(){
                @Override
                public ParentNode deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
                    ParentNode[] scopes = ParentNode.values();
                    for (ParentNode scope : scopes) {
                        JsonObject jsonObject = json.getAsJsonObject();
                        if (scope.toString().equals(jsonObject.get("value").getAsString())){
                            return scope;
                        }
                    }
                    return ParentNode.JP_UNKNOWN;
                }
            })
            .setDateFormat("yyyy/MM/dd")
            .excludeFieldsWithoutExposeAnnotation()
            .create();

        ProductInfo productInfo = gson.fromJson(result, ProductInfo.class);

        // TODO: 2016/11/04 テーブルレイアウト

        ProductAdapter productAdapter = new ProductAdapter();

        ListView listView = (ListView)findViewById(R.id.productListView);
        listView.setAdapter(productAdapter);
        optimazeResultList = productInfo.getOptimazeResultList();
        for (OptimazeResult optimazeResult : optimazeResultList) {
        }
        productAdapter.notifyDataSetChanged();


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

    private class ProductAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return optimazeResultList.size();
        }

        @Override
        public Object getItem(int position) {
            return optimazeResultList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            if(view == null){
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                view = inflater.inflate(R.layout.parts_product, null);
            }

            OptimazeResult optimazeResult = (OptimazeResult)getItem(i);

            TextView title = (TextView) view.findViewById(R.id.title);
            TextView price = (TextView) view.findViewById(R.id.price);

            title.setText(optimazeResult.getTitle());

            return view;
        }
    }


}
