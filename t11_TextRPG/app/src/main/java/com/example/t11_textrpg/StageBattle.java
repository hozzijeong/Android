package com.example.t11_textrpg;

import java.util.ArrayList;
import java.util.Random;

public class StageBattle extends Stage {
    UnitManager unitManager = new UnitManager();
    ArrayList<Player> playerList = null;
    ArrayList<Unit> monList = null;
    Random ran = new Random();
    int monDead = 0;
    int playerDead = 0;

    @Override
    public void init() {
        unitManager.mon_list.clear();
        unitManager.monster_rand_set(unitManager.mons.length);
        playerList =null;
        playerList = unitManager.player_list;
        monList = null;
        monList = unitManager.mon_list;
        monDead = monList.size();
        playerDead = playerList.size();
    }

    void print_chracter() {
        System.out.println("======[BATTLE]======");

        System.out.println("======[PLAYER]======");

        for(int i=0; i<playerList.size();i++) {
            playerList.get(i).printData();
        }

        System.out.println("======[MONSTER]======");
        for(int i=0; i<monList.size(); i++) {
            monList.get(i).printData();
        }
    }

    void player_attact(int idx) {
        Player p = playerList.get(idx);
        if(p.curhp <=0) {
            System.out.println("[메세지] 죽은 플레이어");
            return;
        }
        System.out.println("[메뉴 선택]");
        System.out.println("["+p.name+"] [1.어택] [2.스킬]");
        int sel = GameManager.scan.nextInt();
        if(sel ==1) {
            while(true) {
                int index = ran.nextInt(monList.size());
                if(monList.get(index).curhp>0) {
                    p.attack(monList.get(index));
                    break;
                }
            }
        }else if(sel ==2) {}
    }

    void monster_attack(int idx) {
        Unit m = monList.get(idx);
        if(m.curhp <=0) {
            System.out.println("[메세지] 이미 죽은 몬스터");
            return;
        }
        while(true) {
            int index = ran.nextInt(playerList.size());
            if(playerList.get(index).curhp>0	) {
                m.attack(playerList.get(index));
                break;
            }
        }
    }

    void check_live() {
        int num = 0;
        for(int i=0; i<playerList.size();i++) {
            if(playerList.get(i).curhp <=0) {
                num+=1;
            }
        }

        playerDead = playerList.size() - num;
        num = 0;
        for(int i=0; i<monList.size();i++) {
            if(monList.get(i).curhp <=0) {
                num+=1;
            }
        }
        monDead = monList.size() - num;
    }


    @Override
    public boolean update() {
        boolean run = true;
        int p_index = 0;
        int m_index =0;
        boolean turn = true;
        while(run) {
            if(turn) {
                print_chracter();
                if(p_index < playerList.size()) {
                    player_attact(p_index);

                    p_index +=1;
                }else {
                    turn = !turn;
                    p_index =0;
                }
            }else if(!turn) {
                if(m_index < monList.size()) {
                    monster_attack(m_index);
                    m_index +=1;
                }else {
                    turn = !turn;
                    m_index=0;
                }
            }
            check_live();
            if(monDead<=0 || playerDead <=0) break;
        }
        GameManager.nextStage = "LOBBY";
        return false;
    }

}
