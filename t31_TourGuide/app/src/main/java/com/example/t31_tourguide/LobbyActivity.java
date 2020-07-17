package com.example.t31_tourguide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import Helper.AppHelper;
import LobbyFragment.App_Guide_Fragment;
import LobbyFragment.Personal_info_Fragment;

public class LobbyActivity extends AppCompatActivity implements View.OnClickListener {
    public Button start;
    ImageButton logo;
    Personal_info_Fragment info;
    App_Guide_Fragment app;
    FragmentManager fm;
    FragmentTransaction ft;
    RelativeLayout big_layout;
    Guide_Adapter adapter;
    ViewPager guide;
    ImageView[] btn,temp_btn;
    SharedPreferences sp;
    LinearLayout lobby_linear;
    boolean guide_check = false;
    boolean check =false;
    public static boolean is_checked;
    public int idx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);
        start = findViewById(R.id.lobby_start);
        logo = findViewById(R.id.lobby_logo);
        big_layout = findViewById(R.id.lobby_big_layout);
        guide = findViewById(R.id.guide);
        lobby_linear = findViewById(R.id.lobby_linear);

        btn = new ImageView[4];
        // 페이지에서 새로운 ImageView를 추가 하는 것임(동적으로)
        // 만약에 인터넷에서 데이터를 얻어 쓰는 거라면 이런 방식을 활용
//        temp_btn = new ImageView[4];
//        for(int i=0; i<btn.length; i++){
//            ImageView temp = new ImageView(this);
//            temp_btn[i] = temp;
//            lobby_linear.addView(temp);
//        }

        btn[0] = findViewById(R.id.circle1);
        btn[1] = findViewById(R.id.circle2);
        btn[2] = findViewById(R.id.circle3);
        btn[3] = findViewById(R.id.circle4);

        sp = getSharedPreferences("user_agree",MODE_PRIVATE);
        guide_check = sp.getBoolean("guide",false);
        if(sp.getBoolean("first",false)&&sp.getBoolean("second",false)
            && sp.getBoolean("guide",false)){
            is_checked = true;
        }else{
            is_checked = false;
        }

        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(is_checked){
                    moveMain();
                }else{
                    moveGuide();
                }
            }
        });

        AppHelper.getLog("first: "+sp.getBoolean("first",false));
        AppHelper.getLog("second: "+sp.getBoolean("second",false));
        AppHelper.getLog("guide: "+sp.getBoolean("guide",false));

        start.setOnClickListener(this);
        guide.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            int pos;
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//               for (int i = 0; i < imgArr.length; i++) {
//                   imgArr[i].setImageResource(R.drawable.circle_on);
//               }
//              imgArr[position].setImageResource(R.drawable.circle_off);
                this.pos = position;
                for (int i=0; i<btn.length; i++){
                    if(i == position){
                        btn[i].setImageResource(R.drawable.circle_off);
                    }else{
                        btn[i].setImageResource(R.drawable.circle_on);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        handler.sendEmptyMessageDelayed(0,1000);

    }

    private void moveGuide(){
        logo.setVisibility(View.INVISIBLE);
        big_layout.setVisibility(View.VISIBLE);
        adapter = new Guide_Adapter(getSupportFragmentManager());
        guide.setAdapter(adapter);
    }

    private void moveMain(){
        Intent intent = new Intent(this,com.example.t31_tourguide.MainActivity.class);
        startActivity(intent);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.lobby_start){
            if(!check){
                if(LobbyActivity.is_checked){
                   moveMain();
                }else{
                    guide.setVisibility(View.INVISIBLE);
                    info = new Personal_info_Fragment();
                    fm = getSupportFragmentManager();
                    ft = fm.beginTransaction();
                    ft.replace(R.id.lobby_big_layout,info);
                    ft.commit();
                    check = true;
                    guide_check = true;
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putBoolean("guide",guide_check);
                    editor.commit();
                }
            }
        }
    }


    class Guide_Adapter extends FragmentStatePagerAdapter{
        public Guide_Adapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            app = new App_Guide_Fragment();

            app.str = (position+1)+"장 설명";
            return app;
        }

        @Override
        public int getCount() {
            return 4;
        }
    }


    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if(msg.what == 0){
                AppHelper.getLog("handler operating");
                if(is_checked){
                    moveMain();
                }else{
                    moveGuide();
                }
            }
        }
    };


}
