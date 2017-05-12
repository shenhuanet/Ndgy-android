package com.shenhua.nandagy.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by shenhua on 5/9/2017.
 * Email shenhuanet@126.com
 */
@Entity
public class GreatHateFav {

    @Id(autoincrement = true)
    private Long id;
    private String userzoneId;
    private String postId;
    private boolean isGreat;
    private boolean isHate;
    private boolean isFav;

    @Generated(hash = 1171155449)
    public GreatHateFav(Long id, String userzoneId, String postId, boolean isGreat,
            boolean isHate, boolean isFav) {
        this.id = id;
        this.userzoneId = userzoneId;
        this.postId = postId;
        this.isGreat = isGreat;
        this.isHate = isHate;
        this.isFav = isFav;
    }

    @Generated(hash = 1079425514)
    public GreatHateFav() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserzoneId() {
        return this.userzoneId;
    }

    public void setUserzoneId(String userzoneId) {
        this.userzoneId = userzoneId;
    }

    public String getPostId() {
        return this.postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public boolean getIsGreat() {
        return this.isGreat;
    }

    public void setIsGreat(boolean isGreat) {
        this.isGreat = isGreat;
    }

    public boolean getIsHate() {
        return this.isHate;
    }

    public void setIsHate(boolean isHate) {
        this.isHate = isHate;
    }

    public boolean getIsFav() {
        return this.isFav;
    }

    public void setIsFav(boolean isFav) {
        this.isFav = isFav;
    }
}
