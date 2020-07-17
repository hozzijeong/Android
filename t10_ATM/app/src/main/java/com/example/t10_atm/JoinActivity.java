package com.example.t10_atm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

public class JoinActivity extends AppCompatActivity implements View.OnClickListener {
    Button quite,join;
    EditText insert_id, insert_pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        quite = findViewById(R.id.join_quite);
        join = findViewById(R.id.join_ok);
        insert_id = findViewById(R.id.insert_id);
        insert_pw = findViewById(R.id.insert_pw);

        quite.setOnClickListener(this);
        join.setOnClickListener(this);
    }

    public int check(String id){ // 아이디 중복체크
        int check = -1;
        for(int i=0; i<MainActivity.userInfos.size(); i++){
            if(id.equals(MainActivity.userInfos.get(i).id)){
                check = i;
                break;
            }
        }
        return check;
    }

    public int Acc(){ // 계좌번호 중복체크
        int acc = 0;
        int check = -1;
        Random ran = new Random();
        acc = ran.nextInt(100000)+10000;
        if(MainActivity.userInfos.size() != 0){
            while(true){
                for(int i=0; i<MainActivity.userInfos.size(); i++){
                    if((acc+"").equals(MainActivity.userInfos.get(i).acc_num)){
                        check = i;
                        break;
                    }
                }
                if(check == -1){
                    break;
                }else{
                    acc = ran.nextInt(10000)+90000;
                }
            }
        }
        return acc;
    }

    void add_db(String id, String pw,int money, String acc_num){
        SQLiteDatabase db = openOrCreateDatabase("ATM.db",MODE_PRIVATE,null);
        db.execSQL("INSERT INTO user_info(id,pw,money,acc_num) " +
                "VALUES('"+id+"','"+pw+"','"+money+"','"+acc_num+"')");
        db.close();
    }

    public void db_load_data(){
        SQLiteDatabase db = openOrCreateDatabase("ATM.db",MODE_PRIVATE,null);
        Cursor c = db.rawQuery("SELECT * FROM user_info",null);
        c.moveToFirst();
        MainActivity.userInfos.clear();
        while(c.isAfterLast() == false){
            MainActivity.userInfos.add(new UserInfo(c.getInt(0),c.getString(1),
                    c.getString(2), c.getInt(3),c.getString(4)));
            c.moveToNext();
        }
        c.close();
        db.close();
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.join_ok){
            String id = insert_id.getText().toString();

            if(check(id) != -1){
                AlertDialog.Builder ab = new AlertDialog.Builder(this);
                ab.setTitle("메세지").setMessage("이미 존재하는 아이디입니다!");
                ab.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                ab.show();
            }else{
                String pw = insert_pw.getText().toString();
                String acc_num = Acc() +"";
                int money = 0;
                MainActivity.userInfos.add(new UserInfo(0,id,pw,money,acc_num));
                add_db(id,pw,money,acc_num);
                db_load_data(); // 이렇게 하면 user_info 에 idx 값 까지 같이 딸려서 저장이됨.
                insert_id.setText("");
                insert_pw.setText("");
            }

        }else if(v.getId() == R.id.join_quite){
            finish();
        }
    }
}
