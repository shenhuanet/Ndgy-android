package com.shenhua.nandagy.bean.bmobbean;

import cn.bmob.v3.BmobObject;

/**
 * Created by shenhua on 5/18/2017.
 * Email shenhuanet@126.com
 */
public class SchoolCircleComment extends BmobObject {

    private static final long serialVersionUID = -7696946428129954147L;
    private String content;
    private UserZone commenter;
    private SchoolCircle commentCircle;

    public SchoolCircleComment() {

    }

    public SchoolCircleComment(String content, UserZone commenter, SchoolCircle commentCircle) {
        this.content = content;
        this.commenter = commenter;
        this.commentCircle = commentCircle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserZone getCommenter() {
        return commenter;
    }

    public void setCommenter(UserZone commenter) {
        this.commenter = commenter;
    }

    public SchoolCircle getCommentCircle() {
        return commentCircle;
    }

    public void setCommentCircle(SchoolCircle commentCircle) {
        this.commentCircle = commentCircle;
    }
}
