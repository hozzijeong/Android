package com.example.t11_textrpg;

import java.util.ArrayList;
import java.util.Random;

public class UnitManager {
    ArrayList<Player> player_list = new ArrayList<>();
    ArrayList<Unit> mon_list= new ArrayList<>();
    String path = "com.example.t11_textrpg.";
    String mons[] = {"UnitWolf","UnitOrc","UnitBat"};
    Random ran = new Random();

    public UnitManager() {
        player_list.add(new Player("전사",1000,45));
        player_list.add(new Player("마법사",700,60));
        player_list.add(new Player("도적",800,52));
    }

    void monster_rand_set(int size) {
        for(int i=0; i<size; i++) {
            int num = ran.nextInt(mons.length);
            try {
                //------------------------
                Class<?> clazz = Class.forName(path+mons[num]); // class 경로로 이동해서
                Object obj = clazz.newInstance(); // 그 클래스로 새로운 객체를 생성한 뒤, 그것을 obj에 저장한다.
                //-------------------------
                Unit temp = (Unit) obj; // 그렇게 저장한 object를 Unit으로 형변환 한 뒤,
                int hp = ran.nextInt(100)+100;
                int pw = ran.nextInt(10)+10;
                temp.init(hp, pw); // 체력과 파워를 세팅하고, monsterList에 추가한다.
                mon_list.add(temp);
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

}
