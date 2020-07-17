package com.example.test01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView result;
    Button[] btn_arr;
    Button sum;
    Button sub;
    Button mul;
    Button splt;
    Button back;
    Button ok;
    Button clear;
    ArrayList<CharSequence> data = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        result = findViewById(R.id.result);

        sum = findViewById(R.id.sum);
        sum.setOnClickListener(this);

        sub = findViewById(R.id.sub);
        sub.setOnClickListener(this);

        mul = findViewById(R.id.mul);
        mul.setOnClickListener(this);

        splt = findViewById(R.id.splt);
        splt.setOnClickListener(this);

        ok = findViewById(R.id.ok);
        ok.setOnClickListener(this);

        back = findViewById(R.id.back);
        back.setOnClickListener(this);

        clear = findViewById(R.id.clear);
        clear.setOnClickListener(this);

        btn_arr = new Button[10];

        btn_arr[0] = findViewById(R.id.btn0);
        btn_arr[1] = findViewById(R.id.btn1);
        btn_arr[2] = findViewById(R.id.btn2);
        btn_arr[3] = findViewById(R.id.btn3);
        btn_arr[4] = findViewById(R.id.btn4);
        btn_arr[5] = findViewById(R.id.btn5);
        btn_arr[6] = findViewById(R.id.btn6);
        btn_arr[7] = findViewById(R.id.btn7);
        btn_arr[8] = findViewById(R.id.btn8);
        btn_arr[9] = findViewById(R.id.btn9);

        for(int i=0; i<btn_arr.length;i++){
            btn_arr[i].setOnClickListener(this);
        }


    }

    @Override
    public void onClick(View v) {
        int cnt = 1;
        int info = 0;
        int check = -1;

        if(v.getId() == R.id.btn0){

        }else if( v.getId() == R.id.btn1){
        }else if( v.getId() == R.id.btn2){
        }else if( v.getId() == R.id.btn3){
        }else if( v.getId() == R.id.btn4){
        }else if( v.getId() == R.id.btn5){
        }else if( v.getId() == R.id.btn6){
        }else if( v.getId() == R.id.btn7){
        }else if( v.getId() == R.id.btn8){
        }else if( v.getId() == R.id.btn9){
        }

        for(int i=0; i<data.size(); i++){
            result.setText(data.get(i));
        }






                        // 위치       타이틀             보여지는 시간         보여라
//        Toast.makeText(this,"Title..보여..?",Toast.LENGTH_LONG).show();
//       if(title.isShown() ){
//           title.setVisibility(title.INVISIBLE);
//           title.setText("보여...?");
//       }else{
//           title.setText("지금은...?");
//           title.setVisibility(title.VISIBLE);
//       }


    }
}
