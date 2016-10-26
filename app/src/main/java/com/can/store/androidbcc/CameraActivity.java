package com.can.store.androidbcc;

import android.hardware.Camera;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Locale;


public class CameraActivity extends FragmentActivity {
    private SurfaceView mySurfaceView;
    private Camera myCamera; //hardware


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        //テーブルの追加
        TableLayout tableLayout = (TableLayout)findViewById(R.id.productTable);
        //テーブルに行を追加
        for (int i=0; i<3; i++) {
            // 行を追加
            TableRow tableRow = (TableRow) View.inflate(CameraActivity.this, R.layout.parts_table_product, null);
            tableLayout.addView(tableRow);

        }


        //SurfaceView
        mySurfaceView = (SurfaceView)findViewById(R.id.surface_view);

        //SurfaceHolder(SVの制御に使うInterface）
        SurfaceHolder holder = mySurfaceView.getHolder();
        //コールバックを設定
        holder.addCallback(callback);


    }

    //コールバック
    private SurfaceHolder.Callback callback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder) {

            //CameraOpen
            myCamera = Camera.open();

            //出力をSurfaceViewに設定
            try{
                myCamera.setPreviewDisplay(surfaceHolder);
            }catch(Exception e){
                e.printStackTrace();
            }

        }

        @Override
        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {

            //プレビュースタート（Changedは最初にも1度は呼ばれる）
            myCamera.startPreview();

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

            //片付け
            myCamera.release();
            myCamera = null;
        }
    };
}
