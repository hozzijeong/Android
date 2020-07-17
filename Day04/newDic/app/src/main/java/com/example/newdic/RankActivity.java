package com.example.newdic;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class RankActivity extends AppCompatActivity implements View.OnClickListener {
    ListView rank_Lv;
    Button quite;
    MyAdapter adapter;

    ArrayList<Rank> arr = new ArrayList<>();

    ArrayList<Integer> score = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);
        rank_Lv = findViewById(R.id.rank_Lv);
        quite = findViewById(R.id.rank_quite);
        quite.setOnClickListener(this);

        adapter = new MyAdapter(this);
        rank_Lv.setAdapter(adapter);

        db_load_data();
        loadData();
        sortData();

        adapter.notifyDataSetChanged();
    }

    private void db_load_data(){
        SQLiteDatabase db = openOrCreateDatabase("Word.db",MODE_PRIVATE,null);
        Cursor c = db.rawQuery("SELECT * FROM rank_list",null);
        c.moveToFirst();
        Storage.rank.clear();
        while (c.isAfterLast() == false){
            Storage.rank.add(new Rank(c.getString(1),c.getInt(2)));
            c.moveToNext();
        }
        c.close();
        db.close();
    }



    public void loadData(){
        arr.clear();
        score.clear();
        for(int i=0; i<Storage.rank.size(); i++){
            arr.add( new Rank(Storage.rank.get(i).name , Storage.rank.get(i).score));
            score.add(Storage.rank.get(i).score);
        }
    }

    protected void sortData(){
        int size = Storage.rank.size();
        for(int i=0; i<size; i++){
            for(int j=i; j<size; j++){
                int r1 = score.get(i);
                int r2 = score.get(j);
                if(r1<=r2){
                    int temp = r1;
                    Rank info = arr.get(i);

                    arr.set(i,arr.get(j));
                    arr.set(j,info);

                    score.set(i,r2);
                    score.set(j,temp);
                }
            }
        }
    }

    class   MyAdapter extends ArrayAdapter {
        LayoutInflater lnf;

        public MyAdapter(Activity context) {
            super(context, R.layout.item,arr);
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
                convertView = lnf.inflate(R.layout.item, parent, false);
                viewHolder = new RowHolder();
                // 이제 객체화 시킨 Item_class를 Item_layout과 연결해줌.
                // convertView로 inflate했기 때문에, layout이 convertView에 속해있음
                viewHolder.ivHolder = convertView.findViewById(R.id.item_Iv);
                viewHolder.nameHolder = convertView.findViewById(R.id.item_name);
                viewHolder.scoreHolder = convertView.findViewById(R.id.item_time);

                convertView.setTag(viewHolder);
            }else{
                viewHolder = (RowHolder)convertView.getTag();
            }


            viewHolder.nameHolder.setText(arr.get(position).name);
            viewHolder.scoreHolder.setText(arr.get(position).score+"");

            if(position ==0){
                viewHolder.ivHolder.setImageResource(R.drawable.gold_medal);
            } else if (position == 1) {
                viewHolder.ivHolder.setImageResource(R.drawable.silver_medal);
            } else if (position == 2) {
                viewHolder.ivHolder.setImageResource(R.drawable.bronze_medal);
            }else{
                viewHolder.ivHolder.setImageResource(R.drawable.none);
            }
            return convertView;
        }
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
