package com.antandbuffalo.quicknote.utilities;

import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;

import com.antandbuffalo.quicknote.Storage;
import com.antandbuffalo.quicknote.service.DataHolder;
import com.antandbuffalo.quicknote.service.QuickNoteModel;
import com.antandbuffalo.quicknote.service.QuickNoteResponse;

import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Util {
    public static String getUniqueId(ContentResolver contentResolver, Context context) {
        String uniqueIdFromStorage = Storage.getString(context, Constants.STORAGE_KEY_UNIQUE_ID, null);
        if (uniqueIdFromStorage != null) {
            return uniqueIdFromStorage;
        }
        String androidId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID);
        if (androidId != null) {
            Storage.putString(context, Constants.STORAGE_KEY_UNIQUE_ID, androidId);
            return androidId;
        }
        String uniqueID = UUID.randomUUID().toString();
        Storage.putString(context, Constants.STORAGE_KEY_UNIQUE_ID, uniqueID);
        return uniqueID;
    }

    public static void sendData(ContentResolver contentResolver, Context context) {
        QuickNoteModel quickNoteModel = new QuickNoteModel();
        quickNoteModel.id = Util.getUniqueId(contentResolver, context);
        quickNoteModel.text = Storage.getString(context, Constants.STORAGE_KEY_TEXT, "");
        Call<QuickNoteResponse> call = DataHolder.getDataHolder().apiService.sendNote(quickNoteModel);
        call.enqueue(new Callback<QuickNoteResponse>() {
            @Override
            public void onResponse(Call<QuickNoteResponse> call, Response<QuickNoteResponse> response) {
                if (response.isSuccessful()) {
                    // Use response.body() to get the created user.
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
}
