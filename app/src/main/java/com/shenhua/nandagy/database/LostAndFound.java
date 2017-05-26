package com.shenhua.nandagy.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by shenhua on 5/22/2017.
 * Email shenhuanet@126.com
 */
@Entity
public class LostAndFound {

    @Id(autoincrement = true)
    private Long id;
    private String userId;
    private String title;
    private Integer type;
    private String describe;
    private String contact;
    private String picsUrl;
    private boolean isResolved;

    @Generated(hash = 703878669)
    public LostAndFound(Long id, String userId, String title, Integer type,
                        String describe, String contact, String picsUrl, boolean isResolved) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.type = type;
        this.describe = describe;
        this.contact = contact;
        this.picsUrl = picsUrl;
        this.isResolved = isResolved;
    }

    @Generated(hash = 2025737432)
    public LostAndFound() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPicsUrl() {
        return this.picsUrl;
    }

    public void setPicsUrl(String picsUrl) {
        this.picsUrl = picsUrl;
    }
}
