package com.example.t15_1to50;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class RankActivity extends AppCompatActivity implements View.OnClickListener {

    ArrayList<user_info> arr = new ArrayList<>();
    Button quite;
    MyAdapter adapter;
    ListView Lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);
        quite = findViewById(R.id.rank_quite);
        Lv = findViewById(R.id.rank_Lv);

        quite.setOnClickListener(this);

        load_db_data();
        sort_rank();

         for(int i=0; i<MainActivity.user_infos.size(); i++){
            arr.add(new user_info(MainActivity.user_infos.get(i).name,
                    MainActivity.user_infos.get(i).time));
        }
        adapter = new MyAdapter(this);
        Lv.setAdapter(adapter);

    }

    public void load_db_data(){
        SQLiteDatabase db = openOrCreateDatabase("Rank.db",MODE_PRIVATE,null);
        Cursor c = db.rawQuery("SELECT*FROM rank_list",null);
        c.moveToFirst();
        MainActivity.user_infos.clear();
        while (c.isAfterLast() == false){
            MainActivity.user_infos.add(
                    new user_info(c.getString(1),c.getString(2)));
            c.moveToNext();
        }
        c.close();
        db.close();
    }

    public void sort_rank(){
        for(int i = 0; i<MainActivity.user_infos.size(); i++){
            for(int j = i; j<MainActivity.user_infos.size(); j++){
                double time1 = Double.parseDouble(MainActivity.user_infos.get(i).time);
                double time2 = Double.parseDouble(MainActivity.user_infos.get(j).time);
                    if(time1>time2){
                        user_info temp = MainActivity.user_infos.get(i);
                        MainActivity.user_infos.set(i,MainActivity.user_infos.get(j));
                        MainActivity.user_infos.set(j,temp);
                    }

            }
        }
    }

    class RowHolder{
        ImageView IvHolder;
        TextView nameHolder,timeHolder;
    }
    class MyAdapter extends ArrayAdapter {

        LayoutInflater lnf;

        public MyAdapter(Activity context) {
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

            Log.d("mood","position: "+position+"/"+arr.get(position).name);

            if(convertView == null) {
                // item레이아웃을 받아옴
                convertView = lnf.inflate(R.layout.item, parent, false);
                viewHolder = new RowHolder();

                // 이제 객체화 시킨 Item_class를 Item_layout과 연결해줌.
                // convertView로 inflate했기 때문에, layout이 convertView에 속해있음
                viewHolder.IvHolder = convertView.findViewById(R.id.item_Iv);
                viewHolder.nameHolder = convertView.findViewById(R.id.item_name);
                viewHolder.timeHolder= convertView.findViewById(R.id.item_time);

                convertView.setTag(viewHolder);
            }else{
                viewHolder = (RowHolder)convertView.getTag();
            }

            viewHolder.nameHolder.setText(arr.get(position).name);
            viewHolder.timeHolder.setText(arr.get(position).time);

            if(position ==0){
                viewHolder.IvHolder.setImageResource(R.drawable.gold_medal);
            } else if (position == 1) {
                viewHolder.IvHolder.setImageResource(R.drawable.silver_medal);
            } else if (position == 2) {
                viewHolder.IvHolder.setImageResource(R.drawable.bronze_medal);
            }else{
                viewHolder.IvHolder.setImageResource(R.drawable.none);
            }


            return convertView;
        }

    }

    @Override
    public void onClick(View v) {
        finish();
    }

}
