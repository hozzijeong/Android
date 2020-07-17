package MainFragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.t34_mysns.LobbyActivity;
import com.example.t34_mysns.MainActivity;
import com.example.t34_mysns.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import AppHelper.Helper_Fragment;
import AppHelper.Helper_Main_Fragment;
import AppHelper.Main_1_Feed_Adapter;
import AppHelper.Main_4_MyFeed_Adapter;
import AppHelper.Main_Feed_Info;
import AppHelper.Main_Setting_Dialog;

public class Main_4_MyFeed_Fragment extends Helper_Main_Fragment{

    private Main_4_MyFeed_Adapter adapter;
    private RecyclerView rv;
    private GridLayoutManager manager;
    private TextView my_name,my_nick,feed_num,message;
    private ImageView my_profile;
    private int pos;

    public void setPos(int idx){
        this.pos = idx;
    }

    public void data_Change(){
        feed_num.setText(mainActivity.feeds.size()+"개");
        manager = new GridLayoutManager(mainActivity,3);
        rv.setLayoutManager(manager);
        adapter = new Main_4_MyFeed_Adapter(mainActivity,mainActivity.feeds);
        rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.main_4_myfeed_fragment,container,false);
        rv = v.findViewById(R.id.my_feeds);
        my_name = v.findViewById(R.id.my_name);
        my_nick = v.findViewById(R.id.my_nickname);
        feed_num = v.findViewById(R.id.my_feed_num);
        my_profile = v.findViewById(R.id.my_profile);
        message = v.findViewById(R.id.my_message);
        String url = "http://hozzi.woobi.co.kr/sns/load_my_feed.php";
        params.clear();
        mainActivity.getLog("custom_pos: "+pos);
        feed_num.setText("게시물 없음");
        if(pos <0){
            my_name.setText(LobbyActivity.myinfo.name);
            my_nick.setText(LobbyActivity.myinfo.nick);

            Glide.with(this).load(LobbyActivity.myinfo.url).into(my_profile);
            params.put("author_idx",LobbyActivity.myinfo.user_idx);
        }else{
            my_name.setText(mainActivity.customs.get(pos).name);
            my_nick.setText(mainActivity.customs.get(pos).nick);
            /*
                검색을 했을 때, 해시태그에 걸린 idx 값은 어떻게 처리하지?
             */
            Glide.with(this).load(mainActivity.customs.get(pos).url).into(my_profile);
            params.put("author_idx",mainActivity.customs.get(pos).user_idx);
        }
        requestVolley(url);
        return v;
    }

    @Override
    public void onResponse(String response) {
        super.onResponse(response);
        try {
            JSONObject json = new JSONObject(response);
            String result = json.getString("result");
            mainActivity.getLog("my_feed_data: "+response);
            if(result.equalsIgnoreCase("ok")){
                mainActivity.feeds.clear();
                JSONArray array = json.getJSONArray("feed_info");
                for(int i=0; i<array.length(); i++){
                    int board_idx = Integer.parseInt(array.getJSONObject(i).getString("board_idx"));
                    String author = array.getJSONObject(i).getString("author");
                    int author_idx = Integer.parseInt(array.getJSONObject(i).getString("author_idx"));
                    String content = array.getJSONObject(i).getString("content");
                    boolean like = Boolean.getBoolean(array.getJSONObject(i).getString("like"));
                    String hash_tag = array.getJSONObject(i).getString("hash_tag");
                    mainActivity.feeds.add(new Main_Feed_Info(board_idx,author,author_idx,content,like,hash_tag));
                }
                data_Change();
            }else{
                message.setText(json.getString("msg"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
