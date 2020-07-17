package com.example.t20_gson;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Movie;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Detail_movieActivity extends AppCompatActivity implements Response.Listener<String>,Response.ErrorListener,
        AdapterView.OnItemClickListener {

    String mv_cd;
    String detail_url = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieInfo.json?";
    String my_key;

    TextView title,title_eng,openDt,grade,rtime,genre,company,director;
    ListView actor;

    MovieDetail movieDetail;
    printAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        Intent intent = getIntent();
        mv_cd = intent.getStringExtra("mv_cd");
        my_key = intent.getStringExtra("my_key");


        actor = findViewById(R.id.detail_actors);
        title = findViewById(R.id.detail_title);
        title_eng  = findViewById(R.id.detail_title_eng);
        openDt = findViewById(R.id.detail_openYear);
        grade = findViewById(R.id.detail_grade);
        genre = findViewById(R.id.detail_genre);
        company = findViewById(R.id.detail_company);
        rtime = findViewById(R.id.detail_running_time);
        director = findViewById(R.id.detail_director);



        getResponse();
    }

    public void getResponse(){
        AppHelper.requestQueue = Volley.newRequestQueue(this);
        String url = detail_url+my_key+"&movieCd="+mv_cd;
        Log.d("mood",url);
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
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(String response) {
        Log.d("mood",response);

        Gson gson = new Gson();
        movieDetail = gson.fromJson(response, MovieDetail.class);

        title.setText(movieDetail.movieInfoResult.movieInfo.movieNm);
        title_eng.setText(movieDetail.movieInfoResult.movieInfo.movieNmEn);

        String od = movieDetail.movieInfoResult.movieInfo.openDt;
        if(od.length()>1){
            od = od.substring(0,od.length()-4);
        }
        openDt.setText("("+od+")");

        String gd = "";
        for(int i=0; i<movieDetail.movieInfoResult.movieInfo.audits.size(); i++){
            gd+= movieDetail.movieInfoResult.movieInfo.audits.get(i).watchGradeNm;
        }
        grade.setText("등급: "+gd);

        rtime.setText("상영 시간: "+movieDetail.movieInfoResult.movieInfo.showTm+"분");


        String gr = "";
        for(int i=0; i<movieDetail.movieInfoResult.movieInfo.genres.size(); i++){
            gr += movieDetail.movieInfoResult.movieInfo.genres.get(i).genreNm +"/";
        }
        if(gr.length()>1){
            gr = gr.substring(0,gr.length()-1);
        }
        genre.setText("장르: "+ gr);

        String dr = "";
        for(int i=0; i<movieDetail.movieInfoResult.movieInfo.directors.size(); i++){
            dr += movieDetail.movieInfoResult.movieInfo.directors.get(i).peopleNm+
                    "("+movieDetail.movieInfoResult.movieInfo.directors.get(i).peopleNmEn+")";
        }
        director.setText("감독: "+dr);


        String cp = "";
        for(int i=0; i<movieDetail.movieInfoResult.movieInfo.companys.size(); i++){
            cp += movieDetail.movieInfoResult.movieInfo.companys.get(i).companyNm+"/"+
                    movieDetail.movieInfoResult.movieInfo.companys.get(i).companyPartNm+"\n";
        }
        if(cp.length()>1){
            cp = cp.substring(0,cp.length()-1);
        }
        company.setText("제작사/배급사\n"+cp);

        adapter = new printAdapter(this);
        actor.setAdapter(adapter);
        actor.setOnItemClickListener(this);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String actor_url = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/people/searchPeopleList.json?"
                +my_key + "&peopleNm="+movieDetail.movieInfoResult.movieInfo.actors.get(position).peopleNm;
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                actor_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("mood","응답-> " + response);
                            String data = null;
                            JSONObject obj1 = new JSONObject(response);
                            JSONObject obj2 = obj1.getJSONObject("peopleListResult");
                            JSONArray array = obj2.getJSONArray("peopleList");
                            for(int i=0; i<array.length(); i++){
                                JSONObject temp = array.getJSONObject(i);
                                if(temp.getString("repRoleNm").equals("배우")){
                                    data = temp.getString("peopleCd");
                                }
                            }
                            if(data != null){
                                Log.d("mood","code-> "+data);
                                Intent intent = new Intent(getApplicationContext(),com.example.t20_gson.ActorActivity.class);
                                intent.putExtra("peopleCd",data);
                                intent.putExtra("my_key",my_key);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(getApplicationContext(),"영화인 정보가 없습니다",Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                this
        );

        AppHelper.requestQueue.add(stringRequest);

    }

    class printAdapter extends ArrayAdapter {
        LayoutInflater lnf;
        public printAdapter(Activity context) {
            super(context,R.layout.print_item,movieDetail.movieInfoResult.movieInfo.actors);
            lnf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            Log.d("mood","size: "+movieDetail.movieInfoResult.movieInfo.actors.size());

        }
        // Adapter 에 사용되는 데이터의 개수를 리턴. : 필수 구현
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return movieDetail.movieInfoResult.movieInfo.actors.size();
        }

        // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return movieDetail.movieInfoResult.movieInfo.actors.get(position);
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
            if(movieDetail.movieInfoResult.movieInfo.actors.get(position).peopleNm != null){
                String ac = movieDetail.movieInfoResult.movieInfo.actors.get(position).peopleNm;
//                String ct = movieDetail.movieInfoResult.movieInfo.actors.get(position).cast;
                Log.d("mood","actor: "+ac);
                viewHolder.print_smt.setText("배우"+(position+1)+": "+ac);

            }else{
                viewHolder.print_smt.setText("none");
            }
            return convertView;
        }

    }
}
