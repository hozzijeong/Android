package com.example.animation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

/*
    1. xml파일로 애니메이션 생성
    2. 자바코드에서 애니메이션 등록
    3. 필요에 따라 리스너 장착
 */
public class MainActivity extends AppCompatActivity {
    ImageView mainIv;
    Button mainBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainBtn = findViewById(R.id.main_btn);
        mainIv = findViewById(R.id.main_iv);


        mainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Animation moveAni = AnimationUtils.loadAnimation(MainActivity.this,R.anim.scale);

            moveAni.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    mainIv.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            mainIv.startAnimation(moveAni);

            }
        });

    }
}
