package com.example.t18_insertdate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MovieActivity extends AppCompatActivity implements Response.Listener<String>,Response.ErrorListener {

    TextView title,title_eng,genre,grade,director,maker,openDt;
    TextView[] act = new TextView[2];
    ImageView imageView;

    String mv_cd = "";
    String key = "";
    String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        title = findViewById(R.id.work_title);
        title_eng = findViewById(R.id. work_title_eng);
        genre = findViewById(R.id.work_genre);
        grade = findViewById(R.id.work_grade);
        director = findViewById(R.id.work_director_name);
        act[0] = findViewById(R.id.work_actor1_name);
        act[1] = findViewById(R.id.work_actor2_name);
        maker = findViewById(R.id.work_maker_name);
        openDt = findViewById(R.id.work_openDt);
        imageView = findViewById(R.id.work_poster);


        Init();
//        sendImage();
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(String response) {
        Log.d("mood","data: " + response);
        try {
            JSONObject main = new JSONObject(response);
            JSONObject result = main.getJSONObject("movieInfoResult");
            JSONObject info = result.getJSONObject("movieInfo");
            title.setText(info.getString("movieNm"));
            openDt.setText(info.getString("openDt"));
            title_eng.setText(info.getString("movieNmEn"));

            JSONArray genrearr = info.getJSONArray("genres");
            String genre_name = "";
            for(int i=0; i<genrearr.length(); i++){
                JSONObject temp = genrearr.getJSONObject(i);
                genre_name += temp.getString("genreNm")+"/";
            }
            genre_name.substring(0,genre_name.length()-1);
            genre.setText(genre_name);

            JSONArray directarr = info.getJSONArray("directors");
            String director_name = "";
            for(int i=0; i<directarr.length(); i++){
                JSONObject temp = directarr.getJSONObject(i);
                director_name += temp.getString("peopleNm")+"("+
                temp.getString("peopleNmEn")+")"+"\n";
            }
            director_name = director_name.substring(0,director_name.length()-1);
            director.setText(director_name);

            JSONArray actorarr = info.getJSONArray("actors");
            String name[] = new String [2];
            for(int i=0; i<name.length; i++){
                name[i] = "";
                JSONObject temp = actorarr.getJSONObject(i);
                name[i] += temp.getString("peopleNm")+"("+
                        temp.getString("cast")+")";
            }
            for(int i=0; i<2; i++){
                act[i].setText(name[i]);
            }

            JSONArray makearr = info.getJSONArray("companys");
            JSONObject temp = makearr.getJSONObject(0);
            String make_name = temp.getString("companyNm");
            maker.setText(make_name);

            JSONArray gradearr = info.getJSONArray("audits");
            JSONObject temp1 = gradearr.getJSONObject(0);
            String grade_cut = temp1.getString("watchGradeNm");
            grade.setText(grade_cut);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void Init(){
        Intent intent = getIntent();
        url = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieInfo.json?";
        mv_cd = intent.getStringExtra("mv_cd");
        key = intent.getStringExtra("key");

        String real_url = url+"key="+key+"&movieCd="+mv_cd;

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                real_url,
                this,
                this
        );
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000,0,1f));
        requestQueue.add(stringRequest);

    }

    void sendImage(){
        String urlStr = url+"movieCd"+mv_cd;
        ImageLoad imageLoad = new ImageLoad(urlStr,imageView);
        imageLoad.execute();
    }
}
