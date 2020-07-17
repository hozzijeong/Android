package com.example.newdic;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.invoke.VolatileCallSite;
import java.util.ArrayList;

public class AddActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    // OnItemClickListner는 리스트뷰를 클릭했을때 필요한 이벤트리스너
    // 삽입,삭제,수정,검색까지 가능한 단어장 만들기
    // Voca아예 없애고 한번 해보기. 데이터의 중복을 없애기 위해서
    ArrayList<String> arr = new ArrayList<>(); // 단어를 나타내주는 ArrayList
    ListView mainLv;
    ArrayAdapter adapter;
    Button quite,ok;
    EditText inputeng,inputkor;
    String data = "";
    int size = Storage.voca.size();
    boolean check = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        quite = findViewById(R.id.addquite);
        quite.setOnClickListener(this);

        inputeng = findViewById(R.id.inputeng);
        inputkor = findViewById(R.id.inputkor);

        ok = findViewById(R.id.addok);
        ok.setOnClickListener(this);

        mainLv = findViewById(R.id.mainLv);
        mainLv.setOnItemClickListener(this);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arr);
        mainLv.setAdapter(adapter);
        // 단어를 추가하고, db에 추가를 한 뒤, voca를 초기화 하고 db에 맞춘대로 다시 단어를 추가해야함(idx를 추가한 생성자를 이용해서)
        load_word_db();
        reset_arr();
    }
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.addquite){
            finish();
        }else if(v.getId() == R.id.addok){

            if(!check){
                addword();
            }else{
                modifyword();
                check = false;
            }
            inputeng.setText("");
            inputkor.setText("");
        }
    }

    //VALUE에서 문자열을 추가할때 ""를 이용하자!
    protected void add_word_db(String eng,String kor){
        SQLiteDatabase db = openOrCreateDatabase("sqlite_word_data.db",MODE_PRIVATE,null);
        db.execSQL("INSERT INTO word_list(eng,kor) VALUES ('"+eng+"','"+kor+"')");
        db.close();
    }

    protected void modify_word_db(String eng, String kor, int idx){
        SQLiteDatabase db = openOrCreateDatabase("sqlite_word_data.db",MODE_PRIVATE,null);
        db.execSQL("UPDATE word_list SET eng = '"+eng+"',kor = '"+kor+"' WHERE idx = "+idx);
        db.close();
    }

    protected void delete_word_db(int idx){
        SQLiteDatabase db = openOrCreateDatabase("sqlite_word_data.db",MODE_PRIVATE,null);
        db.execSQL("DELETE FROM word_list WHERE idx ="+idx+"");
        db.close();
    }

    protected void load_word_db(){
        SQLiteDatabase db = openOrCreateDatabase("sqlite_word_data.db",MODE_PRIVATE,null);
        Cursor c = db.rawQuery("SELECT * FROM word_list",null);
        c.moveToFirst();
        Storage.voca.clear(); // voca를 초기화 하고 voca를 다시 새로 집어 넣는다.
        while(c.isAfterLast() == false){
            int tempidx = c.getInt(0);
            String tempEng = c.getString(1);
            String tempKor = c.getString(2);
            Storage.voca.add(new Voca(tempEng,tempKor,tempidx));
            c.moveToNext();
            }
            c.close();
        db.close();
    }

    protected void reset_arr(){
        arr.clear();
        for(int i=0; i<Storage.voca.size(); i++){
            arr.add(Storage.voca.get(i).eng+":"+Storage.voca.get(i).kor);
            Log.d("mood",arr.get(i) );
        }
        adapter.notifyDataSetChanged();
    }



    void addword(){
        String eng = inputeng.getText().toString();
        String kor = inputkor.getText().toString();
        Storage.voca.add(new Voca(eng,kor));
        add_word_db(eng,kor);// db에 voca 단어를 추가함.
        load_word_db(); // voca를 초기화 하고, idx를 추가한 값으로 voca를 재생성함.
        reset_arr();
    }

    void modifyword(){
        String eng = inputeng.getText().toString();
        String kor = inputkor.getText().toString();
        Storage.voca.get(position).eng = eng;
        Storage.voca.get(position).kor = kor;
        int idx = Storage.voca.get(position).db_idx;
        modify_word_db(eng,kor,idx); // db에 있는 idx의 값을 설정할 방법 찾아보기! voca에서는 position번째의 값을 수정하는 것이지만, db에서의 idx는 아예 다름.
        load_word_db();
        reset_arr();

    }

    void removeword(){
        int idx = Storage.voca.get(position).db_idx;
        Storage.voca.remove(position);
        delete_word_db(idx);
        load_word_db();
        reset_arr();
    }

    int position;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        // 로컬의 경우 new 를 붙이지 않아도 final을 안붙여도 되지만, new 를 붙였을 경우 final을 붙여야 되는 것이 규칙이다.
        this.position = position; // final에 있는 position 값을 전역변수 position 값에 대입한다 뭐 요런 의미
        Log.d("mood","position"+position);
        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setTitle("메세지").setMessage("수정 혹은 삭제하시겠습니까?");
        ab.setPositiveButton("수정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                check = true;
            }
        });
        ab.setNegativeButton("삭제", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                removeword();
            }
        });
        ab.setNeutralButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface diefalog, int which) {
            }
        });
        ab.show();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
