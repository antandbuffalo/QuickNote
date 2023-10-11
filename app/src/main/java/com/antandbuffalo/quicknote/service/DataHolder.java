package com.antandbuffalo.quicknote.service;

import com.antandbuffalo.quicknote.utilities.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataHolder {
    private static DataHolder dataHolder;
    public ApiService apiService;

    private static ApiService createRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.SYNC_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        return apiService;
    }
    private DataHolder() {
    }

    public static  DataHolder getDataHolder() {
        if (dataHolder == null) {
            dataHolder = new DataHolder();
            dataHolder.apiService = createRetrofit();
        }
        return dataHolder;
    }
}
