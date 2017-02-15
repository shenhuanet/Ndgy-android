package com.shenhua.nandagy.manager;

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;

import com.shenhua.commonlibs.utils.NetworkUtils;
import com.shenhua.nandagy.App;
import com.shenhua.nandagy.service.OkHttpService;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * Http管理类
 * Created by shenhua on 8/29/2016.
 */
public class HttpManager {

    public static final long CACHE_STALE_SEC = 60 * 60 * 24 * 2;// 缓存有效期为2天
    // 查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
    public static final String CACHE_CONTROL_CACHE = "only-if-cached,max-stale=" + CACHE_STALE_SEC;
    public static final String CACHE_CONTROL_NETWORK = "max-age=0";

    public static final String CONNECTION = "keep-alive";
    public static final String ACCEPT = "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8";
    public static final String USERAGENT = "Mozilla/5.0 (Windows NT 10.0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.80 Safari/537.36 QQBrowser/9.3.6581.400";
    public static final String ACCEPTENCODING = "gzip, deflate, sdch";
    public static final String ACCEPTLANGUAGE = "zh-CN,zh;q=0.8";

    private static HttpManager instance = null;
    private static ExecutorService executorService;
    private static volatile OkHttpClient mOkHttpClient;
    private OkHttpService okHttpService;

    public static HttpManager getInstance() {
        if (instance == null) {
            instance = new HttpManager(0);
        }
        if (executorService == null)
            executorService = Executors.newSingleThreadExecutor();
        return instance;
    }

    public static HttpManager getInstance(int useOkHttpClient) {
        if (instance == null) {
            instance = new HttpManager(useOkHttpClient);
        }
        return instance;
    }

    public HttpManager(int useOkHttpClient) {
        if (useOkHttpClient == 1) {
            initOkHttpClient();
//            Retrofit retrofit = new Retrofit.Builder()
//                    .baseUrl("http://www.baidu.com").client(mOkHttpClient)
//                    .build();
//            okHttpService = retrofit.create(OkHttpService.class);
        }
    }

    private void initOkHttpClient() {
        if (mOkHttpClient == null) {
            synchronized (HttpManager.class) {
                Cache cache = new Cache(new File(App.getContext().getCacheDir(), "HttpCache"), 1024 * 1024 * 100);
                mOkHttpClient = new OkHttpClient.Builder()
                        .addNetworkInterceptor(new RewriteCacheControlInterceptor())
                        .addInterceptor(new RewriteCacheControlInterceptor())
                        .addInterceptor(new ResultInterceptor())
                        .connectTimeout(30, TimeUnit.SECONDS)
                        .cache(cache)
                        .build();
            }
        }
    }

    @NonNull
    private String getCacheControl() {
        return NetworkUtils.isConnectedNet(App.getContext()) ? CACHE_CONTROL_NETWORK : CACHE_CONTROL_CACHE;
    }

    private class RewriteCacheControlInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetworkUtils.isConnectedNet(App.getContext()))
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
            Response response = chain.proceed(request);
            if (NetworkUtils.isConnectedNet(App.getContext()))
                return response.newBuilder().header("Cache-Control", request.cacheControl().toString()).removeHeader("Pragma").build();
            else
                return response.newBuilder().header("Cache-Control", "public,only-if-cached," + CACHE_STALE_SEC).removeHeader("Pragma").build();
        }

    }

    private class ResultInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);
            ResponseBody body = response.body();
            long length = body.contentLength();
            BufferedSource source = body.source();
            source.request(Long.MAX_VALUE);
            Buffer buffer = source.buffer();
            Charset charset = Charset.forName("UTF-8");
            MediaType type = body.contentType();
            if (type != null) {
                try {
                    charset = type.charset(charset);
                } catch (UnsupportedCharsetException e) {
                    e.printStackTrace();
                }
            }
            if (length != 0) {
                System.out.println("shenhua sout:--------------------------------------------开始打印返回数据----------------------------------------------------");
                System.out.println("shenhua sout:" + buffer.clone().readString(charset));
            }
            return response;
        }
    }

    public static class DataLoadType {

        @DataLoadTypeChecker
        public static final int DATA_TYPE_SUCCESS = 1;

        @DataLoadTypeChecker
        public static final int DATA_TYPE_ERROR = 2;

        @IntDef({DATA_TYPE_SUCCESS, DATA_TYPE_ERROR})
        @Retention(RetentionPolicy.SOURCE)
        public @interface DataLoadTypeChecker {

        }
    }

    public void sendRequest(Runnable runnable) {
        if (runnable != null)
            executorService.submit(runnable);
    }

    public Connection getConnection(String host, String param, Connection.Method method) {
        return Jsoup.connect(host + param)
                .header("Host", host.replace("http://", ""))
                .header("Connection", CONNECTION)
                .header("Accept", ACCEPT)
                .header("Upgrade-Insecure-Requests", "1")
                .header("Accept-Encoding", ACCEPTENCODING)
                .header("Accept-Language", ACCEPTLANGUAGE)
                .timeout(5000)
                .header("User-Agent", USERAGENT)
                .method(method)
                .followRedirects(false);
    }

    public Connection.Response buildResponse(Connection connection) {
        try {
            return connection.execute();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
