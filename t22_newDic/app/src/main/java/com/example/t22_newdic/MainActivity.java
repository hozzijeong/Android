package com.example.t22_newdic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import Helper.AppHelper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Response.ErrorListener {
    Button quite,join,login;
    EditText id,pw;
    ProgressBar pro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        quite = findViewById(R.id.login_quite);
        join = findViewById(R.id.login_join);
        login = findViewById(R.id.login_login);

        pro = findViewById(R.id.login_pro);

        id = findViewById(R.id.login_id_et);
        pw = findViewById(R.id.login_pw_et);

        quite.setOnClickListener(this);
        join.setOnClickListener(this);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.login_quite){
            finish();
        }else if(v.getId() == R.id.login_join){
            // 회원가입으로 이동
            Intent intent = new Intent(this,com.example.t22_newdic.JoinActivity.class);
            startActivity(intent);
        }else if(v.getId() == R.id.login_login){
            // 로그인으로 이동
            Login();
            pro.setVisibility(View.VISIBLE);
        }
    }

    Response.Listener<String> loginListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.d("mood",response);
            try {
                JSONObject object = new JSONObject(response);
                String result = object.getString("result");
                if(result.equalsIgnoreCase("ok")){
                    String user_idx = object.getString("user_idx");
                    Intent intent = new Intent(getApplicationContext(),com.example.t22_newdic.WordActivity.class);
                    intent.putExtra("user_idx",user_idx);
                    startActivity(intent);
                    id.setText("");
                    pw.setText("");
                    pro.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(),"환영합니다.",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(),"ID 또는 PW를 확인해주세요.",Toast.LENGTH_LONG).show();
                    pro.setVisibility(View.INVISIBLE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private void Login(){
        String url = "http://hozzi.woobi.co.kr/word_list/login.php";
        AppHelper.requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
                loginListener, this){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("user_id",id.getText().toString().trim());
                params.put("pass",AppHelper.setEnc(pw.getText().toString()));
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000,0,1f));
        AppHelper.requestQueue.add(stringRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }
}
