package com.people.matzip3;

public class MainData {

    private String img;
    private String tv_name;
    private String tv_menu;
    private String tv_addr;


    public MainData(String img, String tv_name, String tv_menu, String tv_addr) {
        this.img = img;
        this.tv_name = tv_name;
        this.tv_menu = tv_menu;
        this.tv_addr = tv_addr;
    }


    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTv_name() {
        return tv_name;
    }

    public void setTv_name(String tv_name) {
        this.tv_name = tv_name;
    }

    public String getTv_menu() {
        return tv_menu;
    }

    public void setTv_menu(String tv_menu) {
        this.tv_menu = tv_menu;
    }

    public String getTv_addr() {
        return tv_addr;
    }

    public void setTv_addr(String tv_addr) {
        this.tv_addr = tv_addr;
    }
}
