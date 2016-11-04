package com.can.store.androidbcc;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private final static int SDKVER_LOLLIPOP = 21;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_main);

        /**
         * 検索ボタンの押下
         */
        Button searchButton = (Button) findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("クリックされました。");
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        /**
         * カメラボタンの押下
         */
        Button cameraButton = (Button) findViewById(R.id.cameraButton);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= SDKVER_LOLLIPOP) {
                    System.out.println("Camera2が起動します。");
                    // Camera2を使ったActivityを開く.
                    Intent ittMainView_Camera2 = new Intent(MainActivity.this, CameraActivity.class);
                    // 次画面のアクティビティ起動
                    startActivity(ittMainView_Camera2);
                } else {
                    System.out.println("旧式が起動します。");
                    // Cameraを使ったActivityを開く.
                    Intent ittMainView_Camera = new Intent(MainActivity.this, OldCameraActivity.class);
                    // 次画面のアクティビティ起動
                    startActivity(ittMainView_Camera);
                }
            }
        });

        /**
         * バーコードボタンの押下
         */
        Button barcodeButton = (Button) findViewById(R.id.barcodeButton);
        barcodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("クリックされました。");
                Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
                startActivity(intent);
            }
        });


        /**
         * 画像のローダー
         * http://qiita.com/chuross/items/e3ca79065d9b67716ace
         */
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
            .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
            .memoryCacheSize(2 * 1024 * 1024)
            .build();
        ImageLoader.getInstance().init(config);

        final ImageView imageView = (ImageView) findViewById(R.id.mainImage);
        String imageUrl = "https://store-can.appspot.com/img/bcc/researcher/premium-350-292_2.jpg";
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        return true;
    }
}
