package com.shenhua.nandagy.module.jiaowu.model;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.shenhua.commonlibs.mvp.HttpManager;
import com.shenhua.commonlibs.utils.QueryString;
import com.shenhua.nandagy.bean.scorebean.ExamScore;
import com.shenhua.nandagy.bean.scorebean.GradeScore;
import com.shenhua.nandagy.service.Constants;
import com.shenhua.nandagy.utils.CacheUrlUtils;
import com.tencent.bugly.crashreport.CrashReport;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Shenhua on 4/1/2017.
 * e-mail shenhuanet@126.com
 */
public class ScoreModelImpl implements ScoreModel {

    private static final String TAG = "ScoreModelImpl";
    public static final int LOGIN_USERNAME_ERROR = 1;
    public static final int LOGIN_PASSWORE_ERROR = 2;
    public static final int LOGIN_REDIRCT_HOME = 3;
    public static final int LOGIN_ERROR = 4;
    public static final int LOGIN_CATCH = 5;
    public static final int NETWORK_ABNORMAL = 6;
    public static final int NETWORK_TIMEOUT = 7;
    public static final int LOGIN_SUCCESS = 45;
    private HttpManager httpManager = HttpManager.getInstance();
    private String[] mNumName;
    private String mRandom;

    @Override
    public int login(Context context, String url, String num, String pwd) {
        Request request = new Request.Builder().url(url).build();
        Call call = httpManager.getOkHttpClientSaveCookies(context, false).newCall(request);
        try {
            Response response = call.execute();
            String result = new String(response.body().bytes(), "gb2312");
            Document document = Jsoup.parse(result);
            String __VIEWSTATE = document.getElementsByTag("input").get(0).attr("value");
            String __VIEWSTATEGENERATOR = document.getElementsByTag("input").get(1).attr("value");
            FormBody.Builder fb = new FormBody.Builder();
            fb.add("__VIEWSTATE", __VIEWSTATE);
            fb.add("__VIEWSTATEGENERATOR", __VIEWSTATEGENERATOR);
            fb.add("TextBox1", num);
            fb.add("TextBox2", pwd);
            fb.add("RadioButtonList1", "%D1%A7%C9%FA");
            fb.add("Button1", "");
            RequestBody formBody = fb.build();
            request = new Request.Builder().url(response.request().url()).post(formBody).build();
            call = httpManager.getOkHttpClientSaveCookies(context, false).newCall(request);
            try {
                response = call.execute();
                result = new String(response.body().bytes(), "gb2312");
                mRandom = response.request().url().toString();
                Log.d(TAG, "1------host:" + mRandom);
                CacheUrlUtils.getInstance().setJiaowuMainUrl(context, mRandom);
                mRandom = mRandom.substring(mRandom.indexOf("("), mRandom.indexOf(")") + 1);
                document = Jsoup.parse(result);
                String info = document.getElementsByTag("script").toString();
                if (info.contains("alert")) {
                    info = info.substring(2 + info.indexOf("('"), info.indexOf("')"));
                    switch (info) {
                        case "密码错误！！":
                            return LOGIN_PASSWORE_ERROR;
                        case "用户名不存在或未按照要求参加教学活动！！":
                            return LOGIN_USERNAME_ERROR;
                        default:
                            return LOGIN_ERROR;
                    }
                } else if (TextUtils.isEmpty(info)) {
                    return LOGIN_REDIRCT_HOME;
                } else if (info.contains("<title>ERROR")) {
                    return LOGIN_ERROR;
                } else {
                    info = document.getElementsByClass("info").get(0).getElementById("xhxm").text();
                    mNumName = info.split(" ");
                    mNumName[1] = mNumName[1].replace("同学", "");
                    return LOGIN_SUCCESS;
                }
            } catch (Exception e) {
                e.printStackTrace();
                CrashReport.postCatchedException(e);
                return LOGIN_CATCH;
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof UnknownHostException) {
                return NETWORK_ABNORMAL;
            }
            if (e instanceof SocketTimeoutException) {
                return NETWORK_TIMEOUT;
            }
            CrashReport.postCatchedException(e);
            return LOGIN_CATCH;
        }
    }

    @Override
    public ExamScore getExamScore(Context context) {
        String url = Constants.JiaoWuScore.HOST + mRandom + Constants.JiaoWuScore.URL_EXAM
                + new QueryString().add("xh", mNumName[0]).add("xm", mNumName[1]).add("gnmkdm", Constants.JiaoWuScore.CODE_URL_EXAM);
        Log.d(TAG, "2------host:" + url);
        String referer = Constants.JiaoWuScore.HOST + mRandom + Constants.JiaoWuScore.URL_MAIN
                + new QueryString().add("xh", mNumName[0]);
        Request request = new Request.Builder().url(url).header("Referer", referer).get().build();
        Call call = httpManager.getOkHttpClientSaveCookies(context, false).newCall(request);
        try {
            Response response = call.execute();
            String result = new String(response.body().bytes(), "gb2312");
            Document document = Jsoup.parse(result);
            String action = document.getElementsByTag("form").attr("action");
            String __VIEWSTATE = document.getElementsByTag("input").get(0).attr("value");
            String __VIEWSTATEGENERATOR = document.getElementsByTag("input").get(1).attr("value");
            FormBody.Builder fb = new FormBody.Builder();
            fb.add("__VIEWSTATE", __VIEWSTATE);
            fb.add("__VIEWSTATEGENERATOR", __VIEWSTATEGENERATOR);
            fb.add("ddlXN", "");
            fb.add("ddlXQ", "");
            fb.add("Button5", new QueryString().encoderString("按学年查询"));
            RequestBody formBody = fb.build();
            request = new Request.Builder().url(url).header("Referer", response.request().url().toString())
                    .post(formBody).build();
            call = httpManager.getOkHttpClientSaveCookies(context, false).newCall(request);
            response = call.execute();
            result = new String(response.body().bytes(), "gb2312");
            CacheUrlUtils.getInstance().setJiaowuExamScoreUrl(context, response.request().url().toString());
            document = Jsoup.parse(result);
            return parseExamScore(document);
        } catch (Exception e) {
            e.printStackTrace();
            CrashReport.postCatchedException(e);
            return null;
        }
    }

    private ExamScore parseExamScore(Document document) {
        ExamScore examScore = new ExamScore();
        ExamScore.Overview overview = examScore.new Overview();
        List<ExamScore.ExamScoreList> lists = new ArrayList<>();
        try {
            Elements xf = document.getElementById("Datagrid2").select("tr").get(5).select("td");
            overview.setRequestCredit(xf.get(1).text());
            overview.setGainCredit(xf.get(2).text());
            overview.setNoPassCredit(xf.get(3).text());
            overview.setNeedCredit(xf.get(4).text());
            String zrs = document.getElementById("zyzrs").text();
            overview.setTotalPeople(Pattern.compile("[^0-9]").matcher(zrs).replaceAll("").trim());
            String pjjd = document.getElementById("pjxfjd").text();
            overview.setAveragePoint(pjjd.substring(1 + pjjd.indexOf("："), pjjd.length()));
            String jdzh = document.getElementById("xfjdzh").text();
            overview.setAverageCredit(jdzh.substring(1 + jdzh.indexOf("："), jdzh.length()));
            Elements cj = document.getElementById("Datagrid1").getElementsByClass("alt");
            Elements es;
            for (int i = 0; i < cj.size(); i++) {
                es = cj.get(i).getElementsByTag("td");
                ExamScore.ExamScoreList list = examScore.new ExamScoreList(
                        es.get(0).text().replace(" ", "").trim(),
                        es.get(1).text().replace(" ", "").trim(),
                        es.get(2).text().replace(" ", "").trim(),
                        es.get(3).text().replace(" ", "").trim(),
                        es.get(6).text().replace(" ", "").trim(),
                        es.get(7).text().replace(" ", "").trim(),
                        es.get(8).text().replace(" ", "").trim(),
                        es.get(9).text().replace(" ", "").trim(),
                        es.get(10).text().replace(" ", "").trim(),
                        es.get(11).text().replace(" ", "").trim(),
                        es.get(14).text().replace(" ", "").trim(),
                        es.get(15).text().replace(" ", "").trim(),
                        es.get(12).text().replace(" ", "").trim(),
                        es.get(16).text().replace(" ", "").trim());
                lists.add(list);
            }
            examScore.setOverview(overview);
            examScore.setExamScoreLists(lists);
            return examScore;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<GradeScore> getGradeScore(Context context) {
        String url = Constants.JiaoWuScore.HOST + mRandom + Constants.JiaoWuScore.URL_GRADE
                + new QueryString().add("xh", mNumName[0]).add("xm", mNumName[1]).add("gnmkdm", Constants.JiaoWuScore.CODE_GRADE);
        String referer = Constants.JiaoWuScore.HOST + mRandom + Constants.JiaoWuScore.URL_MAIN
                + new QueryString().add("xh", mNumName[0]);
        Request request = new Request.Builder().url(url).header("Referer", referer).get().build();
        Call call = httpManager.getOkHttpClientSaveCookies(context, false).newCall(request);
        try {
            Response response = call.execute();
            CacheUrlUtils.getInstance().setJiaowuGradeScoreUrl(context, response.request().url().toString());
            String result = new String(response.body().bytes(), "gb2312");
            Document document = Jsoup.parse(result);
            return parseGradeScore(document);
        } catch (Exception e) {
            e.printStackTrace();
            CrashReport.postCatchedException(e);
            return null;
        }
    }

    private List<GradeScore> parseGradeScore(Document document) {
        List<GradeScore> lists = new ArrayList<>();
        try {
            Elements xf = document.getElementById("DataGrid1").select("tr");
            Elements es;
            for (int i = 1; i < xf.size(); i++) {
                es = xf.get(i).getElementsByTag("td");
                GradeScore list = new GradeScore(
                        es.get(2).text().replace(" ", "").trim(),
                        es.get(0).text().replace(" ", "").trim(),
                        es.get(1).text().replace(" ", "").trim(),
                        es.get(5).text().replace(" ", "").trim(),
                        es.get(3).text().replace(" ", "").trim(),
                        es.get(4).text().replace(" ", "").trim(),
                        es.get(6).text().replace(" ", "").trim(),
                        es.get(7).text().replace(" ", "").trim(),
                        es.get(8).text().replace(" ", "").trim());
                lists.add(list);
            }
            return lists;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String[] getmNumName() {
        return mNumName == null ? new String[]{"", ""} : mNumName;
    }
}
