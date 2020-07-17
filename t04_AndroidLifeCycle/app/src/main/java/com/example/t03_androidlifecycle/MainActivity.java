package com.example.t03_androidlifecycle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("lifeCycle","Main onCreate");
        findViewById(R.id.click).setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("lifeCycle","Main onStart");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("lifeCycle","Main onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("lifeCycle","Main onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("lifeCycle","Main onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("lifeCycle","Main onDestroy");
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.click){
            Intent intent = new Intent(this,com.example.t03_androidlifecycle.subActivity.class);
            startActivity(intent);
        }
    }
}
