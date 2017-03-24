package com.shenhua.nandagy.model;

import android.os.Handler;

import com.shenhua.commonlibs.callback.HttpCallback;
import com.shenhua.commonlibs.mvp.ApiCallback;
import com.shenhua.commonlibs.mvp.BasePresenter;
import com.shenhua.commonlibs.mvp.HttpManager;
import com.shenhua.nandagy.App;
import com.shenhua.nandagy.R;
import com.shenhua.nandagy.bean.XueGongData;
import com.shenhua.nandagy.service.Constants;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 学工处数据实现者
 * Created by shenhua on 8/31/2016.
 */
public class XueGongModelImpl implements XueGongModel<ArrayList[]> {

    private HttpManager httpManager = HttpManager.getInstance();
    private Handler handler = new Handler();

    @Override
    public void toGetXueGongData(BasePresenter basePresenter, String url, final HttpCallback<ArrayList[]> callback) {
        basePresenter.addSubscription(httpManager.createHtmlGetObservable(App.getContext(), url, "gb2312", false), new ApiCallback<String>() {
            @Override
            public void onPreExecute() {
                callback.onPreRequest();
            }

            @Override
            public void onSuccess(String model) {
                new Thread(() -> {
                    ArrayList[] datas = parseHtml(model);
                    handler.post(() -> {
                        if (datas == null) {
                            callback.onError("数据解析错误");
                        } else {
                            callback.onSuccess(datas);
                        }
                    });
                }).start();
            }

            @Override
            public void onFailure(String msg) {
                if (msg.equals("error")) {
                    new Thread(() -> {
                        try {
                            String html = httpManager.getCache(App.getContext(), url, "gb2312");
                            ArrayList[] datas = parseHtml(html);
                            handler.post(() -> {
                                if (datas == null) {
                                    callback.onError("数据解析错误");
                                } else {
                                    callback.onSuccess(datas);
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                            callback.onError("数据为空");
                        }
                    }).start();
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

    private ArrayList[] parseHtml(String html) {
        ArrayList<XueGongData> datas = new ArrayList<>();
        ArrayList<XueGongData.BannerData> banners = new ArrayList<>();
        Document document = Jsoup.parse(html);
        try {
            Elements scripts = document.getElementsByTag("script").eq(4);
            String[] images = getBannerSingle(scripts.html(), "pics='(.*?)'");
            String[] titles = getBannerSingle(scripts.html(), "texts='(.*?)'");
            String[] hrefs = getBannerSingle(scripts.html(), "links=escape\\('(.*?)'");
            if (images == null || titles == null || hrefs == null) {
                return null;
            }
            // 装载banner数据
            for (int i = 0; i < images.length; i++) {
                XueGongData data = new XueGongData();
                XueGongData.BannerData bannerData = data.new BannerData();
                bannerData.setbImage(Constants.XUEGONG_URL + images[i]);
                bannerData.setbTitle(titles[i]);
                bannerData.setbHref(Constants.XUEGONG_URL + hrefs[i]);
                banners.add(bannerData);
            }
            // 4
            Elements elements = document.select("table").eq(4);
            Elements xuegongyaowen = elements.select("td").get(3).select("tbody");
            for (Element element : xuegongyaowen) {
                XueGongData data = new XueGongData();
                data.setTitle(element.select("a[href]").text());
                data.setNewsType(R.drawable.ic_xuegong_yw);
                data.setHref(Constants.XUEGONG_URL + element.getElementsByTag("a").attr("href"));
                data.setTime(element.getElementsByAttributeValue("align", "right").text());
                datas.add(data);
            }
            // 14  15  18  19
            getListSingle(document.select("table").eq(14), datas, 14);
            getListSingle(document.select("table").eq(15), datas, 15);
            getListSingle(document.select("table").eq(18), datas, 18);
            getListSingle(document.select("table").eq(19), datas, 19);
            // 装载ArrayList
            ArrayList[] lists = new ArrayList[2];
            lists[0] = banners;
            lists[1] = datas;
            return lists;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void getListSingle(Elements elements, ArrayList<XueGongData> datas, int type) {
        elements.select("tr").first().remove();
        elements = elements.select("tr");
        for (Element element : elements) {
            XueGongData data = new XueGongData();
            data.setTitle(element.select("a[href]").text());
            data.setHref(Constants.XUEGONG_URL + element.getElementsByTag("a").attr("href"));
            data.setTime(element.getElementsByAttributeValue("align", "right").text());
            if (type == 14) data.setNewsType(R.drawable.ic_xuegong_tz);
            if (type == 15) data.setNewsType(R.drawable.ic_xuegong_rw);
            if (type == 18) data.setNewsType(R.drawable.ic_xuegong_zd);
            if (type == 19) data.setNewsType(R.drawable.ic_xuegong_xl);
            datas.add(data);
        }
    }

    private String[] getBannerSingle(String script, String regex) {
        Matcher picMatcher = Pattern.compile(regex).matcher(script);
        if (picMatcher.find()) {
            return picMatcher.toMatchResult().group(1).split("\\|");
        } else
            return null;
    }
}
