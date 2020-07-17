package com.example.handler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tv;
    Button bt;
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.tv);
        bt = findViewById(R.id.bt);
        iv = findViewById(R.id.iv);

        bt.setOnClickListener(this);
        handler.sendEmptyMessageDelayed(0, 1000);
    }
    int i =0;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if(msg.what == 0) {
                i++;
                tv.setText(i + "초");
                handler.sendEmptyMessageDelayed(0, 1000);
            }else if(msg.what == 5){
                iv.setImageResource(R.drawable.image1);
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeMessages(0);
    }

    @Override
    public void onClick(View v) {
        iv.setImageResource(R.drawable.image2);
        handler.removeMessages(5);
        handler.sendEmptyMessageDelayed(5,5000);
    }
}
