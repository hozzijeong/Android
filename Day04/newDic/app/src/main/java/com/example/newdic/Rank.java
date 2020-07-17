package com.example.newdic;

public class Rank {
    String name;
    int score;

    Rank(String name,int score){
        this.name = name;
        this.score = score;
    }

    public String printRank(){
        String data = name+":"+score+"점";
        return data;
    }
}
