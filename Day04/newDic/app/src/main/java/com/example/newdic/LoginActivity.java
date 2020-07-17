package com.example.newdic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, Response.ErrorListener {

    EditText id,pw;
    Button join,login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        id = findViewById(R.id.login_id_et);
        pw = findViewById(R.id.login_pw_et);

        join = findViewById(R.id.login_joinbtn);
        login = findViewById(R.id.login_loginbtn);

        join.setOnClickListener(this);
        login.setOnClickListener(this);
    }
    Response.Listener<String> loginListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                JSONObject object = new JSONObject(response);
                String result = object.getString("result");
                Log.d("mood","result: "+result);
                if(result.equalsIgnoreCase("ok")){
                    String token = object.getString("token");
                    Log.d("mood","token: "+token);
                    Intent intent = new Intent(getApplicationContext(),com.example.newdic.MainActivity.class);
                    intent.putExtra("token",token);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"ID 또는 PW를 확인해주세요",Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private void Login(){
        String url = "http://heutwo.dothome.co.kr/sample/login.php";
        AppHelper.requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                url,loginListener,this){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("userid",id.getText().toString().trim());
                params.put("pass",AppHelper.setEnc(pw.getText().toString().trim()));
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000,0,1f));
        AppHelper.requestQueue.add(stringRequest);
    }



    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.login_joinbtn){
            Intent intent = new Intent(this,com.example.newdic.JoinActivity.class);
            startActivity(intent);
        }else if(v.getId() == R.id.login_loginbtn){
            Login();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }
}
