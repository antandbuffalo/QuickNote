package com.antandbuffalo.quicknote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.antandbuffalo.quicknote.service.DataHolder;
import com.antandbuffalo.quicknote.service.QuickNoteModel;
import com.antandbuffalo.quicknote.service.QuickNoteResponse;
import com.antandbuffalo.quicknote.utilities.Constants;
import com.antandbuffalo.quicknote.utilities.Util;
import com.google.android.gms.ads.AdView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCurrentDevice extends AppCompatActivity {
    EditText passcodeField;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_current_device);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        passcodeField = findViewById(R.id.passcodeField);
        Button addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validatePasscode();
            }
        });

        AdView adView = findViewById(R.id.adView4);
        Util.loadAd(getApplicationContext(), adView);
    }

    public void validatePasscode() {
        String passcode = passcodeField.getText().toString().trim();
        if(passcode.equalsIgnoreCase("")) {
            return;
        }
        QuickNoteModel quickNoteModel = new QuickNoteModel();
        quickNoteModel.passcode = passcode;
        Call<QuickNoteResponse> call = DataHolder.getDataHolder(this).apiService.validatePasscode(quickNoteModel);
        call.enqueue(new Callback<QuickNoteResponse>() {
            @Override
            public void onResponse(Call<QuickNoteResponse> call, Response<QuickNoteResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AddCurrentDevice.this, "Device added successfully", Toast.LENGTH_SHORT).show();
                    System.out.println("success api call");
                    Storage.putString(getApplicationContext(), Constants.STORAGE_KEY_UNIQUE_ID, response.body().getId());
                    Storage.putString(getApplicationContext(), Constants.STORAGE_KEY_TEXT, response.body().getText());
                    finish();
                } else {
                    // Handle the error.
                    Toast.makeText(AddCurrentDevice.this, "Error validating Passcode. Please try again", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<QuickNoteResponse> call, Throwable t) {
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