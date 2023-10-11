package com.antandbuffalo.quicknote.service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
//    https://sample-node-api-antandbuffalo.vercel.app/
    @POST("/quicknote")
    Call<QuickNoteResponse> sendNote(@Body QuickNoteModel quickNoteModel);
}