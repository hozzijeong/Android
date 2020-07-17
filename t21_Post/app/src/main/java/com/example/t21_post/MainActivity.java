package com.example.t21_post;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.StringReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements Response.Listener<String>,Response.ErrorListener {
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.tv);

        getResponse();
        Log.d("mood","비 암호화 -> 가나다라");
        Log.d("mood","암호화 -> "+setEnc("가나다라").length());

    }

    private void getResponse(){
        String url ="http://heutwo.dothome.co.kr/sample/test.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                this,
                this
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("userid","value");
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000,0,1f));
        requestQueue.add(stringRequest);

    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(String response) {
        tv.setText(setEnc(response));
    }

    private String setEnc(String str){
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256"); // 이 부분을 SHA-256, MD5로만 바꿔주면 된다.
            md.update(str.getBytes()); // "세이프123"을 SHA-1으로 변환할 예정!

            byte byteData[] = md.digest();

            StringBuffer sb = new StringBuffer();

            for(int i=0; i<byteData.length; i++) {
                sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
            }

            result = sb.toString();
            System.out.println(result); // 결과물이 출력됨. aed19017dbb4d25a580b7f9e012e29be089bd1f3
        } catch(NoSuchAlgorithmException e){
            e.printStackTrace();
        }

        return result;
    }
}
