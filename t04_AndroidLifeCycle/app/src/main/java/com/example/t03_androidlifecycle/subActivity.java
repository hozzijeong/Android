package com.example.t03_androidlifecycle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class subActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        Log.d("lifeCycle","Sub onCreate");

        findViewById(R.id.back).setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("lifeCycle","Sub onStart");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("lifeCycle","Sub onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("lifeCycle","Sub onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("lifeCycle","Sub onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("lifeCycle","Sub onDestroy");
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.back){
            finish();
        }
    }
}
