package com.example.t19_mvquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

import java.security.cert.CertStoreSpi;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        Response.Listener<String>,Response.ErrorListener {
    ArrayList<mv_code> mv_codes = new ArrayList<>();
    Random ran = new Random();
    actor_info actor_infos;
    int r;
    String cor_answer = "";
    int check = -1;
    TextView gender,work;
    EditText input;
    Button btn,show;

    String key = "29c79dc61260e9382ace4470e9f9b80c";
    String itemPerPage = "100";
    String people_list_url = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/people/searchPeopleList.json?";
    String people_url = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/people/searchPeopleInfo.json?";
    boolean turn = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gender = findViewById(R.id.hint_gender);
        work = findViewById(R.id.hint_work);
        input = findViewById(R.id.insert_name);
        btn = findViewById(R.id.insert_btn);
        show = findViewById(R.id.show_btn);

        btn.setOnClickListener(this);
        show.setOnClickListener(this);

        getResponse();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.show_btn) {
            Log.d("mood","turn: " + turn + "r" +r + "length" + mv_codes.size()
            +"cd" + mv_codes.get(r).mv_cd);
            getResponse();
            // 데이터를 불러오는 동안에 걸리는 시간은 어떻게 함?
            // 또한, 데이터 값을 불러왔는데 load가 안됨, 데이터 선별에서 문제가 발생한건가?
            if(check == 1){
                showActor();
            }
        }else if(v.getId() == R.id.insert_name){
            if(cor_answer.equals(input.getText().toString())){
                Toast.makeText(this,"정답",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this,"오답",Toast.LENGTH_LONG).show();
            }
        }
    }


    public void getResponse(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = null;
        if(turn){
            String mv_url = people_list_url+"key="+key+"&itemPerPage"+itemPerPage;
            stringRequest = new StringRequest(
                    Request.Method.GET,
                    mv_url,
                    this,
                    this
            );
        }else{
            r = ran.nextInt(mv_codes.size());
            String url = people_url + "key="+key+"&peopleCd"+mv_codes.get(r).mv_cd;
            stringRequest = new StringRequest(
                    Request.Method.GET,
                    url,
                    this,
                    this
            );
        }
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000,0,1f));
        requestQueue.add(stringRequest);
    }

    public void showActor(){
        gender.setText(actor_infos.gender);
        String filmo = "";
        for(int i=0; i<actor_infos.mv_list.size(); i++){
            filmo += actor_infos.mv_list.get(i) +"\n";
        }
        work.setText(filmo);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(String response) {

        try {
            JSONObject object = new JSONObject(response);
            if(turn){
                JSONObject object2 = object.getJSONObject("peopleListResult");
                JSONArray jarr = object2.getJSONArray("peopleList");
                for(int i=0; i<jarr.length(); i++){
                    JSONObject temp = jarr.getJSONObject(i);
                    String role = temp.getString("repRoleNm");
                    if(role.equals("배우")){
                        mv_codes.add(new mv_code(temp.getString("peopleCd")));
                    }
                }
                turn = false;
            }else{
                cor_answer = "";
                JSONObject object2 = object.getJSONObject("peopleInfoResult");
                JSONObject object3 = object2.getJSONObject("peopleInfo");
                cor_answer = object3.getString("peopleNm");
                String sex = object3.getString("sex");
                JSONArray jsonArray = object3.getJSONArray("filmos");
                ArrayList<String> works = null;
                for(int i=0; i<jsonArray.length(); i++){
                    JSONObject temp = jsonArray.getJSONObject(i);
                    works = new ArrayList<>();
                    works.add(temp.getJSONObject("movieNm")+"");
                }

                if(actor_infos == null){
                    actor_infos = new actor_info(mv_codes.get(r).mv_cd,sex,works,cor_answer);
                }

                check = 1;
            }
            Log.d("mood","response"+response);
            // 왜 response에서 전부 다 null 이 나오는지?
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
