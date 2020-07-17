package com.example.t29_glide;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {
    ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv = findViewById(R.id.iv);
        String url = "https://w7.pngwing.com/pngs/995/430/png-transparent-three-despicable-me-minions-illustration-bob-the-minion-stuart-the-minion-universal-s-despicable-me-minion-rush-minions-minions-heroes-film-stuart-the-minion.png";
        Glide.with(this)
                .load(url)
                .into(iv);
    }
}
