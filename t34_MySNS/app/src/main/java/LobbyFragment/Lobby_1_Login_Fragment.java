package LobbyFragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.t34_mysns.LobbyActivity;
import com.example.t34_mysns.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import AppHelper.Helper_Fragment;
import AppHelper.Main_My_Info;

public class Lobby_1_Login_Fragment extends Helper_Fragment implements View.OnClickListener {
    private Button login,join,find,quite;
    private EditText id,pw;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.lobby_1_login_fragment,container,false);
        login = v.findViewById(R.id.login_login);
        join = v.findViewById(R.id.login_join);
        find = v. findViewById(R.id.login_find);
        quite = v.findViewById(R.id.login_quite);

        id = v.findViewById(R.id.login_id_et);
        pw = v.findViewById(R.id.login_pw_et);

        login.setOnClickListener(this);
        join.setOnClickListener(this);
        find.setOnClickListener(this);
        quite.setOnClickListener(this);

        return v;
    }

    private void move_Login(){
        String user_id = id.getText().toString();
        String user_pw = pw.getText().toString();
        String url = "http://hozzi.dothome.co.kr/sns/sns_login.php";
        params.clear();
        params.put("id",user_id);
        params.put("pw",lobbyActivity.setEnc(user_pw));
        requestVolley(url);
    }

    @Override
    public void onResponse(String response) {
        super.onResponse(response);
        try {
            JSONObject json = new JSONObject(response);
            lobbyActivity.getLog("login_data"+response);
            String result = json.getString("result");
            if(result.equalsIgnoreCase("ok")){
                String name = json.getString("name");
                String nick = json.getString("nick");
                String pro_url = json.getString("profile");
                String user_idx = json.getString("idx");
                LobbyActivity.myinfo = new Main_My_Info(name,pro_url,nick,user_idx);
                if(LobbyActivity.myinfo != null){
                    lobbyActivity.moveMain();
                    lobbyActivity.save_login_info(id.getText().toString(),
                            lobbyActivity.setEnc(pw.getText().toString()),false);
                }
            }else{
                lobbyActivity.showToast("로그인 실패...");
                lobbyActivity.save_login_info(null,null,true);
            }
            id.setText("");
            pw.setText("");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.login_login){
            move_Login();
        }else if(v.getId() == R.id.login_join){
            // 개인정보 동의 fragment 로 이동할 것
            lobbyActivity.getFragment(2);
        }else if(v.getId() == R.id.login_find){
            // 아이디,비밀번호 찾기 fragment 로 이동할 것.
        }else if(v.getId() == R.id.login_quite){
            lobbyActivity.finish();
        }
    }




}
