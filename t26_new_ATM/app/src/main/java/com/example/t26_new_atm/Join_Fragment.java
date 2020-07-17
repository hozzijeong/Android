package com.example.t26_new_atm;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
import java.util.Objects;
import java.util.Random;

import Helper.AppHelper;

public class Join_Fragment extends Fragment implements Response.ErrorListener {
    private MainActivity activity;
    private Button join,quite,check;
    private EditText id,pw1,pw2,name;
    private boolean id_possible = false;
    private int acc_num;
    private Random ran = new Random();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (MainActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.join_fragment,container,false);

        join = v.findViewById(R.id.join_btn);
        quite = v.findViewById(R.id.join_quite);
        check = v.findViewById(R.id.join_check_id);

        id = v.findViewById(R.id.join_id_et);
        pw1 = v.findViewById(R.id.join_pw1_et);
        pw2 = v.findViewById(R.id.join_pw2_et);
        name = v.findViewById(R.id.join_name_et);

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id_possible && check_pw()){
                    registerAcc();
                }else{
                    if(!check_pw()){
                        showMessage("비밀번호를 확인하세요");
                    }else if(!id_possible){
                        showMessage("아이디를 확인하세요");
                    }
                }
            }
        });

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check_id();
            }
        });

        quite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.setFragment(0);
            }
        });
        if(id_possible){
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

        return v;
    }

    private Response.Listener<String> checkListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            //왜 이 메서드가 실행 되지 않는 건가?, 인터페이스로 구현을 해도, 한번에 한개밖에 구현을 하지 못함.
            // 인터페이스 안에 이 모든 메소드들을 넣어야 하는거임? 아니면 추상 메소드 안에?
            AppHelper.getLog(response);
            try {
                JSONObject json = new JSONObject(response);
                String result = json.getString("result");
                if(result.equalsIgnoreCase("ok")){
                    id_possible = true;
                    showMessage("사용가능한 아이디입니다.");
                }else{
                    showMessage("이미 존재하는 아이디입니다.");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private void check_id(){
        if(id.getText().toString().length()>6){
            String url = "http://hozzi.woobi.co.kr/ATM/check_id.php";
            AppHelper.requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getContext()));
            StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
                    checkListener,this){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<>();
                    params.put("user_id",id.getText().toString().trim());
                    AppHelper.getLog(params.get("user_id"));
                    return params;
                }
            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000,0,1f));
            AppHelper.requestQueue.add(stringRequest);

        }else{
            showMessage("ID를 6글자 이상 입력해주세요");
        }
        // check_id.php 를 통해서 확인할 것
    }

    Response.Listener<String>addListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                JSONObject json = new JSONObject(response);
                String result = json.getString("result");
                if(result.equalsIgnoreCase("ok")){
                    activity.setFragment(0);
                    showMessage("회원가입을 환영합니다.");
                    id.setText("");
                    pw1.setText("");
                    pw2.setText("");
                    name.setText("");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private void registerAcc(){
        String url = "http://hozzi.woobi.co.kr/ATM/add_acc_info.php";
        AppHelper.requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getContext()));
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
                addListener,this){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("user_id",id.getText().toString().trim());
                params.put("pass",AppHelper.setEnc(pw1.getText().toString()));
                params.put("name",name.getText().toString());
                params.put("acc_num",create_acc_num()+"");
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000,0,1f));
        AppHelper.requestQueue.add(stringRequest);

        // add_acc_info 를 통해서 등록
    }

    private boolean check_pw(){
        boolean pw_possible = false;

        if(pw1.getText().toString().length()>6){
            if(pw1.getText().toString().equals(pw2.getText().toString())){
                pw_possible = true;
            }
        }

        return pw_possible;
    }


    private Response.Listener<String> acc_checkListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                JSONObject json = new JSONObject(response);
                String result = json.getString("result");
                if(!result.equalsIgnoreCase("ok")){
                    acc_num = ran.nextInt(100000)+10000;
                    check_acc();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private int create_acc_num(){
        acc_num = ran.nextInt(100000)+10000;
        // 계좌번호 검사도 해야함. 계좌번호 검사는 check_acc.php 를 통해서 하면 됨.
        check_acc();
        return acc_num;
    }

    private void check_acc(){
        String url = "http://hozzi.woobi.co.kr/ATM/check_acc.php";
        AppHelper.requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getContext()));
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
                acc_checkListener,this){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<>();
                params.put("acc_num",acc_num+"");
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000,0,1f));
        AppHelper.requestQueue.add(stringRequest);
    }


    private void showMessage(String data){
        Toast.makeText(getContext(),data,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }
}
