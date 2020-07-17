package com.example.t10_atm;

import java.util.ArrayList;

public class UserInfo {
    int idx,money;
    String id,pw,acc_num;
    ArrayList<Info> acc_info;
    public UserInfo(int idx,String id, String pw, int money, String acc_num){
        this.idx = idx;
        this.id = id;
        this.pw = pw;
        this.money = money;
        this.acc_num = acc_num;
        this.acc_info = new ArrayList<>();
    }
}

class Info{
    int user_idx,money,total_money;
    String name;
    Info(int user_idx,String id,int money, int total_money){
        this.user_idx = user_idx;
        this.name = id;
        this.money = money;
        this.total_money = total_money;
    }

}


