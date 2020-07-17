package com.example.listview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.LocusId;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    ListView mainLv;
    Button btn;
    ArrayAdapter adapter; // 배열 어댑터 객체화
    ArrayList<String> arr = new ArrayList<>(); // 리스트뷰에 사용할 문자열 배열 선언
    int cnt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.btn);
        mainLv = findViewById(R.id.mainLv);
        for (int i = 0; i < 10; i++) {
            arr.add("List "+cnt);
            cnt++;
        }
        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,arr );
        mainLv.setAdapter(adapter);
        mainLv.setOnItemClickListener(this);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        arr.add("List"+cnt);
        cnt++;
        adapter.notifyDataSetChanged(); // 바뀐 내용을 바로 화면에 전환시켜주는 함수!
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("heu","pos: "+ arr.get(position));
        Log.d("heu","position: "+position);
    }
}
