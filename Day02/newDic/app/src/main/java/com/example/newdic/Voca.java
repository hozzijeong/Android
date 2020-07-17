package com.example.newdic;

public class Voca {
    private String kor;
    private String eng;

    public String getKor() {
        return kor;
    }

    public void setKor(String kor) {
        this.kor = kor;
    }

    public String getEng() {
        return eng;
    }

    public void setEng(String eng) {
        this.eng = eng;
    }

    public Voca(String eng, String kor){
        eng = this.eng;
        kor = this.kor;
    }
}
