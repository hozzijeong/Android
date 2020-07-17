package com.example.t08_newdic;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    static final int REQUEST_MAIN_CODE = 1111;

    /*
      랭킹 추가할 때, 클래스 자체를 어떻게 이동 시키는지...?
      그리고 왜 버튼 한가운데가 비게되는지... 궁금...
     */

    // 안드로이드에 보이는 버튼들
    TextView title;
    Button quite;
    Button add;
    Button game;
    Button rank;

    // 단어들을 나열할때 사용하는 변수들
    String worddata;
    String newword; // 새로 추가된 단어
    String saveword; // 기존에 저장된 단어
    int newSize = 0;
    int saveSize = 0;

    ArrayList<Rank> rankdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title = findViewById(R.id.title);

        add = findViewById(R.id.addbtn);
        quite = findViewById(R.id.quitebtn);
        game = findViewById(R.id.gamebtn);
        rank = findViewById(R.id.rankbtn);

        add.setOnClickListener(this);
        quite.setOnClickListener(this);
        game.setOnClickListener(this);
        rank.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.addbtn){
            Intent intent = new Intent(this,com.example.t08_newdic.AddActivity.class);
            intent.putExtra("data",Word());
            startActivityForResult(intent,REQUEST_MAIN_CODE);
        }else if(v.getId() == R.id.gamebtn){
            Intent intent = new Intent(this,com.example.t08_newdic.GameActivity.class);
            intent.putExtra("data",Word());
            startActivityForResult(intent,REQUEST_MAIN_CODE);
        }else if(v.getId() == R.id.quitebtn){
            SharedPreferences pre = getSharedPreferences("wordData",MODE_PRIVATE);
            SharedPreferences.Editor editor = pre.edit();
            editor.putString("savedata",Word());
            editor.commit();
            finish();
        }else if(v.getId() == R.id.rankbtn){
            Intent intent = new Intent(this,com.example.t08_newdic.RankActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("rankdata",rankdata);
            intent.putExtra("rank",bundle);
            startActivity(intent);
        }


    }

    public String Word(){
        SharedPreferences pre = getSharedPreferences("wordData",MODE_PRIVATE);
        saveword = pre.getString("savedata","");
        // 단어 저장 행위...
        String saveinfo = "";
        String newinfo = "";

        if(!saveword.equals("") || newword != null){
            if(!saveword.equals("")){
                String [] temp = saveword.split("\n");
                saveSize = Integer.parseInt(temp[0]);
                for(int i=1; i<temp.length;i++ ){
                    saveinfo += temp[i]+"\n";
                }
            }
            if(newword != null){
                String []temp = newword.split("\n");
                newSize = Integer.parseInt(temp[0]); //NumberFormatException:
                for(int i=1; i<temp.length; i++){
                    newinfo+= temp[i];
                    if(i != temp.length-1){
                        newinfo+="\n";
                    }
                }
            }else{
                saveinfo = saveinfo.substring(0,saveinfo.length()-1);
            }
            int total = newSize + saveSize;
            worddata = total +"\n" + saveinfo + newinfo;
        }else{
            worddata = null;
        }
        return worddata;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_MAIN_CODE && resultCode == AddActivity.RESULT_ADD_CODE){
            newword = data.getStringExtra("data"); // Add에서 저장한 단어들 불러옴.
            Toast.makeText(this, newword, Toast.LENGTH_SHORT).show();
        }else if(requestCode == REQUEST_MAIN_CODE && resultCode == GameActivity.RESULT_RANK_CODE){
            rankdata = (ArrayList<Rank>) getIntent().getSerializableExtra("rank");
        }
    }
}
