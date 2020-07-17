package com.example.newdic;

public class Voca {
     String kor;
     String eng;
     int db_idx;
    public Voca(String eng, String kor){
      this.eng = eng;
      this.kor = kor;
    }
    public Voca(String eng, String kor,int db_idx){
      this.eng = eng;
      this.kor = kor;
      this.db_idx = db_idx;
    }
}
