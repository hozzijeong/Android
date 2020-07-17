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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Helper_Fragment extends Fragment implements Response.Listener<String>,Response.ErrorListener {
    protected LobbyActivity lobbyActivity;
    private RequestQueue requestQueue;
    protected Map<String,String> params = new HashMap<>();
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        lobbyActivity = (LobbyActivity) getActivity();
    }
    protected void requestVolley(String url){
        requestQueue = Volley.newRequestQueue(lobbyActivity);
        StringRequest st = new StringRequest(Request.Method.POST,url,
                this,this
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                lobbyActivity.getLog("user_data: "+params);
                return params;
            }
        };
        st.setRetryPolicy(new DefaultRetryPolicy(3000,0,1f));
        requestQueue.add(st);

    }

    protected void requestVolley(String url, Response.Listener<String> listener){
        requestQueue = Volley.newRequestQueue(lobbyActivity);
        StringRequest st = new StringRequest(Request.Method.POST,url,
                listener,this
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                lobbyActivity.getLog("user_data: "+params);
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
