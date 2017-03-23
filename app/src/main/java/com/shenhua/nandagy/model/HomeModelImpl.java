package com.shenhua.nandagy.model;

import com.shenhua.commonlibs.callback.HttpCallback;
import com.shenhua.commonlibs.mvp.ApiCallback;
import com.shenhua.commonlibs.mvp.BasePresenter;
import com.shenhua.commonlibs.mvp.HttpManager;
import com.shenhua.nandagy.App;
import com.shenhua.nandagy.bean.HomeData;
import com.shenhua.nandagy.service.Constants;

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

    @Override
    public void toGetHomeData(BasePresenter basePresenter, final String url, final HttpCallback<List<HomeData>> callback, final int type) {
        basePresenter.addSubscription(HttpManager.getInstance()
                .createHtmlGetObservable(App.getContext(), url, "gb2312", false), new ApiCallback<String>() {
            @Override
            public void onPreExecute() {
                callback.onPreRequest();
            }

            @Override
            public void onSuccess(String o) {
                List<HomeData> datas = parseData(o, type);
                if (datas == null) {
                    callback.onError("数据解析失败");
                } else
                    callback.onSuccess(datas);
            }

            @Override
            public void onFailure(String s) {
                callback.onError(s);
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
            Elements tables = document.select(type == 1 ? "div[onclick]" : "table[onclick]");
            String href;
            for (Element element : tables) {
                HomeData data = new HomeData();
                href = element.getElementsByTag(type == 1 ? "div" : "table").attr("onclick");
                Matcher matcher = Pattern.compile(type == 1 ? "\\('(.*?)'," : "'(.*?)'").matcher(href);
                if (matcher.find())
                    href = matcher.toMatchResult().group(1);
                data.setImgUrl(element.getElementsByTag("img").attr("src").trim());
                if (type == 1) {
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
