package com.example.t34_mysns;


import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;


import java.util.ArrayList;

import AppHelper.Helper;
import AppHelper.Main_Board_Comment;
import AppHelper.Main_Custom_Dialog;
import AppHelper.Main_Custom_Info;
import AppHelper.Main_Feed_Info;
import MainFragment.Main_1_Feed_Fragment;
import MainFragment.Main_2_Comment_Fragment;
import MainFragment.Main_3_Search_Fragment;
import MainFragment.Main_4_MyFeed_Fragment;
import MainFragment.Main_5_Setting_Fragment;

public class MainActivity extends Helper implements View.OnClickListener {
    public FragmentManager fm;
    public FragmentTransaction ft;
    public Main_1_Feed_Fragment feed_fragment;
    public Main_2_Comment_Fragment comment_fragment;
    public Main_3_Search_Fragment search_fragment;
    public Main_4_MyFeed_Fragment myFeed_fragment;
    public Main_5_Setting_Fragment setting_fragment;
    public Button my_feed;
    public ImageButton home,create,search,back,setting;
    public RelativeLayout main,sub;
    public LinearLayout downside;
    public Main_Custom_Dialog dialog;

    public DrawerLayout drawer;
    public ListView slide_setting;
    public ArrayAdapter adapter;

    public ArrayList<Main_Feed_Info> feeds = new ArrayList<>();
    public ArrayList<Main_Board_Comment> comments = new ArrayList<>();
    public ArrayList<Main_Custom_Info> customs = new ArrayList<>();
    ArrayList<String> slide_menu = new ArrayList<>();

    public int custom_idx;

    /*
        feeds 는 woobi에 저장된 정보에 따라 업로드 된다.
        comments 역시 woobi에 저장된 정보에 따라 업로드 된다.
        feeds 안에 comments 는 comments 에 저장된 board_idx에 따라 자동으로 정렬되어 나뉘게 된다.

        업로드 순서(날짜, 선호도) 등은 서버에서 데이터를 로드해줄 때 본인이 원하는 방식으로 key값을 요청하는 방식으로
        해결 할 수 있기 때문에, 굳이 date를 저장할 필요가 없다.

        206030 해시태그에 클릭기능 추가 구현 할것(url이용)
                검색 기능 우선 구현 -> 검색기능 구현 후 해시태그 url 클릭같이 구현하면 좋음
                환경설정에서 게시글 수정/삭제 가능하게끔 서버 php 설정할 것.

        20200703 나의 게시글 '수정'을 누르고 수정 하면, 이전 내용 그대로 나타나고, 삭제를 누르면 삭제가 되지 않음.

                마지막으로 카메라 기능 구현해보기


        카메라 intent로 확인
        카메라의 permission.CAMERA
        READ_EXTERNAL_STORAGE & WRITE_EXTERNAL_STORAGE

        글 작성할 때, author_idx가 저장이 안된다. 그것 해결하자

     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        home = findViewById(R.id.main_home);
        search = findViewById(R.id.main_search);
        my_feed = findViewById(R.id.main_my_feed);
        setting = findViewById(R.id.main_setting);
        create = findViewById(R.id.main_create);
        back = findViewById(R.id.back);
        main = findViewById(R.id.main_layout);
        sub = findViewById(R.id.sub_layout);
        downside = findViewById(R.id.downside_layout);
        drawer = findViewById(R.id.drawer);
        slide_setting = findViewById(R.id.slide_setting);

        home.setOnClickListener(this);
        search.setOnClickListener(this);
        create.setOnClickListener(this);
        my_feed.setOnClickListener(this);
        setting.setOnClickListener(this);
        back.setOnClickListener(this);




        getLog("name: "+LobbyActivity.myinfo.name);
        getFragment(-1);
    }

    public void getFragment(int idx){
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        feed_fragment = new Main_1_Feed_Fragment();
        comment_fragment = new Main_2_Comment_Fragment();
        search_fragment = new Main_3_Search_Fragment();
        myFeed_fragment = new Main_4_MyFeed_Fragment();
        setting_fragment = new Main_5_Setting_Fragment();
        if(idx == -1){
            ft.replace(R.id.main_layout,feed_fragment);
            main.setVisibility(View.VISIBLE);
            downside.setVisibility(View.VISIBLE);
            sub.setVisibility(View.INVISIBLE);
        }else if(idx == -2){
            ft.replace(R.id.sub_layout,search_fragment);
            main.setVisibility(View.INVISIBLE);
            downside.setVisibility(View.INVISIBLE);
            sub.setVisibility(View.VISIBLE);
        }else if(idx == -4){
            myFeed_fragment.setPos(custom_idx);
            ft.replace(R.id.sub_layout,myFeed_fragment);
            main.setVisibility(View.INVISIBLE);
            downside.setVisibility(View.INVISIBLE);
            sub.setVisibility(View.VISIBLE);
        }else{
            comment_fragment.setPos(idx);
            ft.replace(R.id.sub_layout,comment_fragment);
            main.setVisibility(View.INVISIBLE);
            downside.setVisibility(View.INVISIBLE);
            sub.setVisibility(View.VISIBLE);
        }
        ft.commit();
    }

    private void setSlide(){
        slide_menu.clear();
        slide_menu.add("설정");
        slide_menu.add("환경설정");
        slide_menu.add("개인정보 변경");
        slide_menu.add("로그아웃");
        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,slide_menu);
        slide_setting.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        if(v.getId()  == R.id.main_home){
            getFragment(-1);
        }else if(v.getId() == R.id.main_search){
            getFragment(-2);
        }else if(v.getId() == R.id.main_create){
            dialog = new Main_Custom_Dialog(this,-1);
            dialog.show();
        }else if(v.getId() == R.id.main_my_feed){
            custom_idx = -1;
            getFragment(-4);
        }else if(v.getId() == R.id.main_setting){
            setSlide();
            drawer.openDrawer(GravityCompat.END);
            slide_setting.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    showToast("slide_pos: "+position);
                    if(position == 0){

                    }else if(position == 1){

                    }else if(position == 2){

                    }else if(position == 3){
                        LobbyActivity activity = new LobbyActivity();
                        activity.sp = getSharedPreferences("sns",MODE_PRIVATE);
                        activity.editor = activity.sp.edit();
                        activity.reset_login_info();
                        finish();
                    }
                    drawer.closeDrawer(GravityCompat.END);
                }
            });
        }else if(v.getId() == R.id.back){
            getFragment(-1);
        }
    }
}
