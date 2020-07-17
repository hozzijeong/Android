package com.example.t22_newdic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
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

public class JoinActivity extends AppCompatActivity implements View.OnClickListener, Response.ErrorListener {
    Button quite,check,join;
    EditText id, pw1,pw2;
    RadioButton male;
    boolean id_possible = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        id= findViewById(R.id.join_id_et);
        pw1 = findViewById(R.id.join_pw_et);
        pw2 = findViewById(R.id.join_pw_check_et);
        quite = findViewById(R.id.join_quite);
        check = findViewById(R.id.join_id_check);
        join = findViewById(R.id.join_join);
        male = findViewById(R.id.join_gender_male);

        quite.setOnClickListener(this);
        check.setOnClickListener(this);
        join.setOnClickListener(this);


        // 아이디에 변경사항이 있으면 바로 false로 바뀜뀜
       id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //아이디 입력창에 다른 글을 입력시 중복체크 상황 초기화
                id_possible = false;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void showToast(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
    }

    Response.Listener<String> idcheckListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                Log.d("mood","response(id_check): "+response);
                JSONObject object = new JSONObject(response);
                String result = object.getString("result");
                if(result.equalsIgnoreCase("ok")){
                    id_possible = true;
                    showToast("사용가능한 아이디입니다.");
                }else{
                    showToast("이미 가입된 아이디입니다.");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private void check_Id(){
        String url = "http://hozzi.woobi.co.kr/word_list/check_id.php";
        AppHelper.requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
                idcheckListener,this){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("user_id",id.getText().toString().trim());
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000,0,1f));
        AppHelper.requestQueue.add(stringRequest);
    }


    private boolean check_Pw(){
        boolean result = false;
        String p1 = pw1.getText().toString();
        String p2 = pw2.getText().toString();

        if(p1.equals(p2)){
            result = true;
        }else{
            showToast("비밀번호를 확인해주세요");
        }
        if(p1.length()<5){
            showToast("6자리 이상 입력해주세요");
        }

        return result;
    }
    Response.Listener<String> joinListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                Log.d("mood","response_join"+response);
                JSONObject object = new JSONObject(response);
                String result = object.getString("result");

                if(result.equalsIgnoreCase("ok")){
                    showToast("회원가입 완료");
                    finish();
                }else{
                    showToast("회원가입 실패");
                }
                Log.d("mood","result: "+result);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private void Join(){
        String url = "http://hozzi.woobi.co.kr/word_list/join.php";
        AppHelper.requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
                joinListener,this){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("user_id",id.getText().toString().trim());
                params.put("pass",AppHelper.setEnc(pw1.getText().toString()));
                params.put("gender",male.isChecked()?"m":"f");
                Log.d("mood","user_id"+id.getText().toString().trim());
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000,0,1f));
        AppHelper.requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.join_quite:
                finish();
                break;
            case R.id.join_id_check:
                check_Id();
                break;
            case R.id.join_join:
                //아이디 체크 완료 되고, 비밀번호까지 다 맞으면 회원가입이 됨
                //아니면 비밀번호 회원가입 실패
                if(id_possible && check_Pw()){
                    Log.d("mood","id: "+id_possible+" pw: "+check_Pw());
                    Join();
                }
                break;
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }
}
