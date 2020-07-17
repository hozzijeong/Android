package com.example.t20_gson;

import java.util.ArrayList;

public class ActorInfo {
    String peopleCd;
    String peopleNm;
    String peopleNmEn;
    String sex ;
    String repRoleNm ;

    ArrayList<Filmos> filmos = new ArrayList<>();
}
class Filmos{
    String movieCd;
    String movieNm;
    String moviePartNm;
}
