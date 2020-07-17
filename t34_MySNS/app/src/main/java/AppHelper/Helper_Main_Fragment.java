package AppHelper;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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

import java.util.HashMap;
import java.util.Map;

public class Helper_Main_Fragment extends Fragment implements Response.Listener<String>,Response.ErrorListener {
    protected MainActivity mainActivity;
    private RequestQueue requestQueue;
    protected Map<String,String> params = new HashMap<>();
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        /*
            lobbyActivity가 있는 구간에서 왜 main_Activitiy가 캐스팅이 안되는가?
            액티비티에 프래그먼트를 붙일 때, 해당 액티비티가 아닌 다른 액티비티는붙일 수 없다.
         */
        mainActivity = (MainActivity) getActivity();
    }

    protected void GetVolley(String url,Response.Listener<String> listener){
        requestQueue = Volley.newRequestQueue(mainActivity);
        StringRequest st = new StringRequest(Request.Method.GET,url,
                listener,this);
        st.setRetryPolicy(new DefaultRetryPolicy(3000,0,1f));
        requestQueue.add(st);
    }



    protected void requestVolley(String url){
        requestQueue = Volley.newRequestQueue(mainActivity);
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

    protected void requestVolley(String url, Response.Listener<String> listener){
        requestQueue = Volley.newRequestQueue(mainActivity);
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

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(String response) {

    }
}
