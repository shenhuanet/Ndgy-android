package com.shenhua.nandagy.bean.scorebean;

/**
 * 成绩结果
 * Created by Shenhua on 9/25/2016.
 */
public class ScoreQueryResult<T> {

    private int code;
    private String errInfo;
    private T data;

    public ScoreQueryResult() {
    }

    public ScoreQueryResult(int code, String errInfo, T data) {
        this.code = code;
        this.errInfo = errInfo;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErrInfo() {
        return errInfo;
    }

    public void setErrInfo(String errInfo) {
        this.errInfo = errInfo;
    }
}
