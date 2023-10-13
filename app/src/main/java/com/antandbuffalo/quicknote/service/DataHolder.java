package com.antandbuffalo.quicknote.service;

import android.content.Context;
import com.antandbuffalo.quicknote.R;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataHolder {
    private static DataHolder dataHolder;
    public ApiService apiService;

    private static ApiService createRetrofit(Context context) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(context.getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        return apiService;
    }

    private DataHolder() {
    }

    public static DataHolder getDataHolder(Context context) {
        if (dataHolder == null) {
            dataHolder = new DataHolder();
            dataHolder.apiService = createRetrofit(context);
        }
        return dataHolder;
    }
}
