package com.example.listtest;

/*
    1. 리스트뷰 설정
    2. layout에서 리스트 뷰를 만든다. ( 레이아웃 프레임 자체는 Linear로 설정했다.)
    3. ListView를 인스턴스화 시킨 뒤,
    4. 리스트 자체를 레이아웃에 있는 리스트와 이어준다.
    5. 새로운 ArrayList 한개를 선언한다.
    6. 새로운 ArrayList와 Layout에 있는 리스트를 연결한다 (Adapter를 통해)
    7. setAdapter를 통해 서로를 이어 준 뒤, add remove 등 여러가지 활동을 한다.
    8.마무리 저장을 한다.
 */

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = findViewById(R.id.list);
        List<String> data = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, data);
        list.setAdapter(adapter); // list와 data를 연결해 주는 메소드 Adapter.
        data.add("오...");
        data.add("이게 되네?");
        data.add("진짜 되네...?");

        adapter.notifyDataSetChanged(); // 저장완료하는 구문

    }
}
