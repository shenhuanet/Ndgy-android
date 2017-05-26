package com.shenhua.nandagy.bean.bmobbean;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by shenhua on 5/22/2017.
 * Email shenhuanet@126.com
 */
public class LostAndFound extends BmobObject {

    private static final long serialVersionUID = 5275372492091203381L;
    public static final int TYPE_LOST = 0;
    public static final int TYPE_FOUND = 1;
    private String title;
    private Integer type;
    private String describe;
    private String contact;
    private boolean isResolved;
    private MyUser user;
    private List<BmobFile> pics;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDescribe() {
        return this.describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getContact() {
        return this.contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public boolean getIsResolved() {
        return this.isResolved;
    }

    public void setIsResolved(boolean isResolved) {
        this.isResolved = isResolved;
    }

    public MyUser getUser() {
        return user;
    }

    public void setUser(MyUser user) {
        this.user = user;
    }

    public List<BmobFile> getPics() {
        return pics;
    }

    public void setPics(List<BmobFile> pics) {
        this.pics = pics;
    }

}
