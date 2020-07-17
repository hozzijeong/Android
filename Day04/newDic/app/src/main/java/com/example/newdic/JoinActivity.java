package com.example.newdic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class JoinActivity extends AppCompatActivity implements View.OnClickListener, ErrorListener {


    EditText id,pw1,pw2;
    Button quite,check,join;
    RadioButton male,female;

    String id_result = "";
    boolean id_possible = false;
    boolean pw_possible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        id = findViewById(R.id.join_id_et);
        pw1 = findViewById(R.id.join_pw_et);
        pw2 = findViewById(R.id.join_pw_again_et);
        quite = findViewById(R.id.join_quitebtn);
        check = findViewById(R.id.join_id_check);
        join = findViewById(R.id.join_joinbtn);
        male = findViewById(R.id.join_male);
        female = findViewById(R.id.join_female);

        quite.setOnClickListener(this);
        check.setOnClickListener(this);
        join.setOnClickListener(this);

        join.setEnabled(false);

    }

    private void showToast(String string){
        Toast.makeText(this,string,Toast.LENGTH_LONG).show();
    }

    Response.Listener<String> checkListener  = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                JSONObject object = new JSONObject(response);
                id_result = object.getString("result");
                Log.d("mood",id_result);
                if(id_result.equalsIgnoreCase("ok")){
                    id_possible = true;
                    showToast("사용 가능한 ID입니다.");
                    join.setEnabled(true);
                }else{
                    showToast("이미 가입된 ID입니다.");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private void checkId(final String id){
        String url = "http://wavenbreakwater.com/sample/duplicate_id.php";
        AppHelper.requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
                checkListener,this){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("userid",id);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000,0,1f));
        AppHelper.requestQueue.add(stringRequest);
    }

    Response.Listener<String> joinListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                JSONObject object = new JSONObject(response);
                String result = object.getString("result");
                Log.d("mood","result: "+result);
                if(result.equalsIgnoreCase("ok")) {
                    showToast("회원가입 완료");
                    finish();
                }else{
                    showToast("회원가입 실패");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };




    private void Join(){
        String url = "http://heutwo.dothome.co.kr/sample/join.php";
        AppHelper.requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url,joinListener,this){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("userid",id.getText().toString().trim());
                params.put("pass",AppHelper.setEnc(pw1.getText().toString().trim()));
                String gen = male.isChecked()?"m":"f";
                params.put("gender",gen);
                Log.d("mood","id: "+ id.getText().toString().trim()+
                        "\npw: "+AppHelper.setEnc(pw1.getText().toString().trim())+
                        "\ngender: "+ gen);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000,0,1f));
        AppHelper.requestQueue.add(stringRequest);
    }

    private void check_pw(String pw1,String pw2){
        if(pw1.trim().length()>5) {
            if (pw1.equals(pw2)) {
                pw_possible = true;
            }
        }
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.join_quitebtn){
            finish();
        }else if(v.getId() == R.id.join_id_check){
            if(id.getText().toString().trim().length()>5){
                checkId(id.getText().toString());
            }else{
                showToast("6자 이상 입력해주세요");
            }
        }else if(v.getId() == R.id.join_joinbtn){
            check_pw(pw1.getText().toString().trim(),pw2.getText().toString().trim());
            if(pw_possible && id_possible){
                Join();
            }else{
                showToast("Id 또는 Pw를 확인해주세요");
            }
        }

    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }
}
