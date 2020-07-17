package com.example.t14_custimlistview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView main_Lv;
    myAdapter myAdapter;
    ArrayList<user_info> arr = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        main_Lv = findViewById(R.id.main_Lv);

        for(int i=0; i<30; i++){
            arr.add(new user_info("이름"+i,"내용"+i));
        }

        myAdapter = new myAdapter(this);
        main_Lv.setAdapter(myAdapter);

    }

    class RowHolder{
        ImageView imageHolder;
        TextView nameHolder;
        TextView contentHolder;
    }

    class myAdapter extends ArrayAdapter {
        LayoutInflater lnf;
        public myAdapter(Activity context) {
            super(context,R.layout.item,arr);
            lnf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        // Adapter 에 사용되는 데이터의 개수를 리턴. : 필수 구현
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return arr.size();
        }

        // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return arr.get(position);
        }

        // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }
        // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
        public View getView(int position, View convertView, ViewGroup parent) {
            RowHolder viewHolder; // 우리가 따로 만든  Item 클래스를 viewHolder로 객체화
            if(convertView == null) {
                // item레이아웃을 받아옴
                convertView = lnf.inflate(R.layout.item, parent, false);
                viewHolder = new RowHolder();

                // 이제 객체화 시킨 Item_class를 Item_layout과 연결해줌.
                // convertView로 inflate했기 때문에, layout이 convertView에 속해있음
               viewHolder.imageHolder = convertView.findViewById(R.id.item_Iv);
               viewHolder.nameHolder = convertView.findViewById(R.id.item_title);
               viewHolder.contentHolder = convertView.findViewById(R.id.item_content);

                convertView.setTag(viewHolder);
            }else{
                viewHolder = (RowHolder)convertView.getTag();
            }


            viewHolder.nameHolder.setText(arr.get(position).name);
            viewHolder.contentHolder.setText(arr.get(position).content);

            if(position ==0){
                viewHolder.imageHolder.setImageResource(R.drawable.gold_medal);
            } else if (position == 1) {
                viewHolder.imageHolder.setImageResource(R.drawable.silver_medal);
            } else if (position == 2) {
                viewHolder.imageHolder.setImageResource(R.drawable.bronze_medal);
            }else{
                viewHolder.imageHolder.setImageResource(R.drawable.none);
            }


            return convertView;
        }

    }


}
