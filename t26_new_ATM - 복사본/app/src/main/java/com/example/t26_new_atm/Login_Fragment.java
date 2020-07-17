package com.example.t26_new_atm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import Helper.AppHelper;

public class Login_Fragment extends Fragment implements Response.ErrorListener {
    MainActivity activity;
    EditText id,pw;
    Button login,join,quite;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (MainActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.login_fragment,container,false);

        id = v.findViewById(R.id.login_id_et);
        pw = v.findViewById(R.id.login_pw_et);

        login = v.findViewById(R.id.login_btn);
        join = v.findViewById(R.id.login_join);
        quite = v.findViewById(R.id.login_quite);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 로그인 성공시 액티비티 이동
                check_id();
            }
        });

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 회원가입 클릭시
                activity.setFragment(1);
            }
        });

        quite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.setFragment(3);
            }
        });

        return v;
    }

    private Response.Listener<String> checkListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                AppHelper.getLog(response);
                JSONObject json = new JSONObject(response);
                String result = json.getString("result");
                if(result.equalsIgnoreCase("ok")){
                    JSONArray array = json.getJSONArray("user_data");
                    JSONObject temp = array.getJSONObject(0);
                    String acc_num = temp.getString("acc_num");
                    String name = temp.getString("name");
                    AppHelper.getLog(acc_num+" , "+name);
                    activity.intent = new Intent(getContext(),com.example.t26_new_atm.UserActivity.class);
                    activity.intent.putExtra("acc_num",acc_num);
                    activity.intent.putExtra("name",name);
                    activity.startActivity(activity.intent);
                }else{
                    Toast.makeText(getContext(),"ID 또는 PW를 확인해주세요.",Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private void check_id(){
        String url = "http://hozzi.woobi.co.kr/ATM/login.php";
        AppHelper.requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getContext()));
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
                checkListener,this){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();

                params.put("user_id",id.getText().toString());
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
