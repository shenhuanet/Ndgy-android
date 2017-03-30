package com.shenhua.nandagy.bean.bmobbean;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * 用户空间工具类
 * Created by shenhua on 9/9/2016.
 */
public class UserZone extends BmobObject implements Serializable {

    private static final long serialVersionUID = -4614475789674374508L;
    private Integer level;
    private Integer dynamic;
    private Integer mi;
    private Integer exper;
    private boolean sex;
    private String photoUrl;
    private String dynamicStr;
    private String name;
    private String sign;
    private String birth;
    private String locate;
    private String love;
    private String depart;
    private String qual;
    private String highSchool;

    public UserZone() {
    }

    public UserZone(Integer level, Integer dynamic, Integer mi, Integer exper) {
        this.dynamic = dynamic;
        this.exper = exper;
        this.level = level;
        this.mi = mi;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public boolean getSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public Integer getDynamic() {
        return dynamic;
    }

    public void setDynamic(Integer dynamic) {
        this.dynamic = dynamic;
    }

    public Integer getExper() {
        return exper;
    }

    public void setExper(Integer exper) {
        this.exper = exper;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getMi() {
        return mi;
    }

    public void setMi(Integer mi) {
        this.mi = mi;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public String getDynamicStr() {
        return dynamicStr;
    }

    public void setDynamicStr(String dynamicStr) {
        this.dynamicStr = dynamicStr;
    }

    public String getHighSchool() {
        return highSchool;
    }

    public void setHighSchool(String highSchool) {
        this.highSchool = highSchool;
    }

    public String getLocate() {
        return locate;
    }

    public void setLocate(String locate) {
        this.locate = locate;
    }

    public String getLove() {
        return love;
    }

    public void setLove(String love) {
        this.love = love;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQual() {
        return qual;
    }

    public void setQual(String qual) {
        this.qual = qual;
    }

    public boolean isSex() {
        return sex;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
