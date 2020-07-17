package com.example.t19_mvquiz;

import java.util.ArrayList;

public class mv_code {
    String mv_cd;
    // 생성자 설정이 좀 어려워서 기본으로 만들고, 나중에 추가를 했는데 null이 나옴...?

    mv_code(String mv_cd){
        this.mv_cd = mv_cd;
    }
}

class actor_info{
    String gender;
    ArrayList<String> mv_list;
    String name;
    String mv_cd;

    actor_info(String mv_cd,String gender, ArrayList<String> mv_list,String name){
        this.gender = gender;
        this.mv_list = mv_list;
        this.name = name;
        this.mv_cd = mv_cd;
    }
}
