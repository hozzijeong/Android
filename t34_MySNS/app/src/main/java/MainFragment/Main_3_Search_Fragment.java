package MainFragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.Response;
import com.example.t34_mysns.MainActivity;
import com.example.t34_mysns.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import AppHelper.Helper_Fragment;
import AppHelper.Helper_Main_Fragment;
import AppHelper.Main_1_Feed_Adapter;
import AppHelper.Main_3_Search_Adapter;
import AppHelper.Main_Custom_Info;

public class Main_3_Search_Fragment extends Helper_Main_Fragment implements  AdapterView.OnItemClickListener {

    private Main_3_Search_Adapter adapter;
    private ListView lv;
    private EditText et;
    private Button btn;
    private TextView notice;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.main_3_search_fragment, container, false);
        lv = v.findViewById(R.id.search_lv);
        et = v.findViewById(R.id.search_et);
        btn= v.findViewById(R.id.search_btn);
        notice = v.findViewById(R.id.search_notice);

        notice.setText("");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://hozzi.dothome.co.kr/sns/load_search_info.php?search_data=" + et.getText().toString();
                GetVolley(url,common_listener);
            }
        });
        lv.setOnItemClickListener(this);
        return v;
    }


   private Response.Listener<String> common_listener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                mainActivity.getLog("search_Response: "+response);
                JSONObject json = new JSONObject(response);
                String result = json.getString("result");
                if (result.equalsIgnoreCase("ok")) {
                    mainActivity.customs.clear();
                    String name = json.getString("name");
                    String nick = json.getString("nick");
                    String url = json.getString("url");
                    String idx = json.getInt("idx")+"";
                    mainActivity.customs.add(new Main_Custom_Info(name, url, nick, idx));
                    mainActivity.getLog(mainActivity.customs.get(0).name);

                    adapter = new Main_3_Search_Adapter(mainActivity, mainActivity.customs);
                    lv.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }else{
                    notice.setText(json.getString("msg"));
                }
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mainActivity.getFragment(-4);
        mainActivity.custom_idx = position;
    }
}





