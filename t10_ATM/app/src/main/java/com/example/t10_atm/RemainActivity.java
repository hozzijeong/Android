package com.example.t10_atm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.drm.DrmStore;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class RemainActivity extends AppCompatActivity implements View.OnClickListener {
    int UserNum;
    TextView tv;
    Button quite;
    ListView Lv;
    MyAdapter adapter;
    ArrayList<Info> arr = new ArrayList<>();
    // 데이터 출력할때, user_idx가 userNum과 같은 것만 arr에 저장하기

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remain);
        tv = findViewById(R.id.remain_user_tv);
        quite = findViewById(R.id.remain_quite_btn);
        Lv = findViewById(R.id.remain_Lv);
        quite.setOnClickListener(this);
        adapter = new MyAdapter(this);

        Intent intent = getIntent();
        UserNum = intent.getIntExtra("UserNum",-1);
        String name = MainActivity.userInfos.get(UserNum).id;

        tv.setText(name+"님 \n " +
                "잔고:"+MainActivity.userInfos.get(UserNum).money);

        Lv.setAdapter(adapter);
        set_arr();
        adapter.notifyDataSetChanged();
    }

    class RowHolder{
        TextView nameHolder,moneyHolder,total_moneyHolder;
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
            if(convertView == null) {
                // item레이아웃을 받아옴
                convertView = lnf.inflate(R.layout.item, parent, false);
                viewHolder = new RowHolder();

                // 이제 객체화 시킨 Item_class를 Item_layout과 연결해줌.
                // convertView로 inflate했기 때문에, layout이 convertView에 속해있음
                viewHolder.moneyHolder = convertView.findViewById(R.id.remain_money_tv);
                viewHolder.nameHolder = convertView.findViewById(R.id.remain_name_tv);
                viewHolder.total_moneyHolder = convertView.findViewById(R.id.remain_total_money_tv);

                convertView.setTag(viewHolder);
            }else{
                viewHolder = (RowHolder)convertView.getTag();
            }

            viewHolder.nameHolder.setText(arr.get(position).name);
            if(arr.get(position).money <0){
                viewHolder.moneyHolder.setText(arr.get(position).money+"");
                viewHolder.moneyHolder.setTextColor(Color.parseColor("#FF0000"));
            }else{
                viewHolder.moneyHolder.setText(arr.get(position).money+"");
                viewHolder.moneyHolder.setTextColor(Color.parseColor("#00BFFF"));
            }
            viewHolder.total_moneyHolder.setText(arr.get(position).total_money+"");

            return convertView;
        }
    }

    public void set_arr(){
        for(int i=0; i<MainActivity.userInfos.get(UserNum).acc_info.size(); i++){
            arr.add(new Info(MainActivity.userInfos.get(UserNum).acc_info.get(i).user_idx,
                    MainActivity.userInfos.get(UserNum).acc_info.get(i).name,
                    MainActivity.userInfos.get(UserNum).acc_info.get(i).money,
                    MainActivity.userInfos.get(UserNum).acc_info.get(i).total_money));
        }
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
