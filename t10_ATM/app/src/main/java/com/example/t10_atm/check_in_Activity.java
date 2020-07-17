package com.example.t10_atm;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class check_in_Activity extends AppCompatActivity implements View.OnClickListener {
    Button quite,login;
    EditText insert_id,insert_pw;
    int logdata = -1;

    final static int REQUEST_LOGIN_CODE = 1001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in_);

        quite = findViewById(R.id.login_quite);
        login = findViewById(R.id.login_ok);

        insert_id = findViewById(R.id.insert_id);
        insert_pw = findViewById(R.id.insert_pw);

        quite.setOnClickListener(this);
        login.setOnClickListener(this);
    }

    public int check_ID(String id,String pw){
        for(int i=0; i<MainActivity.userInfos.size(); i++) {
            if (MainActivity.userInfos.get(i).id.equals(id) && MainActivity.userInfos.get(i).pw.equals(pw)) {
                logdata = i;
                break;
            }
        }
        return logdata;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.login_ok) {
            String id = insert_id.getText().toString();
            String pw = insert_pw.getText().toString();
            check_ID(id,pw);
            if(logdata != -1){
                Intent intent = new Intent(this,com.example.t10_atm.UserActivity.class);
                intent.putExtra("UserNum",logdata); // 배열에서 유저의 번호를 넘겨줌
                startActivityForResult(intent,REQUEST_LOGIN_CODE);
            }else{
                AlertDialog.Builder ab = new AlertDialog.Builder(this);
                ab.setTitle("메세지").setMessage("존재하지 않는 아이디/비밀번호 입니다");
                ab.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                ab.show();
            }
        }else if(v.getId() ==R.id.login_quite){
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_LOGIN_CODE && resultCode == UserActivity.RESULT_USER_CODE){
            finish();
        }
    }
}
