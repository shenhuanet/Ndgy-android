package com.shenhua.nandagy.model;

import android.os.Handler;

import com.shenhua.commonlibs.callback.HttpCallback;
import com.shenhua.commonlibs.mvp.ApiCallback;
import com.shenhua.commonlibs.mvp.BasePresenter;
import com.shenhua.commonlibs.mvp.HttpManager;
import com.shenhua.nandagy.App;
import com.shenhua.nandagy.bean.HomeData;
import com.shenhua.nandagy.service.Constants;
import com.shenhua.nandagy.service.ExceptionMessage;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 首页数据实现者
 * Created by shenhua on 8/29/2016.
 */
public class HomeModelImpl implements HomeModel<List<HomeData>> {

    private static final String TAG = "HomeModelImpl";
    private HttpManager httpManager = HttpManager.getInstance();
    private Handler handler = new Handler();

    @Override
    public void toGetHomeData(BasePresenter basePresenter, final String url, final HttpCallback<List<HomeData>> callback, final int type) {
        basePresenter.addSubscription(httpManager.createHtmlGetObservable(App.getContext(), url, "gb2312", false), new ApiCallback<String>() {
            @Override
            public void onPreExecute() {
                callback.onPreRequest();
            }

            @Override
            public void onSuccess(String html) {
                new Thread(() -> {
                    List<HomeData> datas = parseData(html, type);
                    handler.post(() -> {
                        if (datas == null) {
                            callback.onError(ExceptionMessage.MSG_DATA_PARSE_ERROR);
                        } else
                            callback.onSuccess(datas);
                    });
                }).start();
            }

            @Override
            public void onFailure(String msg) {
                if (msg.equals(ExceptionMessage.MSG_ERROR)) {
                    try {
                        String html = httpManager.getCache(App.getContext(), url, "gb2312");
                        if (html == null) {
                            callback.onError(ExceptionMessage.MSG_DATA_NULL);
                            return;
                        }
                        new Thread(() -> {
                            List<HomeData> datas = parseData(html, type);
                            handler.post(() -> {
                                if (datas == null) {
                                    callback.onError(ExceptionMessage.MSG_DATA_PARSE_ERROR);
                                } else {
                                    callback.onSuccess(datas);
                                }
                            });

                        }).start();
                    } catch (Exception e) {
                        e.printStackTrace();
                        callback.onError(ExceptionMessage.MSG_DATA_NULL);
                    }
                } else {
                    callback.onError(msg);
                }
            }

            @Override
            public void onFinish() {
                callback.onPostRequest();
            }
        });
    }

    /**
     * 解析数据
     */
    private List<HomeData> parseData(String html, int type) {
        List<HomeData> datas = new ArrayList<>();
        try {
            Document document = Jsoup.parse(html);
            Elements tables = document.select(type == 0 ? "div[onclick]" : "table[onclick]");
            String href;
            for (Element element : tables) {
                HomeData data = new HomeData();
                href = element.getElementsByTag(type == 0 ? "div" : "table").attr("onclick");
                Matcher matcher = Pattern.compile(type == 0 ? "\\('(.*?)'," : "'(.*?)'").matcher(href);
                if (matcher.find())
                    href = matcher.toMatchResult().group(1);
                data.setImgUrl(element.getElementsByTag("img").attr("src").trim());
                if (type == 0) {
                    data.setHref(Constants.HOME_URL_GZDT + href);
                    data.setTime(element.getElementsByAttributeValue("width", "236").text().trim());
                    data.setTitle(element.getElementsByAttributeValue("height", "30").text().trim() + "...");
                    data.setDetail(element.getElementsByAttributeValue("height", "80").text().trim());
                } else {
                    data.setHref(href);
                    data.setTime(element.getElementsByAttributeValue("height", "10").text().trim());
                    data.setTitle(element.getElementsByAttributeValue("height", "20").text().trim());
                    data.setDetail(element.getElementsByAttributeValue("height", "60").text().trim());
                }
                datas.add(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return datas;
    }
}
