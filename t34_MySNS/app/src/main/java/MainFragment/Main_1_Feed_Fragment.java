package MainFragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Response;
import com.example.t34_mysns.MainActivity;
import com.example.t34_mysns.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import AppHelper.Helper_Fragment;
import AppHelper.Helper_Main_Fragment;
import AppHelper.Main_1_Feed_Adapter;
import AppHelper.Main_Board_Comment;
import AppHelper.Main_Feed_Info;

public class Main_1_Feed_Fragment extends Helper_Main_Fragment {

    private Main_1_Feed_Adapter adapter;
    private ListView feed_lv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.main_1_feed_fragment,container,false);
        feed_lv = v.findViewById(R.id.main_feed);
        load_feed();
        return v;
    }

    private void load_feed(){
        String url = "http://hozzi.woobi.co.kr/sns/load_feed.php";
        params.clear();
        requestVolley(url,post_listener);
    }

    private void load_hash(){
        String url = "http://hozzi.woobi.co.kr/sns/load_hash_tag.php";
        params.clear();
        requestVolley(url,hash_listener);
    }

    private Response.Listener<String> post_listener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                JSONObject json = new JSONObject(response);
                String result = json.getString("result");
                mainActivity.getLog("feed_data: "+response);
                if(result.equalsIgnoreCase("ok")){
                    mainActivity.feeds.clear();
                    JSONArray array = json.getJSONArray("feed_info");
                    for(int i=0; i<array.length(); i++){
                        int board_idx = Integer.parseInt(array.getJSONObject(i).getString("board_idx"));
                        String author = array.getJSONObject(i).getString("author");
                        int author_idx = Integer.parseInt(array.getJSONObject(i).getString("author_idx"));
                        String content = array.getJSONObject(i).getString("content");
                        mainActivity.feeds.add(new Main_Feed_Info(board_idx,author,author_idx,content));
                    }
                    load_hash();
                    adapter = new Main_1_Feed_Adapter(mainActivity,mainActivity.feeds);
                    feed_lv.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    Response.Listener<String> hash_listener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                JSONObject json = new JSONObject(response);
                String result = json.getString("result");
                if(result.equalsIgnoreCase("ok")){
                    JSONArray array = json.getJSONArray("hash_info");
                    for(int i=0; i<array.length(); i++){
                        int idx = Integer.parseInt(array.getJSONObject(i).getString("board_idx"));
                        String data = array.getJSONObject(i).getString("content");
                        /*
                            파라미터에 데이터를 전송 할 때, 현재 로그인 한 값을 대입 해야 하는 것일까?
                            여기서 idx를 탐색 한 후, 해당하는 idx의 hash에 data를 Add 해주면 되는데,
                            나는 그걸 잘 모르니까.... 일일이 다 해야함... 좀 불편한데...
                         */

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };



}
