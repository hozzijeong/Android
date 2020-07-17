package com.example.t20_gson;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;


public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        Response.Listener<String>,Response.ErrorListener, AdapterView.OnItemClickListener {
    Button  btn;
    EditText insert;
    ListView Lv;
    String daily_url = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?";
    String my_key = "key=29c79dc61260e9382ace4470e9f9b80c";
    MovieList movieList;
    MyAdapter adapter;
    int check = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.main_btn);
        insert = findViewById(R.id.main_date);
        Lv = findViewById(R.id.main_Lv);

        btn.setOnClickListener(this);
        Lv.setOnItemClickListener(this);

    }


    public void getResponse(){
        String date = insert.getText().toString();
        String url = daily_url+my_key+"&targetDt="+date;

        AppHelper.requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                url,
                this,
                this
        );

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000,0,1f));
        AppHelper.requestQueue.add(stringRequest);

    }


    @Override
    public void onClick(View v) {
        getResponse();
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(String response) {
        Gson gson = new Gson();
        movieList = gson.fromJson(response,MovieList.class);
        Log.d("mood","response ->"+ response);
        adapter = new MyAdapter(this);
        Lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        Log.d("mood","size: "+movieList.boxOfficeResult.dailyBoxOfficeList.size());
        for(int i=0; i<movieList.boxOfficeResult.dailyBoxOfficeList.size(); i++){
            Log.d("mood","name: "+movieList.boxOfficeResult.dailyBoxOfficeList.get(i).movieNm);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getApplicationContext(),com.example.t20_gson.Detail_movieActivity.class);
        intent.putExtra("mv_cd",movieList.boxOfficeResult.dailyBoxOfficeList.get(position).movieCd);
        intent.putExtra("my_key",my_key);
        startActivity(intent);
    }


    class MyAdapter extends ArrayAdapter {
        LayoutInflater lnf;
        public MyAdapter(Activity context) {
            super(context,R.layout.movie_item,movieList.boxOfficeResult.dailyBoxOfficeList);
            lnf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        // Adapter 에 사용되는 데이터의 개수를 리턴. : 필수 구현
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return movieList.boxOfficeResult.dailyBoxOfficeList.size();
        }

        // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return movieList.boxOfficeResult.dailyBoxOfficeList.get(position);
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
                convertView = lnf.inflate(R.layout.movie_item, parent, false);
                viewHolder = new RowHolder();

                // 이제 객체화 시킨 Item_class를 Item_layout과 연결해줌.
                // convertView로 inflate했기 때문에, layout이 convertView에 속해있음
                viewHolder.title = convertView.findViewById(R.id.movie_title);
                viewHolder.openDt = convertView.findViewById(R.id.movie_opendt);
                viewHolder.amount = convertView.findViewById(R.id.movie_amount);

                convertView.setTag(viewHolder);
            }else{
                viewHolder = (RowHolder) convertView.getTag();
            }

            viewHolder.title.setText(movieList.boxOfficeResult.dailyBoxOfficeList.get(position).movieNm);
            viewHolder.amount.setText(movieList.boxOfficeResult.dailyBoxOfficeList.get(position).audiAcc);
            viewHolder.openDt.setText(movieList.boxOfficeResult.dailyBoxOfficeList.get(position).openDt);

            return convertView;
        }

    }


}
