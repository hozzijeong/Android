package com.example.customlistview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    /**
     *  1.  item 을 위한 레이아웃
     *  2.  item 을 위한 클래스
     *  3.  데이터를 저장한 클래스
     *  4.  [3]번의 클래스를 저장할 어레이리스트 만들기
     *  5.  아답터 코드 복붙
     *  6.  item의 요소에 따라 xml과 java 코드 연결
     *  7.  리스트 뷰와 연동은 동일
     *  8. default값은 없다고 보면 됨
     *  9. customListView 3개 이상 만들어보기
     *
     * ***/
    ArrayList<Voca> arr = new ArrayList<>();
    MyAdapter adapter; // 커스텀 리스트뷰를 연결해줄 어뎁터(본인 입맛에 맞게 편집)

    ListView lv; // 화면 자체에 나올 리스트뷰
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = findViewById(R.id.lv);

        for (int i = 0; i < 50; i++) {
            arr.add(new Voca(i, "영어 "+i , "한글"));
            // arr에 데이터 저장
        }
        adapter = new MyAdapter(this);
        // 생성자에 context 를 파라미터로 가지도록 설정하고, 객체로 만듦

        lv.setAdapter(adapter);
        // Main_layout과 adapter를 연동

    }

    class RowHolder{
        // custom layout 을 위한 class 선언
        TextView engTvHolder;
        TextView korTvHolder;
        ImageView ivHolder;
    }



    class MyAdapter extends ArrayAdapter {
        LayoutInflater lnf;

        public MyAdapter(Activity context) {// arr은 Voca를 나타내는 리스트, R.layout.item은 리스트 뷰를 나타내는 아이템 레이아웃
            super(context, R.layout.item, arr); // 위치, 어떤 형식, 어떤 배열 인지
            // 생성자 자체에서 생성을 할때 데이터 배열과 item_layout을 adapt해줌.
            lnf = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
                viewHolder.engTvHolder = convertView.findViewById(R.id.engTv);
                viewHolder.korTvHolder = convertView.findViewById(R.id.korTv);
                viewHolder.ivHolder = convertView.findViewById(R.id.iv);

                convertView.setTag(viewHolder);
            }else{
                viewHolder = (RowHolder)convertView.getTag();
            }


            viewHolder.engTvHolder.setText(arr.get(position).eng);
            viewHolder.korTvHolder.setText(arr.get(position).kor);

            if (position%2 ==0){
                viewHolder.ivHolder.setVisibility(View.INVISIBLE);
            }else{
                viewHolder.ivHolder.setVisibility(View.VISIBLE);
            }

            return convertView;
        }
    }
}
