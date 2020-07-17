package AppHelper;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.t34_mysns.LobbyActivity;
import com.example.t34_mysns.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import LobbyFragment.Lobby_2_Join_Fragment;
/*
    부모 클래스와 자식 클래스 사이에 한개의 클래스를 추가한 뒤,
    액티비티에서 공통적으로 사용이 되는 메서드들을 구현시켜 놓는다.
    그렇게 함으로써 굳이 같은 메서드를 여러번 사용 할 필요가 없다.
 */


public class Helper extends AppCompatActivity implements Response.Listener<String> ,Response.ErrorListener {
    protected RequestQueue requestQueue;
    public SharedPreferences sp;
    public SharedPreferences.Editor editor;
    protected Map<String,String> params = new HashMap<>();
    /*
        상속보다 requestQueue의 this가 먼저 실행이 된다. 따라서 onCreate서(상속이 다 이루어지고 난 다음에)
        객체화를 해준다. 아니아니 아예 OnCreate가 실행이 안되는데? 그냥 각 메서드 마다 새롭게 객체화 시켜줘야할듯
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

    }

    public void getLog(String data){
        Log.d("mood",data);
    }

    public void showToast(String data){
        Toast.makeText(this,data,Toast.LENGTH_LONG).show();
    }

    public void moveMain(){
        Intent intent = new Intent(this,com.example.t34_mysns.MainActivity.class);
        startActivity(intent);
    }

    public String setEnc(String str){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256"); // 이 부분을 SHA-256, MD5로만 바꿔주면 된다.
            md.update(str.getBytes()); // "세이프123"을 SHA-1으로 변환할 예정!
            byte byteData[] = md.digest();

            StringBuffer sb = new StringBuffer();

            for(int i=0; i<byteData.length; i++) {
                sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
            }

            str = sb.toString();
        } catch(NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return str;
    }

    public void requestVolley(String url){
        requestQueue = Volley.newRequestQueue(this);
        StringRequest st = new StringRequest(Request.Method.POST,url,
                this,this
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        st.setRetryPolicy(new DefaultRetryPolicy(3000,0,1f));
        requestQueue.add(st);

    }

    public void requestVolley(String url,Response.Listener<String> listener){
        requestQueue = Volley.newRequestQueue(this);
        StringRequest st = new StringRequest(Request.Method.POST,url,
                listener,this
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        st.setRetryPolicy(new DefaultRetryPolicy(3000,0,1f));
        requestQueue.add(st);
    }

    protected void GetVolley(String url,Response.Listener<String> listener){
        requestQueue = Volley.newRequestQueue(this);
        StringRequest st = new StringRequest(Request.Method.GET,url,
                listener,this);
        st.setRetryPolicy(new DefaultRetryPolicy(3000,0,1f));
        requestQueue.add(st);
    }



    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(String response) {

    }
}
