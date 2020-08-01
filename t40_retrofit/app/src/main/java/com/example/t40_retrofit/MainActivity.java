package com.example.t40_retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    final String base_url = "http://www.kobis.or.kr/";
    Retrofit retrofit;
    ServerAPI serverAPI;
    private Call<String> movieList;
    final String API_KEY = "29c79dc61260e9382ace4470e9f9b80c";
    String date = "20180818";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRetrofit();
        callMovieList();
    }
    private void setRetrofit(){
        retrofit = new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        serverAPI = retrofit.create(ServerAPI.class);
    }

    private void callMovieList(){
        movieList = serverAPI.movieList(API_KEY,date);
        movieList.enqueue(RetrofitCallBack);
    }

    Callback<String > RetrofitCallBack = new Callback<String>() {
        @Override
        public void onResponse(Call<String> call, Response<String> response) {
            String result = response.body();
            Log.d("mood",result);
        }

        @Override
        public void onFailure(Call<String> call, Throwable t) {

        }
    };


}