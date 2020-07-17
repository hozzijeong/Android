package MainFragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Response;
import com.example.t34_mysns.LobbyActivity;
import com.example.t34_mysns.MainActivity;
import com.example.t34_mysns.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import AppHelper.Helper_Fragment;
import AppHelper.Helper_Main_Fragment;
import AppHelper.Main_2_Comment_Adapter;
import AppHelper.Main_Board_Comment;

public class Main_2_Comment_Fragment extends Helper_Main_Fragment implements View.OnClickListener {
    private Main_2_Comment_Adapter adapter;
    private ListView listView;
    private TextView tv;
    private int pos;
    private Button add_btn;
    private EditText cor_comment;


    public void setPos(int pos) {
        this.pos = pos;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.main_2_comment_fragment,container,false);
        // (feed_idx 번호)
        listView = v.findViewById(R.id.comment_lv);
        tv = v.findViewById(R.id.comment_tv);
        add_btn = v.findViewById(R.id.comment_add_btn);
        cor_comment = v.findViewById(R.id.comment_add);

        add_btn.setOnClickListener(this);
        load_comment();
        return v;
    }
    private void load_comment(){
        String url = "http://hozzi.woobi.co.kr/sns/load_comment.php";
        params.clear();
        params.put("board_idx",mainActivity.feeds.get(pos).board_idx+"");
        requestVolley(url,comment_listener);
    }
    private Response.Listener<String> comment_listener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                JSONObject json = new JSONObject(response);
                String result = json.getString("result");
                if(result.equalsIgnoreCase("ok")){
                    JSONArray array = json.getJSONArray("comment_info");
                    mainActivity.comments.clear();
                    for(int i=0; i<array.length();i++){
                        int board_idx = Integer.parseInt(array.getJSONObject(i).getString("board_idx"));
                        String author = array.getJSONObject(i).getString("author");
                        String content = array.getJSONObject(i).getString("content");
                        String url = array.getJSONObject(i).getString("profile");
                        mainActivity.comments.add(new Main_Board_Comment(board_idx,author,content,url));
                    }
                    if(mainActivity.comments != null){
                        tv.setText(mainActivity.feeds.get(pos).content);
                    }
                    adapter = new Main_2_Comment_Adapter(mainActivity,mainActivity.comments);
                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }else{
                    mainActivity.comments.clear();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };


    @Override
    public void onClick(View v) {
        String comment = cor_comment.getText().toString();
        if(comment.length() == 0){
            Toast.makeText(getContext(),"댓글을 입력하세요",Toast.LENGTH_LONG).show();
        }else{
            String url = "http://hozzi.woobi.co.kr/sns/add_comment.php";
            params.clear();
            params.put("author", LobbyActivity.myinfo.nick);
            params.put("content",comment);
            params.put("board_idx",mainActivity.feeds.get(pos).board_idx+"");
            params.put("url",LobbyActivity.myinfo.url);
            requestVolley(url,add_listener);
            cor_comment.setText("");
        }
    }

    Response.Listener<String> add_listener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            load_comment();
        }
    };


}
