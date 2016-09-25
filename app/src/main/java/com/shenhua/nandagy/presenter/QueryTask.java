package com.shenhua.nandagy.presenter;

import android.content.Context;
import android.os.AsyncTask;

import com.shenhua.nandagy.bean.ScoreCETParams;
import com.shenhua.nandagy.callback.OnScoreQueryListener;
import com.shenhua.nandagy.manager.HttpManager;
import com.shenhua.nandagy.service.HttpService;
import com.shenhua.nandagy.widget.LoadingAlertDialog;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * 成绩查询的异步线程
 * 第一个参数：传入doInBackground()方法的参数类型
 * 第二个参数：传入onProgressUpdate()方法的参数类型
 * 第三个参数：传入onPostExecute()方法的参数类型，也是doInBackground()方法返回的类型
 * Created by Shenhua on 9/23/2016.
 */
public class QueryTask<T> extends AsyncTask<Integer, Integer, Object> {

    private T data;
    private Context mContext;
    private OnScoreQueryListener onScoreQueryListener;

    public QueryTask(Context context, T data, OnScoreQueryListener onScoreQueryListener) {
        this.mContext = context;
        this.data = data;
        this.onScoreQueryListener = onScoreQueryListener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        LoadingAlertDialog.showLoadDialog(mContext, "查询中，请稍后...", true);
    }

    @Override
    protected Object doInBackground(Integer... params) {
        if (params[0] == 1) {
            Document doc = doQueryCET(data);

            return null;
        }

        return null;
    }

    @Override
    protected void onPostExecute(Object s) {
        super.onPostExecute(s);
        LoadingAlertDialog.dissmissLoadDialog();
        if (s != null) {
            onScoreQueryListener.onQuerySuccess(s);
        } else {
            onScoreQueryListener.onQueryFailed(-1);
        }
    }

    private Document doQueryCET(T data) {
        Document doc;
        try {
            doc = Jsoup.connect(HttpService.SCORE_QUERY_URL_CET)
                    .data("zkzh", ((ScoreCETParams) data).getZkzh())
                    .data("xm", ((ScoreCETParams) data).getName())
                    .cookie("auth", "token")
                    .userAgent(HttpManager.USERAGENT)
                    .timeout(5000)
                    .header("Referer", "http://www.chsi.com.cn/cet/")
                    .post();
            return doc;
        } catch (IOException e) {
            return null;
        }
    }
}