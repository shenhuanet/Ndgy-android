package com.shenhua.nandagy.bean.bmobbean;

import java.io.Serializable;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * 校内圈子(帖子)
 * Created by Shenhua on 10/6/2016.
 * e-mail shenhuanet@126.com
 */
public class SchoolCircle extends BmobObject implements Serializable {

    private static final long serialVersionUID = -1042012306150329924L;
    private UserZone userzone;
    private String content;
    private List<BmobFile> pics;
    private BmobRelation likes;
    private Integer hate;
    private Integer comment;
    private Integer share;
    private boolean isFavorite;
    private boolean isLove;
    private boolean isAudit;

    public UserZone getUserZone() {
        return userzone;
    }

    public void setUserZone(UserZone userZone) {
        this.userzone = userZone;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<BmobFile> getPics() {
        return pics;
    }

    public void setPics(List<BmobFile> pics) {
        this.pics = pics;
    }

    public BmobRelation getLikes() {
        return likes;
    }

    public void setLikes(BmobRelation likes) {
        this.likes = likes;
    }

    public Integer getHate() {
        return hate;
    }

    public void setHate(Integer hate) {
        this.hate = hate;
    }

    public Integer getComment() {
        return comment;
    }

    public void setComment(Integer comment) {
        this.comment = comment;
    }

    public Integer getShare() {
        return share;
    }

    public void setShare(Integer share) {
        this.share = share;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public boolean isLove() {
        return isLove;
    }

    public void setLove(boolean love) {
        isLove = love;
    }

    public boolean isAudit() {
        return isAudit;
    }

    public void setAudit(boolean audit) {
        isAudit = audit;
    }
}
