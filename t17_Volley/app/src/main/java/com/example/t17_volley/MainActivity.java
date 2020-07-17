package com.example.t17_volley;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        Response.Listener<String>,Response.ErrorListener {

    TextView tv;
    Button btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.tv);
        btn = findViewById(R.id.click);

        btn.setOnClickListener(this);

    }
    String date = "";
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.click){
            date = "20190301";
            String uri = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json"
                    + "?key=430156241533f1d058c603178cc3ca0e"
                    + "&targetDt="+date;

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            // RequestQueue 객체화, 이제 여기에 값만 넣어 주면 됨
            StringRequest stringRequest = new StringRequest(
                    Request.Method.GET,
                    uri,
                    this,
                    this
            );

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000,0,1f));
            requestQueue.add(stringRequest);
        }
    }

    @Override
    public void onResponse(String response) {
        tv.setText(response);

        try {
            JSONObject object = new JSONObject(response);
            JSONObject object2 = object.getJSONObject("boxOfficeResult");
            JSONArray jsonArray = object2.getJSONArray("dailyBoxOfficeList");
            for(int i=0; i<jsonArray.length(); i++){
                JSONObject temp = jsonArray.getJSONObject(i);
                String name = temp.getString("movieNm");
                String amount = temp.getString("audiAcc");
                String moviecd = temp.getString("movieCd");
                String num = temp.getString("rnum");
                Log.d("mood","영화 이름:"+name + " 관객 수: "+amount+" 영화 코드: "+moviecd + " 순번" + num);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d("mood","실패");

    }

}
