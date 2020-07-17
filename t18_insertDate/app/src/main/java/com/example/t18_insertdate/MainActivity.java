package com.example.t18_insertdate;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        Response.Listener<String>,Response.ErrorListener,AdapterView.OnItemClickListener {

    EditText input;
    Button insert;
    ListView lv;
    MyAdapter adapter;

    String date;
    String key = "29c79dc61260e9382ace4470e9f9b80c";
    //29c79dc61260e9382ace4470e9f9b80c
    //430156241533f1d058c603178cc3ca0e
    String url = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?";

    ArrayList<mv_info> infos = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        input = findViewById(R.id.input);
        insert = findViewById(R.id.insert);
        lv = findViewById(R.id.lv);
        insert.setOnClickListener(this);

        adapter = new MyAdapter(this);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);

    }

    @Override
    public void onClick(View v) {
        infos.clear();
        date = input.getText().toString();
        String real_url = url+"key="+key+"&targetDt="+date;
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                real_url,
                this,
                this
        );

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                3000,0,1f));
        requestQueue.add(stringRequest);
        Log.d("mood","key: "+key+" url: "+ url);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("mood","key: "+key+" mv_cd:" +infos.get(position).mv_cd+" url: "+url);

        Intent intent = new Intent(this,com.example.t18_insertdate.MovieActivity.class);
        intent.putExtra("key",key);
        intent.putExtra("mv_cd",infos.get(position).mv_cd);
        startActivity(intent);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(String response) {
        Log.d("mood",response);
        try {
            JSONObject object = new JSONObject(response);
            JSONObject object2 = object.getJSONObject("boxOfficeResult");
            JSONArray jsonArray = object2.getJSONArray("dailyBoxOfficeList");
            for(int i=0; i<jsonArray.length(); i++){
                JSONObject temp = jsonArray.getJSONObject(i);
                String name = temp.getString("movieNm");
                String amount = temp.getString("audiAcc");
                String dt = temp.getString("openDt");
                String mv_cd = temp.getString("movieCd");
                infos.add(new mv_info(name,amount,dt,mv_cd));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        adapter.notifyDataSetChanged();

    }

    class MyAdapter extends ArrayAdapter {
        LayoutInflater lnf;
        public MyAdapter(Activity context) {
            super(context,R.layout.list_item,infos);
            lnf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        // Adapter 에 사용되는 데이터의 개수를 리턴. : 필수 구현
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return infos.size();
        }

        // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return infos.get(position);
        }

        // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }
        // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
        public View getView(int position, View convertView, ViewGroup parent) {
            List_item viewHolder; // 우리가 따로 만든  Item 클래스를 viewHolder로 객체화
            if(convertView == null) {
                // item레이아웃을 받아옴
                convertView = lnf.inflate(R.layout.list_item, parent, false);
                viewHolder = new List_item();

                // 이제 객체화 시킨 Item_class를 Item_layout과 연결해줌.
                // convertView로 inflate했기 때문에, layout이 convertView에 속해있음
                viewHolder.mv_title = convertView.findViewById(R.id.mv_title);
                viewHolder.mv_amount = convertView.findViewById(R.id.mv_amount);
                viewHolder.mv_openDt = convertView.findViewById(R.id.mv_openDt);

                convertView.setTag(viewHolder);
            }else{
                viewHolder = (List_item) convertView.getTag();
            }

            viewHolder.mv_title.setText(infos.get(position).name);
            viewHolder.mv_amount.setText(infos.get(position).amount);
            viewHolder.mv_openDt.setText(infos.get(position).dt);

            return convertView;
        }

    }



}
