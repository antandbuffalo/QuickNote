package com.antandbuffalo.quicknote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.antandbuffalo.quicknote.service.DataHolder;
import com.antandbuffalo.quicknote.service.QuickNoteModel;
import com.antandbuffalo.quicknote.service.QuickNoteResponse;
import com.antandbuffalo.quicknote.utilities.Constants;
import com.antandbuffalo.quicknote.utilities.Util;
import com.google.android.gms.ads.AdView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddNewDevice extends AppCompatActivity {
    Context currentContext;
    TextView passcodeText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_device);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        passcodeText = findViewById(R.id.passcode);
        generatePasscode(getContentResolver(), this);

        TextView webAppUrlView = findViewById(R.id.web_app_url);
        webAppUrlView.setPaintFlags(webAppUrlView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        webAppUrlView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.WEB_APP_URL));
                startActivity(browserIntent);
            }
        });

        AdView adView = findViewById(R.id.adView3);
        Util.loadAd(getApplicationContext(), adView);
    }

    public void generatePasscode(ContentResolver contentResolver, Context context) {
        QuickNoteModel quickNoteModel = new QuickNoteModel();
        quickNoteModel.id = Util.getUniqueId(contentResolver, context);
        quickNoteModel.text = Storage.getString(context, Constants.STORAGE_KEY_TEXT, "");
        Call<QuickNoteResponse> call = DataHolder.getDataHolder(this).apiService.generatePasscode(quickNoteModel);
        call.enqueue(new Callback<QuickNoteResponse>() {
            @Override
            public void onResponse(Call<QuickNoteResponse> call, Response<QuickNoteResponse> response) {
                if (response.isSuccessful()) {
                    passcodeText.setText(response.body().getPasscode());
                    System.out.println("success api call");
                } else {
                    // Handle the error.
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