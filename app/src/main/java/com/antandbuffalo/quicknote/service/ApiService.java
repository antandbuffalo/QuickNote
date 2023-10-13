package com.antandbuffalo.quicknote.service;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
//    https://sample-node-api-antandbuffalo.vercel.app/
    @POST("/quicknote")
    Call<QuickNoteResponse> sendNote(@Body QuickNoteModel quickNoteModel);

    @GET("/quicknote")
    Call<QuickNoteResponse> getNote(@Query("id") String id);

    @POST("/quicknote/sync")
    Call<QuickNoteResponse> generatePasscode(@Body QuickNoteModel quickNoteModel);

    @POST("/quicknote/sync/validate")
    Call<QuickNoteResponse> validatePasscode(@Body QuickNoteModel quickNoteModel);

}