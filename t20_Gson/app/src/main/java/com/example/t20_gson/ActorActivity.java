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
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

public class ActorActivity extends AppCompatActivity implements Response.ErrorListener,
        Response.Listener<String>, AdapterView.OnItemClickListener {
    TextView name,name_eng,sex;
    ListView Lv;

    printAdapter adapter;
    ActorDetail actorDetail;
    String peopleCd;
    String my_key;
    String url = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/people/searchPeopleInfo.json?";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actor);
        Intent intent = getIntent();
        peopleCd = intent.getStringExtra("peopleCd");
        my_key = intent.getStringExtra("my_key");

        name = findViewById(R.id.actor_name);
        name_eng = findViewById(R.id.actor_name_eng);
        sex = findViewById(R.id.actor_sex);
        Lv = findViewById(R.id.actor_filmo_Lv);

        getResponse();
        Lv.setOnItemClickListener(this);
    }

    public void getResponse(){

        String actor_url = url + my_key + "&peopleCd="+peopleCd;

        AppHelper.requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                actor_url,
                this,
                this
        );

        stringRequest.setRetryPolicy(
                new DefaultRetryPolicy(3000,0,1f));

        AppHelper.requestQueue.add(stringRequest);


    }


    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(String response) {
        Gson gson = new Gson();
        actorDetail = gson.fromJson(response,ActorDetail.class);

        name.setText(actorDetail.peopleInfoResult.peopleInfo.peopleNm);
        name_eng.setText(actorDetail.peopleInfoResult.peopleInfo.peopleNmEn);
        sex.setText(actorDetail.peopleInfoResult.peopleInfo.sex);

        adapter = new printAdapter(this);
        Lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getApplicationContext(),com.example.t20_gson.Detail_movieActivity.class  );
        intent.putExtra("my_key",my_key);
        intent.putExtra("mv_cd",actorDetail.peopleInfoResult.peopleInfo.filmos.get(position).movieCd);
        startActivity(intent);

        finish();
    }


    class printAdapter extends ArrayAdapter {
        LayoutInflater lnf;
        public printAdapter(Activity context) {
            super(context,R.layout.print_item,actorDetail.peopleInfoResult.peopleInfo.filmos);
            lnf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            Log.d("mood","size: "+actorDetail.peopleInfoResult.peopleInfo.filmos);

        }
        // Adapter 에 사용되는 데이터의 개수를 리턴. : 필수 구현
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return actorDetail.peopleInfoResult.peopleInfo.filmos.size();
        }

        // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return actorDetail.peopleInfoResult.peopleInfo.filmos.get(position);
        }

        // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            RowHolder viewHolder; // 우리가 따로 만든  Item 클래스를 viewHolder로 객체화
            if(convertView == null) {
                convertView = lnf.inflate(R.layout.print_item, parent, false);
                viewHolder = new RowHolder();

                viewHolder.print_smt = convertView.findViewById(R.id.print_item);

                convertView.setTag(viewHolder);
            }else{
                viewHolder = (RowHolder) convertView.getTag();
            }
            if(actorDetail.peopleInfoResult.peopleInfo.filmos.get(position).movieNm!= null){
                String Nm = actorDetail.peopleInfoResult.peopleInfo.filmos.get(position).movieNm;
//                String ct = movieDetail.movieInfoResult.movieInfo.actors.get(position).cast;
                Log.d("mood","actor: "+Nm);
                viewHolder.print_smt.setText("작품"+(position+1)+": "+Nm);

            }else{
                viewHolder.print_smt.setText("none");
            }
            return convertView;
        }

    }

}
