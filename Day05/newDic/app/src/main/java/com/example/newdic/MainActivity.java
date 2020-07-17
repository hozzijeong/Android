package com.example.newdic;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    // 데이터의 저장을 할때는 바로바로 저장하는 것이 좋다. 데이터의 수정/삭제/추가 등의 행위가 이루어 졌을때 바로바로 실행하자.
    // DB의 데이터는 왠만해서는 건드리지 말자. 삭제를 한다고 해도, idx의 값은 초기화 되지 않는다.
    TextView title;
    Button quite,add,game;
//    String data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title = findViewById(R.id.title);

        add = findViewById(R.id.addbtn);
        quite = findViewById(R.id.quitebtn);
        game = findViewById(R.id.gamebtn);

        add.setOnClickListener(this);
        quite.setOnClickListener(this);
        game.setOnClickListener(this);

//        SharedPreferences sp = getSharedPreferences("Word", MODE_PRIVATE);
//        data = sp.getString("word",null);
//
//        if(data != null){
//            Log.d("mood","data: "+data);
//            String[] temp = data.split("\n");
//            Log.d("mood","길이: "+temp.length);
//            for(int i=0; i<temp.length; i++){
//                String[] info = temp[i].split(":");
//                Storage.voca.add(new Voca(info[0],info[1]));
//            }
//        }

        load_data_db();

        for(int i=0; i<Storage.voca.size(); i++){
            Log.d("mood", Storage.voca.get(i).eng +":"+Storage.voca.get(i).kor);
        }

    }
    private void load_data_db(){
        SQLiteDatabase db =openOrCreateDatabase("sqlite_word_data.db",MODE_PRIVATE,null);
        db.execSQL("CREATE TABLE IF NOT EXISTS word_list("
                +"idx INTEGER PRIMARY KEY AUTOINCREMENT,"
                +"eng TEXT,"
                +"kor TEXT"
                +")"
        ); // 맨처음에 로드할때 테이블을 생성해 주면됨. 어차피 insert는 저장할때 만들어 주면 되기때문에 상관이 없음, 테이블 구조를 바꿀 경우, 데이터를 전부 삭제하거나, 앱을 날린다음에 재설치 해야함
        db.close();
    }


//
//    public void saveword(){
//        SharedPreferences sp = getSharedPreferences("Word",MODE_PRIVATE);
//        SharedPreferences.Editor editor = sp.edit();
//        data = "";
//        for(int i =0; i<Storage.voca.size(); i++) {
//            data += Storage.voca.get(i).eng + ":" + Storage.voca.get(i).kor + "\n";
//        }
//        if(data.length()>1){
//            data = data.substring(0,data.length()-1);
//        }
//        Log.d("mood","add data: "+data);
//        editor.putString("word",data);
//        editor.commit();
//    }

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
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        saveword();
    }
}
