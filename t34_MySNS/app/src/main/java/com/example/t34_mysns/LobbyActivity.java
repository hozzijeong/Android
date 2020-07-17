package com.example.t34_mysns;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import org.json.JSONException;
import org.json.JSONObject;

import AppHelper.Helper;
import AppHelper.Main_My_Info;
import LobbyFragment.Lobby_1_Login_Fragment;
import LobbyFragment.Lobby_2_Join_Fragment;
import LobbyFragment.Lobby_3_Agree_Fragment;
import LobbyFragment.Lobby_4_Guide_Fragment;

public class LobbyActivity extends Helper {
    public FragmentManager fm;
    public FragmentTransaction ft;
    public ViewPager guide_pager;
    public Guide_Adapter adapter;
    public Lobby_1_Login_Fragment login;
    public Lobby_2_Join_Fragment join;
    public Lobby_3_Agree_Fragment agree;
    public Lobby_4_Guide_Fragment guide;
    public RelativeLayout guide_layout,lobby_layout;
    public Boolean guide_condition = false;
    public static Main_My_Info myinfo;
    ImageView[] btn = new ImageView[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        sp = getSharedPreferences("sns",MODE_PRIVATE);
        editor = sp.edit();

        guide_pager = findViewById(R.id.guide);
        guide_layout = findViewById(R.id.lobby_guide_layout);
        lobby_layout = findViewById(R.id.lobby_layout);

        btn[0] = findViewById(R.id.lobby_btn1);
        btn[1] = findViewById(R.id.lobby_btn2);
        btn[2] = findViewById(R.id.lobby_btn3);
        btn[3] = findViewById(R.id.lobby_btn4);

        guide_condition = sp.getBoolean("guide",false);

//        getLog("guide_condition: "+guide_condition);
//        getLog("condition: "+sp.getBoolean("log_con",true));
//        getLog("id: "+sp.getString("id",null));
//        getLog("pw: "+sp.getString("pw",null));

        if(guide_condition){
            getFragment(0);
            if(!sp.getBoolean("log_con",true)){
                String url = "http://hozzi.dothome.co.kr/sns/sns_login.php";
                params.clear();
                params.put("id",sp.getString("id",null));
                params.put("pw",sp.getString("pw",null));
                requestVolley(url);
            }
        }else{
            getFragment(3);
        }

        guide_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                setMark(position);
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    @Override
    public void onResponse(String response) {
        super.onResponse(response);
        try {
            JSONObject json = new JSONObject(response);
            String result = json.getString("result");
            if(result.equals("ok")){
                String name = json.getString("name");
                String nick = json.getString("nick");
                String pro_url = json.getString("profile");
                String user_idx = json.getString("idx");
                LobbyActivity.myinfo = new Main_My_Info(name,pro_url,nick,user_idx);
                if(LobbyActivity.myinfo != null){
                    moveMain();
                    save_login_info(sp.getString("id",null),
                            sp.getString("pw",null),false);
                    getLog("자동 로그인 \nid: "+sp.getString("id",null)+"\npw: "+ sp.getString("pw",null));
                }
            }else{
                showToast("로그인 실패...");
                reset_login_info();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void save_login_info(String id, String pw,boolean result){
        editor.putString("id",id);
        editor.putString("pw",pw);
        editor.putBoolean("log_con",result);
        editor.commit();
    }
    public void reset_login_info(){
        editor.putString("id",null);
        editor.putString("pw",null);
        editor.putBoolean("log_con",true);
        editor.commit();
    }
    public void getFragment(int idx){
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();

        login = new Lobby_1_Login_Fragment();
        agree = new Lobby_3_Agree_Fragment();
        join = new Lobby_2_Join_Fragment();

        if(idx != 3){
            lobby_layout.setVisibility(View.VISIBLE);
            guide_layout.setVisibility(View.INVISIBLE);
        }else{
            lobby_layout.setVisibility(View.INVISIBLE);
            guide_layout.setVisibility(View.VISIBLE);
            setMark(0);
        }

        if(idx == 0){
            //로그인 화면으로 이동
            ft.replace(R.id.lobby_layout,login);
        }else if(idx ==1){
            // 회원가입 화면으로 이동
            ft.replace(R.id.lobby_layout,join);
        }else if (idx ==2){
            // 개인정보 동의로 이동
            ft.replace(R.id.lobby_layout,agree);
        }else if(idx ==3){
            // 사용 방법으로 이동
            adapter = new Guide_Adapter(fm);
            guide_pager.setAdapter(adapter);
        }
        ft.commit();

    }
    private void setMark(int idx){
        for(int i=0; i<btn.length; i++){
            btn[i].setBackgroundResource(R.drawable.circle_off_shape);
        }
        btn[idx].setBackgroundResource(R.drawable.circle_on_shape);
    }
    class Guide_Adapter extends FragmentStatePagerAdapter{
        public Guide_Adapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            guide = new Lobby_4_Guide_Fragment();
            guide.text = (position+1)+"장 설명";
            if(position == getCount()){
                guide.guide_btn.setVisibility(View.VISIBLE);
                guide.guide_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        guide_condition = true;
                        editor.putBoolean("guide",guide_condition);
                        editor.commit();
                        getFragment(0);
                    }
                });
            }
            return guide ;
        }

        @Override
        public int getCount() {
            return 4;
        }
    }
}
