package com.example.intro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

/*
    이미지 한개가 존재하며
    오른쪽으로 이동하면서 크기가 절반 작아졌다가
    왼쪽으로 복귀하면서 크기가 원상 복귀
 */



public class MainActivity extends AppCompatActivity {
    ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv = findViewById(R.id.main_iv);
        Log.d("mood","mainActivity");
        Animation ani = AnimationUtils.loadAnimation(this,R.anim.set);

        iv.setAnimation(ani);
    }
}
