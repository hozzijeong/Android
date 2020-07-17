package com.example.t12_handler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/*
    한가지 사진을 계속해서 띄워줌
    버튼을 클릭하면 사진이 바뀜
    바뀐 사진이 5초뒤에 다시 원래사진으로 바뀜
    (단, 사진이 바뀌기 전에 버튼을 클릭하면 바뀌는 시간이 초기화됨)
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView timer;
    Button btn;
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timer = findViewById(R.id.timer);
        btn = findViewById(R.id.btn);
        iv = findViewById(R.id.Iv);
        btn.setOnClickListener(this);

        handler.sendEmptyMessageDelayed(0,1000);
    }


    int i;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) { // 핸들러 메세지 메서드를 오버라이딩
            super.handleMessage(msg);
            if(msg.what ==0){
                i++;
                timer.setText(i+"초");
               handler.sendEmptyMessageDelayed(0,1000);
            }else if(msg.what ==1){
                iv.setImageResource(R.drawable.screenshot_01);
            }
        }
    };



    @Override
    public void onClick(View v) {
        iv.setImageResource(R.drawable.screenshot_02);
        handler.removeMessages(1);
        handler.sendEmptyMessageDelayed(1,5000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeMessages(0);
    }

}
