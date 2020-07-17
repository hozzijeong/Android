package com.example.volley;

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
    Button btn;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.btn);
        tv = findViewById(R.id.tv);

        btn.setOnClickListener(this);
    }

    String date = "";

    @Override
    public void onClick(View v) {
        date = "20200301";
        String uri = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json" +
                "?key=430156241533f1d058c603178cc3ca0e" +
                "&targetDt="+ date;
        RequestQueue stringRequest = Volley.newRequestQueue(this);
        StringRequest myReq = new StringRequest(Request.Method.GET,
                uri,
                this,
                this);
        myReq.setRetryPolicy(new DefaultRetryPolicy(3000, 0, 1f));
        stringRequest.add(myReq);

    }

    @Override
    public void onResponse(String response) {
        tv.setText(response);
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject jsonObject2 = jsonObject.getJSONObject("boxOfficeResult");
            JSONArray jsonArray = jsonObject2.getJSONArray("dailyBoxOfficeList");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject temp = jsonArray.getJSONObject(i);
                String name = temp.getString("movieNm");
                String amount = temp.getString("audiAcc");
                String movieCd = temp.getString("movieCd");
                Log.d("heu","name: "+ name + " 누적관객: "+amount +" 영화코드: "+movieCd);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d("heu","error: "+error.getLocalizedMessage());
    }
}
