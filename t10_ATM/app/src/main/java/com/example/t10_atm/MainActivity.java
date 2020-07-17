package com.example.t10_atm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button join,login,quite;
    static ArrayList<UserInfo> userInfos = new ArrayList<>(); //유저들의 정보를 배열로 저장함.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        join = findViewById(R.id.main_join);
        login = findViewById(R.id.main_login);
        quite = findViewById(R.id.main_quite);

        join.setOnClickListener(this);
        login.setOnClickListener(this);
        quite.setOnClickListener(this);

        dbInit();
        db_load_data();


    }

    @Override
    protected void onResume() {
        super.onResume();
        for(int i = 0; i<userInfos.size(); i++){
            Log.d("mood",userInfos.get(i).acc_info.size()+"user_info:"+userInfos.get(i).idx+"/"+userInfos.get(i).id+"/"
                    +userInfos.get(i).pw+"/"+userInfos.get(i).money+"/"+userInfos.get(i).acc_num+"\n");
        }

    }

    private void dbInit(){
        SQLiteDatabase db= openOrCreateDatabase("ATM.db",MODE_PRIVATE,null);

        db.execSQL("CREATE TABLE IF NOT EXISTS user_info("
                +"idx INTEGER PRIMARY KEY AUTOINCREMENT,"
                +"id TEXT,"
                +"pw TEXT,"
                +"money INTEGER,"
                +"acc_num TEXT"
                +")"
        );

        db.execSQL("CREATE TABLE IF NOT EXISTS user_acc_info("
                +"idx INTEGER PRIMARY KEY AUTOINCREMENT,"
                +"user_idx INTEGER,"
                +"id TEXT,"
                +"money INTEGER,"
                +"total_money INTEGER"
                +")"
        );
        db.close();
    }

    public void db_load_data(){
        SQLiteDatabase db = openOrCreateDatabase("ATM.db",MODE_PRIVATE,null);
        Cursor c = db.rawQuery("SELECT * FROM user_info",null);
        c.moveToFirst();
        userInfos.clear();
        while(c.isAfterLast() == false){
            userInfos.add(new UserInfo(c.getInt(0),c.getString(1),
                    c.getString(2), c.getInt(3),c.getString(4)));
            c.moveToNext();
        }
        c.close();
        db.close();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.main_join){
            Intent intent = new Intent(this,com.example.t10_atm.JoinActivity.class);
            startActivity(intent);
        }else if(v.getId() == R.id.main_login) {
            Intent intent = new Intent(this, com.example.t10_atm.check_in_Activity.class);
            startActivity(intent);
        }else if (v.getId() == R.id.main_quite ){
            Toast.makeText(this,"종료합니다",Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
