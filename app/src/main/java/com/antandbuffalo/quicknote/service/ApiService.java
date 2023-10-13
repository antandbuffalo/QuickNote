package com.antandbuffalo.quicknote.service;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
//    https://sample-node-api-antandbuffalo.vercel.app/
    @POST("/quicknote")
    Call<QuickNoteResponse> sendNote(@Body QuickNoteModel quickNoteModel);

    @POST("/quicknote/sync")
    Call<QuickNoteResponse> generatePasscode(@Body QuickNoteModel quickNoteModel);

    @POST("/quicknote/sync/validate")
    Call<QuickNoteResponse> validatePasscode(@Body QuickNoteModel quickNoteModel);
}