package com.antandbuffalo.quicknote;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.antandbuffalo.quicknote.utilities.Constants;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class About extends AppCompatActivity {
    AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);

        TextView version = (TextView) findViewById(R.id.version);
        version.setText("Version " + BuildConfig.VERSION_NAME + " (" + BuildConfig.VERSION_CODE + ")");
        loadAd();
        Button contactUs = (Button) findViewById(R.id.contactUs);
        contactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                emailIntent.setType("vnd.android.cursor.item/email");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{Constants.FEEDBACK_EMAIL});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, Constants.FEEDBACK_EMAIL_SUBJECT + " v" + BuildConfig.VERSION_NAME + " (" + BuildConfig.VERSION_CODE + ")");
                startActivity(Intent.createChooser(emailIntent, Constants.FEEDBACK_EMAIL_POPUP_MESSAGE));
            }
        });

        Button share = (Button) findViewById(R.id.share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create the sharing intent
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = Constants.PLAY_STORE_URL;
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share..."));
            }
        });
    }

    public void loadAd() {
        mAdView = this.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdView.resume();
    }

    @Override
    public void onPause() {
        // Pause the AdView.
        mAdView.pause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        // Destroy the AdView.
        mAdView.destroy();
        super.onDestroy();
    }
}
