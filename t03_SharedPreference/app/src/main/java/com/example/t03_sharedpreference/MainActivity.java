package com.example.t03_sharedpreference;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.DataOutput;

/*
    <SharedPreference>  ->  임시 저장 하는 기능
    1. 임시 저장할 데이터 선언 ( 여기서는 edittext에 적힌 글을 앱을 종료해도 다음번 실행 시 나타나도록 설정)
    2. 공유할 이름 설정
    3.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //OnCreate는 맨 처음 앱이 실행됬을때 제일 처음으로 샐행되는 메서드를 의미
    EditText et_01;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.save).setOnClickListener(this);
        findViewById(R.id.load).setOnClickListener(this);
        et_01 = findViewById(R.id.et_01);
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.save){
            SharedPreferences pre = getSharedPreferences("newdata",MODE_PRIVATE);
            SharedPreferences.Editor editor =pre.edit();
            String data = pre.getString("data",null);
            data += et_01.getText().toString();
            // 값을 '대입' 시키면 가능, 값을 += 로 누적시키지 않으면됨. 즉 초기화 가능
            editor.putString("data",data);
            editor.commit();
            et_01.setText("");
            Toast.makeText(this,pre.getString("data",""),Toast.LENGTH_LONG).show();
        }else if(v.getId() == R.id.load){
            SharedPreferences pre = getSharedPreferences("newdata",MODE_PRIVATE);
            String word = pre.getString("data","저장된 것이 없습니다");
            Toast.makeText(this,word,Toast.LENGTH_LONG).show();
        }

    }
}
