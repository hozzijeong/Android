package com.example.t11_textrpg;

public class StageTitle extends Stage {
    String Title_title;
    String Title_message;
    @Override
    public boolean update() {
         Title_title = "======[TEXT RPG]======";
         Title_message = "[시작]을 입력하세요";
//        String start = GameManager.scan.next();
//        if(start.equals("시작")) {
//            GameManager.nextStage = "LOBBY";
//        }
        return false;
    }

    @Override
    public void init() {

    }
}
