package AppHelper;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.android.volley.Response;
import com.example.t34_mysns.LobbyActivity;
import com.example.t34_mysns.MainActivity;
import com.example.t34_mysns.R;

import org.json.JSONException;
import org.json.JSONObject;

public class Main_Custom_Dialog extends Dialog {
    private EditText content;
    private Button quite,post;
    private MainActivity activity;
    private int check_pos;
    public Main_Custom_Dialog(final Activity context, final int check) {
        super(context);
        setContentView(R.layout.main_create_item);
        activity = (MainActivity)context;
        content = findViewById(R.id.create_content);
        quite = findViewById(R.id.create_quite);
        post = findViewById(R.id.create_post);
        this.check_pos = check;

        if (check_pos>=0){
            content.setText(activity.feeds.get(check_pos).content);
        }



        quite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss(); // 다이얼을 닫는 메소드
            }
        });


        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check_pos<0) {
                    String url = "http://hozzi.woobi.co.kr/sns/create_feed.php";
                    activity.params.clear();
                    activity.params.put("author", LobbyActivity.myinfo.nick);
                    activity.params.put("author_idx", LobbyActivity.myinfo.user_idx);
                    activity.params.put("content", content.getText().toString());
                    activity.params.put("like", "false");
                    activity.params.put("hash_tag", Hash_Tag());

                    activity.requestVolley(url, post_listener);
                    activity.getFragment(-1);
                    dismiss();
                }else{
                    String url = "http://hozzi.woobi.co.kr/sns/modify_my_feed.php";
                    activity.params.clear();
                    activity.params.put("board_idx",((MainActivity) context).feeds.get(check_pos).board_idx+"");
                    activity.params.put("content", content.getText().toString());
                    activity.params.put("hash_tag", Hash_Tag());
                    activity.requestVolley(url,modify_listener);
                    activity.custom_idx = -1;
                    activity.getFragment(-4);
                    dismiss();
                }
            }
        });
    }
    private Response.Listener<String> post_listener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                JSONObject temp = new JSONObject(response);
                String result = temp.getString("result");
                if (result.equalsIgnoreCase("ok")) {
                    activity.showToast("게시글 등록 완료");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };


    private Response.Listener<String> modify_listener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                JSONObject temp = new JSONObject(response);
                String result = temp.getString("result");
                if (result.equalsIgnoreCase("ok")) {
                    activity.showToast("게시글 수정 완료");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private String Hash_Tag(){
        String result = "";
        int size = content.length();
        for(int i=0; i<size; i++) {
            if (content.getText().charAt(i) == '#') {
                for (int j = i; j < size; j++) {
                    if (j == size-1 || content.getText().charAt(j+1) =='#') {
                        result += content.getText().toString().substring(i, j+1) + ",";
                        break;
                    }
                }
            }
        }
        if(result.length() != 0){
            result = result.substring(0,result.length()-1);
        }
        return result;
    }
}
