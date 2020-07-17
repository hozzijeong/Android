package com.example.t24_dic_fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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

public class Login_Fragment extends Fragment implements Response.ErrorListener {
    MainActivity activity;
    Button join,login,quite;
    EditText id,pw;
    ProgressBar pro;
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
        View v = inflater.inflate(R.layout.login,container,false);
        join = v.findViewById(R.id.login_join);
        login = v.findViewById(R.id.login_login);
        quite = v.findViewById(R.id.login_quite);
        id = v.findViewById(R.id.login_id_et);
        pw = v.findViewById(R.id.login_pw_et);
        pro = v.findViewById(R.id.login_pro);

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.getFragment(3);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
                pro.setVisibility(View.VISIBLE);

            }
        });
        quite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.getFragment(0);
            }
        });
        return v;
    }

    Response.Listener<String> loginListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                JSONObject object = new JSONObject(response);
                String result = object.getString("result");
                if(result.equalsIgnoreCase("ok")){
                    activity.user_idx = object.getInt("user_idx");
                    // 유저 idx를 어떻게 넘겨줘야 할까? -> static? : 약간 보안에 취약할것 같은 느낌.
                    activity.getFragment(4);
                    id.setText("");
                    pw.setText("");
                    pro.setVisibility(View.INVISIBLE);
                    Toast.makeText(getContext(),"환영합니다.",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getContext(),"ID 또는 PW를 확인해주세요.",Toast.LENGTH_LONG).show();
                    pro.setVisibility(View.INVISIBLE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private void Login(){
        String url = "http://hozzi.woobi.co.kr/word_list/login.php";
        AppHelper.requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getContext()));
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
