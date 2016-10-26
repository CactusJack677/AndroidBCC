package com.can.store.androidbcc;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.amazonservices.mws.products.MarketplaceWebServiceProductsClient;
import com.amazonservices.mws.products.MarketplaceWebServiceProductsException;
import com.amazonservices.mws.products.model.ListMatchingProductsRequest;
import com.amazonservices.mws.products.model.ListMatchingProductsResponse;
import com.amazonservices.mws.products.model.ResponseHeaderMetadata;
import com.amazonservices.mws.products.samples.MarketplaceWebServiceProductsSampleConfig;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private final static int SDKVER_LOLLIPOP = 21;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        /**
         *
         */
        Button mwsTestButton = (Button) findViewById(R.id.mwsTestButton);
        mwsTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("クリックされました。");

                // Get a client connection.
                // Make sure you've set the variables in MarketplaceWebServiceProductsSampleConfig.
                MarketplaceWebServiceProductsClient client = MarketplaceWebServiceProductsSampleConfig.getClient();

                // Create a request.
                ListMatchingProductsRequest request = new ListMatchingProductsRequest();
                String sellerId = "A2SFOEUFUIPMQE";
                request.setSellerId(sellerId);
                String mwsAuthToken = "amzn.mws.8eab3ec9-c675-260c-7a5e-6ed54d1016ab";
                request.setMWSAuthToken(mwsAuthToken);
                String marketplaceId = "A1VC38T7YXB528";
                request.setMarketplaceId(marketplaceId);
                String query = "example";
                request.setQuery(query);
                String queryContextId = "example";
                request.setQueryContextId(queryContextId);


                try {
                    // Call the service.
                    ListMatchingProductsResponse response = client.listMatchingProducts(request);
                    ResponseHeaderMetadata rhmd = response.getResponseHeaderMetadata();
                    // We recommend logging every the request id and timestamp of every call.
                    System.out.println("Response:");
                    System.out.println("RequestId: "+rhmd.getRequestId());
                    System.out.println("Timestamp: "+rhmd.getTimestamp());
                    String responseXml = response.toXML();
                    System.out.println(responseXml);
                    response.getListMatchingProductsResult();
                } catch (MarketplaceWebServiceProductsException ex) {
                    // Exception properties are important for diagnostics.
                    System.out.println("Service Exception:");
                    ResponseHeaderMetadata rhmd = ex.getResponseHeaderMetadata();
                    if(rhmd != null) {
                        System.out.println("RequestId: "+rhmd.getRequestId());
                        System.out.println("Timestamp: "+rhmd.getTimestamp());
                    }
                    System.out.println("Message: "+ex.getMessage());
                    System.out.println("StatusCode: "+ex.getStatusCode());
                    System.out.println("ErrorCode: "+ex.getErrorCode());
                    System.out.println("ErrorType: "+ex.getErrorType());
                    throw ex;
                }


            }
        });


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
        FloatingActionButton cameraButton = (FloatingActionButton) findViewById(R.id.cameraButton);
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
