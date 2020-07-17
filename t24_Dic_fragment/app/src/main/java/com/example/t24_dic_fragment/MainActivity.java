package com.example.t24_dic_fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import Helper.Voca;

public class MainActivity extends AppCompatActivity {
    FragmentManager fm = null;
    FragmentTransaction ft = null;

    GLobby_Fragment gLobby_fragment = new GLobby_Fragment();
    Hangman_Fragment hangman_fragment = new Hangman_Fragment();
    Join_Fragment join_fragment = new Join_Fragment();
    List_Fragment list_fragment = new List_Fragment();
    Login_Fragment login_fragment = new Login_Fragment();
    WGame_Fragment wGame_fragment = new WGame_Fragment();

    int user_idx = -1;
    ArrayList<Voca> voca = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getFragment(5);

    }

    public void getFragment(int idx){
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        if(idx ==1){
            ft.replace(R.id.main_fragment,gLobby_fragment);
        }else if(idx == 2){
            ft.replace(R.id.main_fragment,hangman_fragment);
        }else if(idx ==3){
            ft.replace(R.id.main_fragment,join_fragment);
        }else if(idx == 4){
            ft.replace(R.id.main_fragment,list_fragment);
            Log.d("mood","idx: "+user_idx);
        }else if(idx ==5){
            ft.replace(R.id.main_fragment,login_fragment);
        }else if (idx == 6){
            ft.replace(R.id.main_fragment,wGame_fragment);
        }else if(idx == 0){
            ft.remove(login_fragment);
            finish();
        }
        ft.commit();
    }




}
