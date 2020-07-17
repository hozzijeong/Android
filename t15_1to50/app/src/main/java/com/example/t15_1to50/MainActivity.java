package com.example.t15_1to50;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button game,rank,quite;

    static ArrayList<user_info> user_infos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        game = findViewById(R.id.main_game);
        rank = findViewById(R.id.main_rank);
        quite = findViewById(R.id.main_quite);

        game.setOnClickListener(this);
        rank.setOnClickListener(this);
        quite.setOnClickListener(this);

        dbInit();
    }

    private void dbInit(){
        SQLiteDatabase db = openOrCreateDatabase("Rank.db",MODE_PRIVATE,null);
        db.execSQL("CREATE TABLE IF NOT EXISTS rank_list("
                +"idx INTEGER PRIMARY KEY AUTOINCREMENT,"
                +"name TEXT,"
                +"time TEXT"
                +")"
        );
        db.close();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.main_game){
            Intent intent = new Intent(this,com.example.t15_1to50.GameActivity.class);
            startActivity(intent);
        }else if(v.getId() == R.id.main_rank){
            Intent intent = new Intent(this,com.example.t15_1to50.RankActivity.class);
            Log.d("mood","click_rank");
            startActivity(intent);
        }else if(v.getId() == R.id.main_quite){
            finish();
        }

    }
}
