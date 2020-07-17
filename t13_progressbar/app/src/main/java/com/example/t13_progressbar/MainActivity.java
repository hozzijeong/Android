package com.example.t13_progressbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn;
    ProgressBar pro1,pro2;
    int i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.progress_btn);
        pro1 = findViewById(R.id.progress1);
        pro2 = findViewById(R.id.progress2);

        pro2.setMax(100);
        pro1.setVisibility(View.INVISIBLE);
        btn.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        handler.sendEmptyMessageDelayed(1,1000);
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
                if(i<=100){
                    i++;
                    pro2.setProgress(i);
                    pro1.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(),"다운로드중...\n\t"+i+"%",Toast.LENGTH_SHORT).show();
                    handler.sendEmptyMessageDelayed(1,1000);
                }else{
                    handler.removeMessages(1);
                    pro1.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(),"끝났습니다...",Toast.LENGTH_LONG).show();
                }
            }
    };
}
