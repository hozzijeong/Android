package com.example.t40_retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ServerAPI {
    @GET()
    Call<String> movieList(@Query("key")String key, @Query("targetDt")String date);
}
