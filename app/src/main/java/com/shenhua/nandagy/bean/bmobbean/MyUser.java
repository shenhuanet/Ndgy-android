package com.shenhua.nandagy.bean.bmobbean;

import java.io.Serializable;

import cn.bmob.v3.BmobUser;

public class MyUser extends BmobUser implements Serializable {

    private static final long serialVersionUID = 5589615413893455310L;
    private String url_photo;
    private String nick;
    private String name_num;
    private String name_id;
    private String name;
    private String info;// 记录最近一次操作
    private Boolean sex;
    private UserZone userZone;

    public MyUser() {
    }

    public MyUser(String nameNum, String nameId, String name) {
        this.name_num = nameNum;
        this.name_id = nameId;
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName_id() {
        return name_id;
    }

    public void setName_id(String name_id) {
        this.name_id = name_id;
    }

    public String getName_num() {
        return name_num;
    }

    public void setName_num(String name_num) {
        this.name_num = name_num;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public String getUrl_photo() {
        return url_photo;
    }

    public void setUrl_photo(String url_photo) {
        this.url_photo = url_photo;
    }

    public UserZone getUserZone() {
        return userZone;
    }

    public void setUserZone(UserZone userZone) {
        this.userZone = userZone;
    }

}
