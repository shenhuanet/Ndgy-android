package com.shenhua.nandagy.model;

import android.content.Context;
import android.os.AsyncTask;

import com.shenhua.commonlibs.callback.HttpCallback;
import com.shenhua.commonlibs.utils.NetworkUtils;
import com.shenhua.nandagy.bean.StudyListData;
import com.shenhua.nandagy.database.StudyDBDao;
import com.shenhua.nandagy.service.ExceptionMessage;
import com.shenhua.nandagy.service.HttpService;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.shenhua.nandagy.service.HttpService.STUDY_HOST;

/**
 * Created by Shenhua on 2/9/2017.
 * e-mail shenhuanet@126.com
 */
public class StudyModelImpl implements StudyModel {

    @Override
    public void toGetList(Context context, int type, HttpCallback<List<StudyListData>> callback) {
        StudyDBDao studyDBDao = new StudyDBDao(context, type);
        new AsyncTask<Void, Void, List<StudyListData>>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                callback.onPreRequest();
            }

            @Override
            protected List<StudyListData> doInBackground(Void... params) {
                if (NetworkUtils.isConnectedNet(context)) {// 有网
                    try {
                        Document document = Jsoup.connect(STUDY_HOST + buildUrl(type)).timeout(30000)
                                .header("Connection", "keep-alive").get();
                        Elements lists = document.getElementsByClass("list").get(0).getElementsByTag("ul")
                                .get(0).getElementsByTag("li");
                        List<StudyListData> listDatasList = new ArrayList<>();
                        for (int i = 0; i < lists.size(); i++) {
                            Element e = lists.get(i);
                            StudyListData datas = new StudyListData(e.getElementsByTag("a").text().trim(),
                                    STUDY_HOST + e.getElementsByTag("a").attr("href"),
                                    e.getElementsByTag("p").text());
                            listDatasList.add(datas);
                        }
                        return listDatasList;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                } else {
                    return studyDBDao.listAll();
                }
            }

            @Override
            protected void onPostExecute(List<StudyListData> datas) {
                super.onPostExecute(datas);
                if (datas == null || datas.size() == 0) {
                    callback.onError(ExceptionMessage.MSG_DATA_NULL);
                    callback.onPostRequest();
                    return;
                }
                studyDBDao.deleteTable();
                studyDBDao.addList(datas);
                callback.onSuccess(datas);
                callback.onPostRequest();
            }
        }.execute();
    }

    @Override
    public void toGetDetail(Context context, int type, int position, String url, HttpCallback<StudyListData> callback) {
        StudyDBDao studyDBDao = new StudyDBDao(context, type);
        new AsyncTask<Integer, Void, StudyListData>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                callback.onPreRequest();
            }

            @Override
            protected StudyListData doInBackground(Integer... position) {
                if (NetworkUtils.isConnectedNet(context)) {
                    try {
                        Document document = Jsoup.connect(url).timeout(30000).get();
                        String time = document.getElementsByClass("time").get(0).text();
                        String content = document.getElementsByClass("content").get(0).getElementsByTag("p").toString();
                        studyDBDao.update(new StudyListData(time, content), position[0]);
                        return new StudyListData(time, content);
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                } else {
                    return studyDBDao.query(position[0]);
                }
            }

            @Override
            protected void onPostExecute(StudyListData data) {
                super.onPostExecute(data);
                if (data == null) {
                    callback.onError(ExceptionMessage.MSG_DATA_NULL);
                    callback.onPostRequest();
                    return;
                }
                callback.onSuccess(data);
                callback.onPostRequest();
            }
        }.execute(position);
    }

    private String buildUrl(int type) {
        String url = "";
        switch (type) {
            case 0:
                url = HttpService.STUDY_YINGYU;
                break;
            case 1:
                url = HttpService.STUDY_JISUANJI;
                break;
            case 2:
                url = HttpService.STUDY_DIANZI;
                break;
            case 3:
                url = HttpService.STUDY_JIXIE;
                break;
            case 4:
                url = HttpService.STUDY_JINGGUAN;
                break;
            case 5:
                url = HttpService.STUDY_JIANZHU;
                break;
            case 6:
                url = HttpService.STUDY_CAIKUAI;
                break;
        }
        return url;
    }
}
