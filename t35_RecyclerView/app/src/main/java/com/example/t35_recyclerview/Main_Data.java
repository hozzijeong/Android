package com.example.t35_recyclerview;


public class Main_Data {
    private int iv_profile;
    private String name,content;

    public Main_Data(int iv_profile, String name, String content) {
        this.iv_profile = iv_profile;
        this.name = name;
        this.content = content;
    }

    public int getIv_profile() {
        return iv_profile;
    }

    public void setIv_profile(int iv_profile) {
        this.iv_profile = iv_profile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
