package com.example.t08_newdic;

import java.io.Serializable;
@SuppressWarnings("serial")
public class Rank implements Serializable {
    String id;
    int score;

    public Rank(String id,int score){
        this.id = id;
        this.score = score;
    }
}
