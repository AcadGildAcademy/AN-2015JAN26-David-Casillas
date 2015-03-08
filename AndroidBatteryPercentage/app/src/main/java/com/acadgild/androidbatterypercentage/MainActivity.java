package com.acadgild.androidbatterypercentage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


public class MainActivity extends ActionBarActivity {

    public ToggleButton toggleButton;
    public CheckBox checkBox;
    public TextView batteryLevel;
    public WifiManager wifiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        toggleButton = (ToggleButton) findViewById(R.id.toggleButton);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        batteryLevel = (TextView) findViewById(R.id.batteryTV);

        wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        toggleButton.setChecked(wifiManager.isWifiEnabled());

        checkBox.setChecked(true);

        if (checkBox.isChecked()) {
            registerReceiver(WifiStateChangedReceiver, new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION));
        } else {
            unregisterReceiver(WifiStateChangedReceiver);
        }

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                wifiManager.setWifiEnabled(isChecked);
            }
        });

        registerReceiver(BatteryPercentage, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

    }

    private BroadcastReceiver WifiStateChangedReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            int extraWifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE ,
                    WifiManager.WIFI_STATE_UNKNOWN);

            switch(extraWifiState){
                case WifiManager.WIFI_STATE_DISABLED:
                    Toast.makeText(context,"WIFI STATE DISABLED", Toast.LENGTH_SHORT).show();
                    break;
                case WifiManager.WIFI_STATE_DISABLING:
                    Toast.makeText(context,"WIFI STATE DISABLING", Toast.LENGTH_SHORT).show();
                    break;
                case WifiManager.WIFI_STATE_ENABLED:
                    Toast.makeText(context,"WIFI STATE ENABLED", Toast.LENGTH_SHORT).show();
                    break;
                case WifiManager.WIFI_STATE_ENABLING:
                    Toast.makeText(context,"WIFI STATE ENABLING", Toast.LENGTH_SHORT).show();
                    break;
                case WifiManager.WIFI_STATE_UNKNOWN:
                    Toast.makeText(context,"WIFI STATE UNKNOWN", Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    };

    private BroadcastReceiver BatteryPercentage = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra("level", 0);
            batteryLevel.setText("Battery Level Remaining: " + Integer.toString(level) + "%");
        }
    };

}
