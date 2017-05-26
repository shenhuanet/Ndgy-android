package com.shenhua.nandagy.module.xuegong.model;

import com.shenhua.commonlibs.callback.HttpCallback;
import com.shenhua.commonlibs.handler.BaseThreadHandler;
import com.shenhua.commonlibs.handler.CommonRunnable;
import com.shenhua.commonlibs.mvp.ApiCallback;
import com.shenhua.commonlibs.mvp.BasePresenter;
import com.shenhua.commonlibs.mvp.HttpManager;
import com.shenhua.libs.bannerview.BannerData;
import com.shenhua.nandagy.App;
import com.shenhua.nandagy.R;
import com.shenhua.nandagy.bean.module.XueGongData;
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
 * 学工处数据实现者
 * Created by shenhua on 8/31/2016.
 */
public class XueGongModelImpl implements XueGongModel<XueGongData> {

    private HttpManager httpManager = HttpManager.getInstance();

    @Override
    public void toGetXueGongData(BasePresenter basePresenter, String url, final HttpCallback<XueGongData> callback) {
        basePresenter.addSubscription(httpManager.createHtmlGetObservable(App.getContext(), url, "gb2312", false), new ApiCallback<String>() {
            @Override
            public void onPreExecute() {
                callback.onPreRequest();
            }

            @Override
            public void onSuccess(String html) {
                getData(html, callback);
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
                        getData(html, callback);
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

    private void getData(final String html, final HttpCallback<XueGongData> callback) {
        BaseThreadHandler.getInstance().sendRunnable(new CommonRunnable<XueGongData>() {

            @Override
            public XueGongData doChildThread() {
                return parseHtml(html);
            }

            @Override
            public void doUiThread(XueGongData xueGongData) {
                if (xueGongData == null) {
                    callback.onError(ExceptionMessage.MSG_DATA_PARSE_ERROR);
                } else {
                    callback.onSuccess(xueGongData);
                }
            }
        });
    }

    private XueGongData parseHtml(String html) {
        XueGongData xueGongData = new XueGongData();
        BannerData bannerData = new BannerData();
        List<XueGongData.XuegongListData> listDatas = new ArrayList<>();
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
                images[i] = Constants.XUEGONG_URL + images[i];
                hrefs[i] = Constants.XUEGONG_URL + hrefs[i];
            }
            bannerData.setaImage(images);
            bannerData.setaTitle(titles);
            bannerData.setaHref(hrefs);
            // 4
            Elements elements = document.select("table").eq(4);
            Elements xuegongyaowen = elements.select("td").get(3).select("tbody");
            for (Element element : xuegongyaowen) {
                XueGongData.XuegongListData data = xueGongData.new XuegongListData();
                data.setTitle(element.select("a[href]").text());
                data.setNewsType(R.drawable.ic_xuegong_yw);
                data.setHref(Constants.XUEGONG_URL + element.getElementsByTag("a").attr("href"));
                data.setTime(element.getElementsByAttributeValue("align", "right").text());
                listDatas.add(data);
            }
            // 14  15  18  19
            getListSingle(document.select("table").eq(14), listDatas, 14);
            getListSingle(document.select("table").eq(15), listDatas, 15);
            getListSingle(document.select("table").eq(18), listDatas, 18);
            getListSingle(document.select("table").eq(19), listDatas, 19);
            // 装载ArrayList
            xueGongData.setBannerData(bannerData);
            xueGongData.setXuegongListDatas(listDatas);
            return xueGongData;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void getListSingle(Elements elements, List<XueGongData.XuegongListData> datas, int type) {
        elements.select("tr").first().remove();
        elements = elements.select("tr");
        XueGongData xueGongData = new XueGongData();
        for (Element element : elements) {
            XueGongData.XuegongListData data = xueGongData.new XuegongListData();
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
