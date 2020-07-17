package com.example.newdic;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class AddActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    // OnItemClickListner는 리스트뷰를 클릭했을때 필요한 이벤트리스너
    ArrayList<String> arr = new ArrayList<>();
    ListView mainLv;
    ArrayAdapter adapter;
    Button quite,ok;
    EditText inputeng,inputkor;
    boolean check = false;
    int idx;

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

        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,arr);
        mainLv.setAdapter(adapter);

        db_load_word();
        showword();
    }

    private void db_add_word(String eng, String kor){
        SQLiteDatabase db = openOrCreateDatabase("Word.db",MODE_PRIVATE,null);
        db.execSQL("INSERT INTO word_list(eng,kor) VALUES('"+eng+"','"+kor+"')");
        db.close();
    }

    private void db_delete_word(int idx){ // 여기서 idx는 해당 값의 idx;
        SQLiteDatabase db = openOrCreateDatabase("Word.db",MODE_PRIVATE,null);
        db.execSQL("DELETE FROM word_list WHERE idx ="+ idx);
        db.close();
    }

    private void db_modify_word(int idx, String eng, String kor){
        SQLiteDatabase db = openOrCreateDatabase("Word.db",MODE_PRIVATE,null);
        db.execSQL("UPDATE word_list SET eng = '"+eng+"',kor='"+kor +"'WHERE idx="+idx);
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

    public void showword(){
        arr.clear();
        for(int i=0; i<Storage.voca.size(); i++){
            arr.add(Storage.voca.get(i).eng+":"+Storage.voca.get(i).kor);
        }
        adapter.notifyDataSetChanged();
    }

    public void addword(String eng, String kor){
        Storage.voca.add(new Voca(0,eng,kor));
        db_add_word(eng,kor);
        db_load_word(); //voca (idx, eng, kor) 값이 제대로 들어있음.
        showword();
    }
    public void modifyword(int idx,String eng,String kor){
        Storage.voca.get(idx).eng = eng;
        Storage.voca.get(idx).kor = kor;
        db_modify_word(Storage.voca.get(idx).idx,eng,kor);
        db_load_word();
        showword();
    }

    public void removeword(int idx){ // 여기서 idx 는 해당 리스트의 position 값을 의미
        Log.d("mood", "position: " + idx+"idx: " + Storage.voca.get(idx).idx);
        db_delete_word(Storage.voca.get(idx).idx);
        Storage.voca.remove(idx);
        // ※ 단어의 수정/ 삭제를 할때 무엇이 우선적으로 처리되어야 하는지 확인할것!
        // idx의 값, 즉 배열 크기에 변화를 주는 수정 사항이라면 항상 순서 확인!
        db_load_word();
        showword();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.addquite){
            finish();
        }else if(v.getId() == R.id.addok){
            String eng = inputeng.getText().toString();
            String kor = inputkor.getText().toString();
            if(check == false){
                addword(eng,kor);
            }else{
                modifyword(idx,eng,kor);
                check = false;
            }
            showword();
            inputeng.setText("");
            inputkor.setText("");
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        idx = position;
        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setTitle("메세지").setMessage("수정 혹은 삭제하시겠습니까?");
        ab.setPositiveButton("수정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                check = true;
                String[] temp = arr.get(position).split(":");
                inputeng.setText(temp[0]);
                inputkor.setText(temp[1]);
            }
        });
        ab.setNegativeButton("삭제", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                removeword(position);
                showword();
            }
        });
        ab.setNeutralButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface diefalog, int which) {

            }
        });
        ab.show();
    }

}
