package com.example.t26_new_atm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Helper.Acc_Info;
import Helper.AppHelper;


/*
    거래내역 불러오기.
    (서버에서 데이터 불러오기)
 */
public class UserActivity extends AppCompatActivity implements Response.ErrorListener {
    Intent intent;
    TextView tv;
    String acc,name;

    Check_Fragment check_fragment;
    Leave_Fragment leave_fragment;
    Transfer_Fragment transfer_fragment;
    User_Fragment user_fragment;

    ArrayList<Acc_Info> acc_infos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        tv = findViewById(R.id.user_tv);
        intent = getIntent();
        acc = intent.getStringExtra("acc_num");
        name = intent.getStringExtra("name");
        tv.setText(name+"님\n\n\t\t계좌 번호:"+acc +"\n\t\t 잔액: "+getTotal());

        check_fragment = new Check_Fragment();
        leave_fragment = new Leave_Fragment();
        transfer_fragment = new Transfer_Fragment();
        user_fragment = new User_Fragment();
        show_acc_info();
        getFragment(3);

    }

    public int getTotal(){
        int total;
        if(acc_infos.size() == 0){
            total = 0;
        }else{
            total = Integer.parseInt(acc_infos.get(acc_infos.size()-1).total);
        }
        return total;
    }

    public void getFragment(int idx){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if(idx == 0){
            show_acc_info();
            ft.replace(R.id.user_layout,check_fragment);
        }else if(idx == 1){
            ft.replace(R.id.user_layout,transfer_fragment);
        }else if(idx == 2){
            ft.replace(R.id.user_layout,leave_fragment);
        }else if(idx ==3){
            ft.replace(R.id.user_layout,user_fragment);
        }else if(idx ==4){
            finish();
        }
        ft.commit();
    }
    /*
        1. 배열 업로드는 변화가 생길때마다 해줘야함.
        2. 프래그먼트의 생명주기와 액티비티의 생명주기가 겹치는 부분이 있나?
            -> 존재하지 않을것 같다. 프래그먼트는 액티비티 위에서 존재하기 때문에, 프래그먼트가 소멸된다 해도
               액티비티가 소멸될 것 같지는 않다.
               그렇다면
               1. 프래그먼트가 교체될때마다 배열을 초기화 시켜서 업로드 해준다.
               2. 계좌 정보 프래그먼트가 attach 될 때마다 배열을 초기화 시켜서 업로드한다.(2번 채택)

     */

    Response.Listener<String> showListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                JSONObject object = new JSONObject(response);
                String result = object.getString("result");
                if(result.equalsIgnoreCase("ok")){
                    acc_infos.clear();
                    JSONArray jsonArray = object.getJSONArray("acc_infos");
                    int length = jsonArray.length();
                    for(int i=0; i<length; i++){
                        JSONObject temp = jsonArray.getJSONObject(i);
                        String host_acc_num = temp.getString("host_acc_num");
                        String host_name = temp.getString("host_name");
                        String client_acc_num = temp.getString("client_acc_num");
                        String client_name = temp.getString("client_name");
                        String transfer_money = temp.getString("transfer_money");
                        String total_money = temp.getString("total_money");
                        acc_infos.add(new Acc_Info(host_acc_num,host_name,client_acc_num,client_name,
                                transfer_money,total_money));

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    public void show_acc_info(){
        String url = "hozzi.woobi.co.kr/ATM/show_acc_info.php";
        AppHelper.requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
                showListener,this){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params= new HashMap<>();
                params.put("acc_num",acc);
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
