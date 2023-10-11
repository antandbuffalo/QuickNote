package com.antandbuffalo.quicknote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.antandbuffalo.quicknote.service.DataHolder;
import com.antandbuffalo.quicknote.service.QuickNoteModel;
import com.antandbuffalo.quicknote.utilities.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SyncSetting extends AppCompatActivity {
    Context currentContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync_setting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Switch autoSyncSwitch = findViewById(R.id.autoSyncSwitch);

        autoSyncSwitch.setChecked(Storage.getBoolean(currentContext, Constants.STORAGE_KEY_AUTO_SYNC, false));

        autoSyncSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Storage.putBoolean(currentContext, Constants.STORAGE_KEY_AUTO_SYNC, b);
                sendData();
            }
        });

        Button addDeviceButton = findViewById(R.id.addDevice);
        addDeviceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(currentContext, AddNewDevice.class);
                startActivity(intent);
            }
        });

        Button addCurrentDeviceButton = findViewById(R.id.addCurrentDevice);
        addCurrentDeviceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(currentContext, AddCurrentDevice.class);
                startActivity(intent);
            }
        });
    }

    public void sendData() {
        QuickNoteModel quickNoteModel = new QuickNoteModel();
        quickNoteModel.id = "123";
        quickNoteModel.text = "sample";
        Call<String> call = DataHolder.getDataHolder().apiService.sendNote(quickNoteModel);
        System.out.println(call.request());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                System.out.println("success api call" + response.message());
                if (response.isSuccessful()) {
                    // Use response.body() to get the created user.
                    System.out.println("success api call");
                } else {
                    // Handle the error.
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                // Handle the failure.
                System.out.println("failure api call" + t);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}