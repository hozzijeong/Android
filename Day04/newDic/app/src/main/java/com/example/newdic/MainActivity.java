package com.example.newdic;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView title;
    Button quite,add,game,rank;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title = findViewById(R.id.title);

        add = findViewById(R.id.addbtn);
        quite = findViewById(R.id.quitebtn);
        game = findViewById(R.id.gamebtn);
        rank = findViewById(R.id.rankebtn);

        add.setOnClickListener(this);
        quite.setOnClickListener(this);
        game.setOnClickListener(this);
        rank.setOnClickListener(this);

        dbInit();
        db_load_word();
    }

    public void dbInit(){
        SQLiteDatabase db =openOrCreateDatabase("Word.db",MODE_PRIVATE,null);
        // 단어장 저장할 테이블 생성
        db.execSQL("CREATE TABLE IF NOT EXISTS word_list("
                +"idx INTEGER PRIMARY KEY AUTOINCREMENT,"
                +"eng TEXT,"
                +"kor TEXT"
                +")"
        );

        //랭킹 저장할 테이블 생성
        db.execSQL("CREATE TABLE IF NOT EXISTS rank_list("
                +"idx INTEGER PRIMARY KEY AUTOINCREMENT,"
                +"name TEXT,"
                +"score INTEGER"
                +")"
        );

        db.close();
    }

    private void db_load_word(){
        SQLiteDatabase db = openOrCreateDatabase("Word.db",MODE_PRIVATE,null);
        Cursor c = db.rawQuery("SELECT * FROM word_list",null);
        c.moveToFirst();
        Storage.voca.clear();
        while(c.isAfterLast() == false){
            Storage.voca.add(new Voca(c.getInt(0),
                    c.getString(1),c.getString(2)));
            c.moveToNext();
        }
        c.close();
        db.close();
    }



    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.addbtn){
            Intent intent = new Intent(this,com.example.newdic.AddActivity.class);
            startActivityForResult(intent,3000);
        }else if(v.getId() == R.id.gamebtn){
            Intent intent = new Intent(this,com.example.newdic.GameActivity.class);
            startActivityForResult(intent,500);
        }else if(v.getId() == R.id.quitebtn){
            finish();
        }else if(v.getId() == R.id.rankebtn){
            Intent intent = new Intent(this,com.example.newdic.RankActivity.class);
            startActivity(intent);
        }
    }
}
