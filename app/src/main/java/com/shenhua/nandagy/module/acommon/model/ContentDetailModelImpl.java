package com.shenhua.nandagy.module.acommon.model;

import com.shenhua.commonlibs.callback.HttpCallback;
import com.shenhua.commonlibs.mvp.ApiCallback;
import com.shenhua.commonlibs.mvp.BasePresenter;
import com.shenhua.commonlibs.mvp.HttpManager;
import com.shenhua.nandagy.App;
import com.shenhua.nandagy.bean.common.ContentDetailData;
import com.shenhua.nandagy.service.Constants;
import com.shenhua.nandagy.service.ExceptionMessage;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 正文详情实现者
 * Created by Shenhua on 3/23/2017.
 * e-mail shenhuanet@126.com
 */
public class ContentDetailModelImpl implements ContentDetailModel<ContentDetailData> {

    private HttpManager httpManager = HttpManager.getInstance();

    /**
     * 获取主页详情数据
     */
    @Override
    public void getHomeDetail(BasePresenter basePresenter, String url, HttpCallback<ContentDetailData> callback) {
        basePresenter.addSubscription(httpManager.createHtmlGetObservable(App.getContext(), url, "gb2312", false), new ApiCallback<String>() {
            @Override
            public void onPreExecute() {
                callback.onPreRequest();
            }

            @Override
            public void onSuccess(String model) {
                Document document = Jsoup.parse(model);
                try {
                    Element tables = document.select("table").get(12);
                    String title = tables.getElementsByAttributeValue("height", "50").text();
                    Element content = tables.getElementById("MyContent");
                    // 去除href标签，使本身图片失去点击
                    Elements hrefs = content.select("a[href]");
                    for (Element el : hrefs) {
                        el.removeAttr("href");
                    }
                    // 增加图片的host前缀
                    Elements imgs = content.select("img[src]");
                    for (Element element : imgs) {
                        String imgUrl = element.attr("src");
                        if (imgUrl.trim().startsWith("/")) {
                            imgUrl = Constants.HOME_URL_GZDT + imgUrl;
                            element.attr("src", imgUrl);
                        }
                    }
                    // 设置数据
                    ContentDetailData detailData = new ContentDetailData();
                    detailData.setTitle(title);
                    detailData.setContent(Constants.HtmlString.HTML_HEAD + content + Constants.HtmlString.HTML_END);
                    callback.onSuccess(detailData);
                } catch (Exception e) {
                    callback.onError(ExceptionMessage.MSG_DATA_PARSE_ERROR);
                }
            }

            @Override
            public void onFailure(String msg) {
                callback.onError(msg);
            }

            @Override
            public void onFinish() {
                callback.onPostRequest();
            }
        });
    }

    /**
     * 获取学工详情数据
     */
    @Override
    public void getXuegongDetail(BasePresenter basePresenter, String url, HttpCallback<ContentDetailData> callback) {
        basePresenter.addSubscription(httpManager.createHtmlGetObservable(App.getContext(), url, "gb2312", false), new ApiCallback<String>() {
            @Override
            public void onPreExecute() {
                callback.onPreRequest();
            }

            @Override
            public void onSuccess(String model) {
                Document document = Jsoup.parse(model);
                try {
                    Element tables = document.select("table").get(3);
                    String title = tables.getElementsByAttributeValue("height", "30").text();
                    Element content = tables.getElementById("MyContent");
                    // 去除href标签，使本身图片失去点击
                    Elements hrefs = content.select("a[href]");
                    for (Element el : hrefs) {
                        el.removeAttr("href");
                    }
                    // 增加图片的host前缀
                    Elements imgs = content.select("img[src]");
                    for (Element element : imgs) {
                        String imgUrl = element.attr("src");
                        if (imgUrl.trim().startsWith("/")) {
                            imgUrl = Constants.XUEGONG_URL + imgUrl;
                            element.attr("src", imgUrl);
                        }
                    }
                    // 设置数据
                    ContentDetailData detailData = new ContentDetailData();
                    detailData.setTitle(title);
                    detailData.setContent(Constants.HtmlString.HTML_HEAD + content + Constants.HtmlString.HTML_END);
                    callback.onSuccess(detailData);
                } catch (Exception e) {
                    callback.onError(ExceptionMessage.MSG_DATA_PARSE_ERROR);
                }
            }

            @Override
            public void onFailure(String msg) {
                callback.onError(msg);
            }

            @Override
            public void onFinish() {
                callback.onPostRequest();
            }
        });
    }

    /**
     * 获取教务详情数据
     */
    @Override
    public void getJiaowuDetail(BasePresenter basePresenter, String url, HttpCallback<ContentDetailData> callback) {
        basePresenter.addSubscription(httpManager.createHtmlGetObservable(App.getContext(), url, "gb2312", false), new ApiCallback<String>() {
            @Override
            public void onPreExecute() {
                callback.onPreRequest();
            }

            @Override
            public void onSuccess(String model) {
                try {
                    Document document = Jsoup.parse(model);
                    Element element = document.select("table").get(18);
                    Elements hrefs = element.select("a[href]");
                    for (Element el : hrefs) {
                        el.removeAttr("href");
                    }
                    // 增加图片的host前缀
                    Elements imgs = element.select("img[src]");
                    for (Element e : imgs) {
                        String imgUrl = e.attr("src");
                        if (imgUrl.trim().startsWith("/")) {
                            imgUrl = Constants.JIAOWU_URL + imgUrl;
                            e.attr("src", imgUrl);
                        }
                    }
                    ContentDetailData detailData = new ContentDetailData();
                    detailData.setTitle(element.getElementsByAttributeValue("height", "60").text());
                    detailData.setContent(Constants.HtmlString.HTML_HEAD + element.getElementsByClass("MsoNormal").html() + Constants.HtmlString.HTML_END);
                    callback.onSuccess(detailData);
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onError(ExceptionMessage.MSG_DATA_PARSE_ERROR);
                }
            }

            @Override
            public void onFailure(String msg) {
                callback.onError(msg);
            }

            @Override
            public void onFinish() {
                callback.onPostRequest();
            }
        });
    }
}
