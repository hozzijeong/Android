package AppHelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Response;
import com.example.t34_mysns.LobbyActivity;
import com.example.t34_mysns.MainActivity;
import com.example.t34_mysns.R;

import org.json.JSONException;
import org.json.JSONObject;

public class Main_Setting_Dialog extends Dialog {
    private Button modify,remove,quite;
    private MainActivity activity;
    private int pos;

    public Main_Setting_Dialog(Activity context,int position) {
        super(context);
        setContentView(R.layout.main_setting_item);
        activity = (MainActivity)context;
        quite = findViewById(R.id.feed_quite);
        modify = findViewById(R.id.feed_modify);
        remove = findViewById(R.id.feed_remove);
        this.pos = position;

        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                activity.dialog = new Main_Custom_Dialog(activity,pos);
                activity.dialog.show();
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ab = new AlertDialog.Builder(activity);
                ab.setTitle("메세지").setMessage("정말로 삭제 하시겠습니까? ");
                ab.setPositiveButton("네", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String url = "http://hozzi.woobi.co.kr/sns/remove_my_feed.php";
                        activity.params.clear();
                        activity.params.put("board_idx",activity.feeds.get(pos).board_idx+"");
                        activity.requestVolley(url,delete_listener);
                    }
                });
                ab.setNegativeButton("아니요", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                });
                ab.show();
            }
        });

        quite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss(); // 다이얼을 닫는 메소드
            }
        });
    }

    private Response.Listener<String> delete_listener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                JSONObject json = new JSONObject(response);
                String result = json.getString("result");
                if(result.equalsIgnoreCase("ok")){
                    activity.showToast("삭제 완료");
                    activity.feeds.remove(pos);
                }else{
                    activity.showToast("삭제 실패");
                }
                dismiss();
                activity.myFeed_fragment.data_Change();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    };
}
