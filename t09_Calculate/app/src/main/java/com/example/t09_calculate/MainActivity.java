package com.example.t09_calculate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button clear,mul,div,sub,sum,rem,dot,insert,del;
    EditText result,process;

    String num1;
    String num2;

    int type;
    int SUM = 1;
    int SUB = 2;
    int MUL = 3;
    int DIV = 4;
    int REM = 5;
    String pro;

    double d1;
    double d2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 사칙연산자
        mul = findViewById(R.id.btn_mul);
        div = findViewById(R.id.btn_div);
        sub = findViewById(R.id.btn_sub);
        sum = findViewById(R.id.btn_sum);
        rem = findViewById(R.id.btn_rem);

        // 계산 도와주는 버튼
        clear =findViewById(R.id.btn_clear);
        del = findViewById(R.id.btn_del);
        dot = findViewById(R.id.btn_dot);
        insert = findViewById(R.id.btn_insert);

        //결과 도출
        result = findViewById(R.id.result);
        process = findViewById(R.id.process);

        mul.setOnClickListener(this);
        sub.setOnClickListener(this);
        rem.setOnClickListener(this);
        sum.setOnClickListener(this);
        div.setOnClickListener(this);
        clear.setOnClickListener(this);
        del.setOnClickListener(this);
        dot.setOnClickListener(this);
        insert.setOnClickListener(this);

//        View.OnClickListener mListener = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        }
    }

    @Override
    public void onClick(View v) {
        if(result.getText().toString() == null){
            Toast.makeText(this,"숫자를 입력하세요",Toast.LENGTH_LONG).show();
        }
        switch (v.getId()) {
            case R.id.btn_0:
                result.setText(result.getText().toString() + 0);
                break;
            case R.id.btn_1:
                result.setText(result.getText().toString() + 1);
                break;
            case R.id.btn_2:
                result.setText(result.getText().toString() + 2);
               break;
            case R.id.btn_3:
                result.setText(result.getText().toString() + 3);
                break;
            case R.id.btn_4:
                result.setText(result.getText().toString() + 4);
                break;
            case R.id.btn_5:
                result.setText(result.getText().toString() + 5);
                break;

            case R.id.btn_6:
                result.setText(result.getText().toString() + 6);
                break;
            case R.id.btn_7:
                result.setText(result.getText().toString() + 7);
                break;
            case R.id.btn_8:
                result.setText(result.getText().toString() + 8);
                break;
            case R.id.btn_9:
                result.setText(result.getText().toString() + 9);
                break;
            case R.id.btn_dot:
                result.setText(result.getText().toString() + ".");
                break;

            case R.id.btn_sum:
                num1 = result.getText().toString();
                pro = result.getText().toString() + "+";
                result.setText(pro);
                process.setText(pro);
                result.setText("");
                type = SUM;
                break;

            case R.id.btn_sub:
                num1 = result.getText().toString();
                pro = result.getText().toString() + "-";
                process.setText(pro);
                result.setText("");
                type = SUB;
                break;


            case R.id.btn_mul:
                num1 = result.getText().toString();
                pro = result.getText().toString() + "x";
                process.setText(pro);
                result.setText("");
                type = MUL;
                break;

            case R.id.btn_div:
                num1 = result.getText().toString();
                pro = result.getText().toString() + "/";
                process.setText(pro);
                result.setText("");
                type = DIV;
                break;
            case R.id.btn_rem:
                num1 = result.getText().toString();
                pro = result.getText().toString() + "%";
                process.setText(pro);
                result.setText("");
                type = REM;
                break;
            case R.id.btn_del:
                String del_num = result.getText().toString();
                result.setText(del_num.substring(0,del_num.length()-1));
                break;

            case R.id.btn_clear:
                num1 = num2 = "";
                result.setText("");
                d1 = d2 = 0;
                type = 0;
                process.setText("");
                pro = "";

            case R.id.btn_insert:
                double r = 0;

                num2 = result.getText().toString();

                d1 = Double.parseDouble(num1);
                d2 = Double.parseDouble(num2);

                if(type == SUM){
                    r = d1+ d2;
                }else if(type == SUB){
                    r = d1 - d2;
                }else if(type == MUL){
                    r = d1*d2;
                }else if(type == DIV){
                    r = d1/d2;
                }else if(type == REM){
                    r = d1%d2;
                }

                result.setText(r+"");
                process.setText("");
                num1 = result.getText().toString();
                break;

        }
    }
}
