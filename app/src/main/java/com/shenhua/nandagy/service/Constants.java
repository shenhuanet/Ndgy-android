package com.shenhua.nandagy.service;

/**
 * Created by shenhua on 2/13/2017.
 * Email shenhuanet@126.com
 */
public class Constants {

    public static final String DATABASE_NAME = "list_db";
    public static final String HOME_URL_GZDT = "http://www.ndgy.cn";
    public static final String HOME_URL_GGGS = HOME_URL_GZDT + "/Item/list.asp?id=1268";
    public static final String XUEGONG_URL = "http://xgc.ndgy.cn";
    public static final String JIAOWU_URL = "http://www.ncupyh.cn";

    public static class HtmlString {
        public static final String HTML_HEAD = "<html>\n" +
                "<head>\n" +
                "   <meta charset=\"utf-8\">\n" +
                "   <title>详情</title>\n" +
                "   <meta name=\"viewport\" content=\"width=device-width, minimum-scale=1.0, maxmum-scale=1.0,user-scale=no\">\n" +
                "   <meta name=\"format-detection\" content=\"telephone=no, address=no, email=no\">\n" +
                "   <meta name=\"apple-mobile-web-app-capable\" content=\"yes\">\n" +
                "   <meta name=\"apple-mobile-web-app-status-bar-style\" content=\"black\">\n" +
                "   <style type=\"text/css\">.content-text{text-indent:2em;}img{width:100%;}</style>\n" +
                "</head>\n<body>\n";
        public static final String HTML_END = "\n</body>\n" + "</html>";

        public static String formatImg(String html) {
            return html.replace("<img", "</br><img").replace("<p><img", "</br><img");
        }
    }

    public static class Code {
        public static final int REQUEST_CODE_NAV_TO_USER_ZONE = 1;
        public static final int REQUEST_CODE_NAV_TO_USER_ACCOUNT = 2;
        public static final int REQUEST_CODE_NAV_TO_LOGIN = 3;
        public static final int REQUEST_CODE_NAV_TO_PUBLISH_DYNAMIC = 4;
        public static final int REQUEST_CODE_NAV_TO_MESSAGE = 5;
        public static final int REQUEST_CODE_NAV_TO_SETTING = 6;
        public static final int REQUEST_CODE_NAV_TO_ABOUT = 7;
        public static final int REQUEST_CODE_NAV_TO_BINDING = 8;

        public static final int RECULT_CODE_LOGIN_SUCCESS = 100;
        public static final int RECULT_CODE_BINDING_SUCCESS = 101;
    }

}
