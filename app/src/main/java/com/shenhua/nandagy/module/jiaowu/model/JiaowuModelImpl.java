package com.shenhua.nandagy.module.jiaowu.model;

import com.shenhua.commonlibs.callback.HttpCallback;
import com.shenhua.commonlibs.handler.BaseThreadHandler;
import com.shenhua.commonlibs.handler.CommonRunnable;
import com.shenhua.commonlibs.mvp.ApiCallback;
import com.shenhua.commonlibs.mvp.BasePresenter;
import com.shenhua.commonlibs.mvp.HttpManager;
import com.shenhua.nandagy.App;
import com.shenhua.nandagy.R;
import com.shenhua.nandagy.bean.module.JiaowuData;
import com.shenhua.nandagy.service.Constants;
import com.shenhua.nandagy.service.ExceptionMessage;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shenhua on 3/24/2017.
 * e-mail shenhuanet@126.com
 */
public class JiaowuModelImpl implements JiaowuModel<JiaowuData> {

    private HttpManager httpManager = HttpManager.getInstance();

    @Override
    public void getJiaowuDatas(BasePresenter basePresenter, String url, HttpCallback<JiaowuData> callback) {
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

    private void getData(final String html, final HttpCallback<JiaowuData> callback) {
        BaseThreadHandler.getInstance().sendRunnable(new CommonRunnable<JiaowuData>() {
            @Override
            public JiaowuData doChildThread() {
                return parseHtml(html);
            }

            @Override
            public void doUiThread(JiaowuData jiaowuData) {
                if (jiaowuData == null) {
                    callback.onError(ExceptionMessage.MSG_DATA_PARSE_ERROR);
                } else {
                    callback.onSuccess(jiaowuData);
                }
            }
        });
    }

    private JiaowuData parseHtml(String html) {
        JiaowuData data = new JiaowuData();
        List<JiaowuData.JiaowuList> lists = new ArrayList<>();
        try {
            Document document = Jsoup.parse(html);
            // week
            data.setWeek(document.select("table").get(5).select("tr").get(1).text());
            // list
            getdata(document, 19, data, lists);
            getdata(document, 20, data, lists);
            getdata(document, 22, data, lists);
            getdata(document, 23, data, lists);
            // 装载数据
            data.setList(lists);
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void getdata(Document document, int type, JiaowuData data, List<JiaowuData.JiaowuList> lists) {
        Elements elements = document.select("table").get(type).select("tr");
        for (int i = 0; i < elements.size(); i++) {
            JiaowuData.JiaowuList list = data.new JiaowuList();
            list.setTitle(elements.get(i).select("a[href]").text());
            list.setHref(Constants.JIAOWU_URL + "/" + elements.get(i).getElementsByTag("a").attr("href"));
            list.setTime(elements.get(i).getElementsByAttributeValue("width", "18%").text());
            if (type == 19 || type == 20) list.setDrawable(R.drawable.img_jiaowu_xw);
            else list.setDrawable(R.drawable.img_jiaowu_tz);
            lists.add(list);
        }
    }
}
