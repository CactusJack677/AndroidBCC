package com.can.store.androidbcc;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Set;

public class DeviceListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);

        ArrayAdapter pairedDeviceAdapter = new ArrayAdapter(this, R.layout.parts_device);
        BluetoothAdapter mBtAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> bondedDevices = mBtAdapter.getBondedDevices();
        if(bondedDevices.size() > 0){
            //接続履歴のあるデバイスが存在する
            for(BluetoothDevice device : bondedDevices){
                pairedDeviceAdapter.add(device.getName() + "\n" + device.getAddress());
            }
            ListView deviceList = (ListView)findViewById(R.id.deviceList);
            deviceList.setAdapter(pairedDeviceAdapter);
        }

    }
}
