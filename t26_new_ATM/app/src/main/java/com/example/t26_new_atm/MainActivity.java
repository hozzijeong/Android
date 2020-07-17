package com.example.t26_new_atm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    Join_Fragment join_fragment;
    Login_Fragment login_fragment;
    FragmentManager fm;
    FragmentTransaction ft;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        join_fragment = new Join_Fragment();
        login_fragment = new Login_Fragment();
        ft.replace(R.id.main_fragment,login_fragment);
        ft.commit();
    }

    public void setFragment(int idx){
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        if(idx == 0){
            ft.replace(R.id.main_fragment,login_fragment);
        }else if(idx ==1){
            ft.replace(R.id.main_fragment,join_fragment);
        }else if(idx == 2){
            // 로그인 성공
            Intent intent = new Intent(this,com.example.t26_new_atm.UserActivity.class);
            startActivity(intent);
        }else if(idx == 3){
            finish();
        }
        ft.commit();
    }


}
