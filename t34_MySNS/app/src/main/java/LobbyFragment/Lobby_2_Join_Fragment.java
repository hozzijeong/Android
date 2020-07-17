package LobbyFragment;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Response;
import com.example.t34_mysns.LobbyActivity;
import com.example.t34_mysns.R;

import org.json.JSONException;
import org.json.JSONObject;

import AppHelper.Helper_Fragment;

public class
Lobby_2_Join_Fragment extends Helper_Fragment implements View.OnClickListener {
    private EditText id,pw1,pw2,mail,name,nick;
    private Button idck,join,back;
    private RadioButton male;
    private String gender;
    private boolean id_possible = false;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.lobby_2_join_fragment,container,false);

        id = v.findViewById(R.id.join_id_et);
        pw1 = v.findViewById(R.id.join_pw1);
        pw2 = v.findViewById(R.id.join_pw2);
        mail = v.findViewById(R.id.join_mail);
        name = v.findViewById(R.id.join_name);
        nick = v.findViewById(R.id.join_nick);

        idck = v.findViewById(R.id.join_idck);
        join = v.findViewById(R.id.join_join);
        back = v.findViewById(R.id.join_quite);

        male = v.findViewById(R.id.joni_gender_male);

        idck.setOnClickListener(this);
        join.setOnClickListener(this);
        back.setOnClickListener(this);
        if(id_possible){
            id.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    //아이디 입력창에 다른 글을 입력시 중복체크 상황 초기화
                    id_possible = false;
                }
                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
        return v;
    }

    private void id_check(){
        String url = "http://hozzi.dothome.co.kr/sns/sns_check_id.php";
        params.clear();
        params.put("id",id.getText().toString());
        requestVolley(url,check_listener);

    }

    private Response.Listener<String> check_listener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                JSONObject json = new JSONObject(response);
                String result = json.getString("result");
                if(result.equalsIgnoreCase("ok")){
                    id_possible = true;
                    lobbyActivity.showToast("사용가능한 아이디입니다.");
                }else{
                    lobbyActivity.showToast("중복된 아이디 입니다.");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private void join(){
        String url = "http://hozzi.dothome.co.kr/sns/sns_member_join.php";
        String user_id = id.getText().toString();
        String user_pw = pw1.getText().toString();
        String user_name = name.getText().toString();
        String user_email = mail.getText().toString();
        String user_nick = nick.getText().toString();
        String user_gender = gender;
        String user_pro = "https://yt3.ggpht.com/a/AATXAJxMp-" +
                "xDRMsv1GVyLqJB4V6QPkKgVHBUhZuzyw=s100-c-k-c0xffffffff-no-rj-mo";

        params.clear();
        params.put("id",user_id);
        params.put("pw",lobbyActivity.setEnc(user_pw));
        params.put("name",user_name);
        params.put("mail",user_email);
        params.put("nick",user_nick);
        params.put("gender",user_gender);
        params.put("profile_url",user_pro);
        requestVolley(url,join_listener);
    }

    private Response.Listener<String> join_listener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                JSONObject json = new JSONObject(response);
                String result = json.getString("result");
                if(result.equalsIgnoreCase("ok")) {
                    lobbyActivity.showToast("회원가입을 환영합니다.");
                }else{
                    lobbyActivity.showToast("회원가입 실패했습니다.");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };




    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.join_join){
            if(male.isChecked()){
                gender = "male";
            }else{
                gender = "female";
            }
            if(pw_check() && id_possible){
                join();
                lobbyActivity.getFragment(0);
                id_possible = false;
            }else{
                lobbyActivity.getLog("id: "+id_possible +"pw: "+pw_check());
                lobbyActivity.showToast("아이디 또는 비밀번호를 확인하세요");
            }
        }else if(v.getId() == R.id.join_idck) {
            id_check();
        }else if(v.getId() == R.id.join_quite){
            lobbyActivity.getFragment(0);
        }
    }

    private boolean pw_check(){
        boolean possible =false;
        String p1 = pw1.getText().toString();
        String p2 = pw2.getText().toString();
        if(p1.equals(p2) && p1.length()>5){
            possible = true;
        }
        return possible;
    }

}
