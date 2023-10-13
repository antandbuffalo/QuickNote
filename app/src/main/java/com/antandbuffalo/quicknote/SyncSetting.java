package com.antandbuffalo.quicknote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;


import com.antandbuffalo.quicknote.utilities.Constants;
import com.antandbuffalo.quicknote.utilities.Util;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;


public class SyncSetting extends AppCompatActivity {
    AdView adView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync_setting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Switch autoSyncSwitch = findViewById(R.id.autoSyncSwitch);

        autoSyncSwitch.setChecked(Storage.getBoolean(getApplicationContext(), Constants.STORAGE_KEY_AUTO_SYNC, false));

        autoSyncSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Storage.putBoolean(getApplicationContext(), Constants.STORAGE_KEY_AUTO_SYNC, b);
                if (b) {
                    Util.sendData(getContentResolver(), getApplicationContext());
                }
            }
        });

        Button addDeviceButton = findViewById(R.id.addDevice);
        addDeviceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddNewDevice.class);
                startActivity(intent);
            }
        });

        Button addCurrentDeviceButton = findViewById(R.id.addCurrentDevice);
        addCurrentDeviceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddCurrentDevice.class);
                startActivity(intent);
            }
        });

        AdView adView = findViewById(R.id.adView2);
        Util.loadAd(getApplicationContext(), adView);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}