package com.can.store.androidbcc;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.can.store.androidbcc.exception.MyException;
import com.can.store.androidbcc.util.StackTraceUtil;
import com.can.store.androidbcc.util.StringUtil;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;

public class MainActivity extends AppCompatActivity {

    private final static int SDKVER_LOLLIPOP = 21;
    private final static int BLUETOOTH_REQUEST = 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_main);

        //Bootstrapアイコンセットのロード
        TypefaceProvider.registerDefaultIconSets();

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        String rmUserId = sp.getString("rmUserId", "aaaaa");
        Log.d("rmUserId", rmUserId);

        BluetoothAdapter Bt = BluetoothAdapter.getDefaultAdapter();
        if (Bt != null) {
            boolean btEnable = Bt.isEnabled();
            if(btEnable == true){
                Log.d("debug", "BluetoothがONになっています。");
                //BluetoothがONだった場合の処理
            }else{
                Log.d("debug", "BluetoothがONになっていません。");
                //OFFだった場合、ONにすることを促すダイアログを表示する画面に遷移
                Intent btOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(btOn, BLUETOOTH_REQUEST);
            }
        }else{
            Log.d("debug", "Bluetoothが利用できない端末です。");
        }

        /**
         * 検索ボタンの押下
         */
        BootstrapButton authButton = (BootstrapButton) findViewById(R.id.authButton);
        authButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("クリックされました。");
                Intent intent = new Intent(MainActivity.this, AuthActivity.class);
                startActivity(intent);
            }
        });

        /**
         * 検索ボタンの押下
         */
        BootstrapButton settingBtn = (BootstrapButton) findViewById(R.id.settingBtn);
        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("クリックされました。");
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        /**
         * MWSテスト用
         */
        BootstrapButton mwsTestButton = (BootstrapButton) findViewById(R.id.mwsTestButton);
        mwsTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("クリックされました。");
                Uri.Builder builder = new Uri.Builder();
                builder.scheme("https");
                builder.authority("store-can.appspot.com");
                builder.path("/api/bcc/GetProductInfoForJan");
                builder.appendQueryParameter("idList", "4210201075592");
                AsyncHttpRequest task = new AsyncHttpRequest(MainActivity.this);
                task.execute(builder);
            }
        });

        /**
         * カメラボタンの押下
         */
        BootstrapButton cameraButton = (BootstrapButton) findViewById(R.id.cameraButton);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
                integrator.setCameraId(0);
                integrator.setPrompt("Scan a barcode");
                integrator.initiateScan();

                    // 次画面のアクティビティ起動
//                    startActivity(barcodeIntent);
//                if (Build.VERSION.SDK_INT >= SDKVER_LOLLIPOP) {
//                    System.out.println("Camera2が起動します。");
//                    // Camera2を使ったActivityを開く.
//                    Intent ittMainView_Camera2 = new Intent(MainActivity.this, CameraActivity.class);
//                    // 次画面のアクティビティ起動
//                    startActivity(ittMainView_Camera2);
//                } else {
//                    System.out.println("旧式が起動します。");
//                    // Cameraを使ったActivityを開く.
//                    Intent ittMainView_Camera = new Intent(MainActivity.this, OldCameraActivity.class);
//                    // 次画面のアクティビティ起動
//                    startActivity(ittMainView_Camera);
//                }
            }
        });


        /**
         * 画像のローダー
         * http://qiita.com/chuross/items/e3ca79065d9b67716ace
         */


        DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
            .imageScaleType(ImageScaleType.EXACTLY)
            .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
            .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
            .memoryCacheSize(2 * 1024 * 1024)
            .defaultDisplayImageOptions(displayImageOptions)
            .build();
        ImageLoader.getInstance().init(config);


        final ImageView imageView = (ImageView) findViewById(R.id.mainImage);
        String imageUrl = "https://store-can.appspot.com/lp/d/images/main_img.jpg";
        ImageLoader loader = ImageLoader.getInstance();
        // loadImageを使う場合
        loader.loadImage(imageUrl, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                imageView.setImageBitmap(loadedImage);
            }
        });

        //


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("requestCode", String.valueOf(requestCode));
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == BLUETOOTH_REQUEST){
            if(resultCode == Activity.RESULT_OK){
                //BluetoothがONにされた場合の処理
                Log.d("debug", "BluetoothをONにしてもらえました。");
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);


            }else{
                Log.d("debug", "BluetoothがONにしてもらえませんでした。");
                finish();
            }
        }else{
            IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (scanResult != null) {
                String jan = scanResult.getContents();
                Log.d("scan", "==-----:  " + jan);
                Uri.Builder builder = new Uri.Builder();
                builder.scheme("https");
                builder.authority("store-can.appspot.com");
                builder.path("/api/bcc/GetProductInfoForJan");
                if(StringUtil.isNotEmpty(jan)){
                    builder.appendQueryParameter("idList", jan);
                    AsyncHttpRequest task = new AsyncHttpRequest(this);
                    task.execute(builder);
                }
            }

        }
    }


    public class AsyncHttpRequest extends AsyncTask<Builder, Void, String> {

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
                if(StringUtil.isEmpty(url)){
                    return null;
                }
                Response response = Jsoup.connect(url).execute();

                Log.d("body", response.body());

                // TODO: 2016/11/04 検索結果を別のアクティビティに渡す

                return response.body();
            } catch (Exception e){
                e.printStackTrace();
                throw new MyException(StackTraceUtil.toString(e));
            }
        }


        // このメソッドは非同期処理の終わった後に呼び出されます
        @Override
        protected void onPostExecute(String result) {
            // 取得した結果をテキストビューに入れちゃったり
            Log.d("コールバック", result);


            System.out.println("クリックされました。");
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            intent.putExtra("result", result);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        // メニューの要素を追加
        menu.add("Normal item");

        // メニューの要素を追加して取得
        MenuItem actionItem = menu.add("Action Button");

        // SHOW_AS_ACTION_IF_ROOM:余裕があれば表示
        actionItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        // アイコンを設定
        actionItem.setIcon(android.R.drawable.ic_menu_share);

        // メニューの要素を追加して取得
        MenuItem aaaaItem = menu.add("aaaa Button");

        // SHOW_AS_ACTION_IF_ROOM:余裕があれば表示
        aaaaItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        // アイコンを設定
        aaaaItem.setIcon(android.R.drawable.ic_dialog_alert);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Toast.makeText(this, "Selected Item: " + item.getTitle(), Toast.LENGTH_SHORT).show();
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
