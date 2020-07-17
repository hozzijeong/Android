package com.example.t37_animation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    Button run;
    ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        run = findViewById(R.id.main_run);
        iv = findViewById(R.id.main_iv);

        run.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("mood","click");
                Animation ani = AnimationUtils.loadAnimation(MainActivity.this,R.anim.rotate);
                iv.startAnimation(ani);
            }
        });
    }
}
