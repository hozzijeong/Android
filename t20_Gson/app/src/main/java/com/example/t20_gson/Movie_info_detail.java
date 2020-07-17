package com.example.t20_gson;

import java.util.ArrayList;

public class Movie_info_detail {
    String movieCd ;
    String movieNm;
    String movieNmEn;
    String movieNmOg ;
    String showTm;
    String prdtYear;
    String openDt;
    String prdtStatNm;
    String typeNm;

    ArrayList<Genre> genres = new ArrayList<>();
    ArrayList<Actor> actors = new ArrayList<>();
    ArrayList<Director> directors = new ArrayList<>();
    ArrayList<Company> companys = new ArrayList<>();
    ArrayList<Audit> audits = new ArrayList<>();
}
    class Genre{
        String genreNm;
    }
    class Actor{
        String peopleNm;
        String peopleNmEn;
        String cast;
    }
    class Director{
        String peopleNm;
        String peopleNmEn;

    }
    class Company{
        String companyCd;
        String companyNm ;
        String companyNmEn;
        String companyPartNm;
    }
    class Audit{
        String auditNo;
        String watchGradeNm;

    }
