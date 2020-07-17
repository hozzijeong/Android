package com.example.t24_dic_fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
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

import Helper.AppHelper;

public class Join_Fragment extends Fragment implements Response.ErrorListener {
    MainActivity activity;
    Button join,check,quite;
    EditText id,pw1,pw2;
    RadioButton male;
    boolean id_possible = false;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (MainActivity) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.join,container,false);
        join =v.findViewById(R.id.join_join);
        check = v.findViewById(R.id.join_id_check);
        quite = v.findViewById(R.id.join_quite);
        id = v.findViewById(R.id.join_id_et);
        pw1 = v.findViewById(R.id.join_pw_et);
        pw2 = v.findViewById(R.id.join_pw_check_et);
        male = v.findViewById(R.id.join_gender_male);

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id_possible && check_Pw()){
                    Log.d("mood","id: "+id_possible+" pw: "+check_Pw());
                    Join();
                }
            }
        });

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check_Id();
            }
        });

        quite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.getFragment(5);
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


    private void showToast(String msg){
        Toast.makeText(getContext(),msg,Toast.LENGTH_LONG).show();
    }

    Response.Listener<String> idcheckListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
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
        AppHelper.requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getContext()));
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
                Log.d("mood",response);
                JSONObject object = new JSONObject(response);
                String result = object.getString("result");
                if(result.equalsIgnoreCase("ok")){
                    showToast("회원가입 완료");
                    activity.getFragment(5);
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
        AppHelper.requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getContext()));
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
                joinListener,this){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("user_id",id.getText().toString().trim());
                params.put("pass",AppHelper.setEnc(pw1.getText().toString()));
                params.put("gender",male.isChecked()?"m":"f");
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
