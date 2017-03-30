package com.shenhua.nandagy.bean.bmobbean;

import java.io.Serializable;

import cn.bmob.v3.BmobUser;

public class MyUser extends BmobUser implements Serializable {

    private static final long serialVersionUID = 5589615413893455310L;
    private String userId;
    private String userName;
    private String phone;
    private String eMail;
    private String nick;//昵称
    private String name_num;//学号
    private String name;//姓名
    private String name_id;//sfz
    private String info;
    private String url_photo;//头像
    private Boolean sex;//性别  true女，false男
    private String userZoneObjID;

    public MyUser() {
    }

    public MyUser(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public MyUser(String nameNum, String nameId, String name) {
        this.name_num = nameNum;
        this.name_id = nameId;
        this.name = name;
    }

    public MyUser(String eMail, String info, String name, String name_id, String name_num, String nick,
                  String phone, Boolean sex, String url_photo, String userId, String userName, String userZoneObjID) {
        this.eMail = eMail;
        this.info = info;
        this.name = name;
        this.name_id = name_id;
        this.name_num = name_num;
        this.nick = nick;
        this.phone = phone;
        this.sex = sex;
        this.url_photo = url_photo;
        this.userId = userId;
        this.userName = userName;
        this.userZoneObjID = userZoneObjID;
    }

    public String getUserZoneObjID() {
        return userZoneObjID;
    }

    public void setUserZoneObjID(String userZoneObjID) {
        this.userZoneObjID = userZoneObjID;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

}
