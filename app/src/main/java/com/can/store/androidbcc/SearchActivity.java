package com.can.store.androidbcc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
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
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SearchActivity extends Activity {

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

        if(productInfo == null){
            throw new MyException("productInfo is null.");
        }

        OptimazeResult optimazeResult = productInfo.getOptimazeResultList().get(0);
        for (int i = 0; i < 10; i++) {
            optimazeResult.setAsin(i + "aaaaaaaaaaaa");
            productInfo.getOptimazeResultList().add(optimazeResult);
        }
        Log.d("debug", productInfo.getOptimazeResultList().size() + "件あります。");
        ProductAdapter productAdapter = new ProductAdapter(SearchActivity.this, 0, productInfo.getOptimazeResultList(), R.id.deleteProductButton);

        ListView listView = (ListView)findViewById(R.id.productListView);
        listView.setAdapter(productAdapter);
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



    public class ProductAdapter extends ArrayAdapter<OptimazeResult> {
        private LayoutInflater layoutInflater_;
        private int mButton;

        public ProductAdapter(Context context, int textViewResourceId, List<OptimazeResult> objects, int button) {
            super(context, textViewResourceId, objects);
            layoutInflater_ = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mButton = button;
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {
            // 特定の行(position)のデータを得る
            OptimazeResult item = (OptimazeResult)getItem(position);

            // convertViewは使い回しされている可能性があるのでnullの時だけ新しく作る
            if (null == convertView) {
                convertView = layoutInflater_.inflate(R.layout.parts_product, null);
            }

            // CustomDataのデータをViewの各Widgetにセットする
            TextView title;
            title = (TextView)convertView.findViewById(R.id.title);
            title.setText(item.getTitle());
            Log.d("debug", item.getTitle());

            TextView asin;
            asin = (TextView)convertView.findViewById(R.id.asin);
            asin.setText(item.getAsin());
            Log.d("debug", item.getAsin());


            BootstrapButton btn = (BootstrapButton) convertView.findViewById(mButton);
            btn.setTag(position);
            /**
             * 画像のローダー
             * http://qiita.com/chuross/items/e3ca79065d9b67716ace
             */


            DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
                .imageScaleType(ImageScaleType.NONE)
                .build();
            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                    .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                    .memoryCacheSize(2 * 1024 * 1024)
                    .defaultDisplayImageOptions(displayImageOptions)
                    .build();
            ImageLoader.getInstance().init(config);

            final ImageView imageView = (ImageView) convertView.findViewById(R.id.productImage);
            ImageLoader loader = ImageLoader.getInstance();

            // loadImageを使う場合
            loader.loadImage(item.getImageUrl(), new SimpleImageLoadingListener() {
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    imageView.setImageBitmap(loadedImage);
                }
            });

            final ListView list =  (ListView) parent;
            BootstrapButton deleteProductButton = (BootstrapButton)convertView.findViewById(R.id.deleteProductButton);
            deleteProductButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    long id = getItemId(position);
                    Log.d("debug", getItem(position).getAsin());

                    // TODO: 2016/11/12 webviewを切り替え
                }
            });

            return convertView;
        }
    }




}
