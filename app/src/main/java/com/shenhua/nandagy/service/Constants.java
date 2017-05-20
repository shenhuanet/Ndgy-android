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
        public static final int REQUEST_CODE_NAV_CIRCLE_DETAIL = 9;

        public static final int RECULT_CODE_LOGIN_SUCCESS = 100;
        public static final int RECULT_CODE_BINDING_SUCCESS = 101;

        public static final int URL_REQUEST_TYPE_HOME = 1;
        public static final int URL_REQUEST_TYPE_XUEGONG = 2;
        public static final int URL_REQUEST_TYPE_JIAOWU = 3;
    }

    public static class JiaoWuScore {

        public static final String HOST = "http://www.ncupyh.cn/zfjwgl/";
        public static final String URL_LOGIN = "default2.aspx";
        public static final String URL_MAIN = "/xs_main.aspx";

        public static final String URL_REGISTER_ONLINE = "/bmxmb.aspx";// 网上报名
        public static final String URL_PESON_INFO = "/xsgrxx.aspx";// 个人信息
        public static final String URL_PASSWORD = "/mmxg.aspx";// 密码修改
        public static final String URL_SCHEDULE = "/xskbcx.aspx";// 个人课表
        public static final String URL_VIEW_EXAM = "/xskscx.aspx";// 考试查询
        public static final String URL_EXAM = "/xscj_gc.aspx";// 成绩查询
        public static final String URL_GRADE = "/xsdjkscx.aspx";// 等级成绩查询

        public static final String CODE_REGISTER_ONLINE = "N121303";
        public static final String CODE_PESON_INFO = "N121501";
        public static final String CODE_PASSWORD = "N121502";
        public static final String CODE_SCHEDULE = "N121603";
        public static final String CODE_VIEW_EXAM = "N121604";
        public static final String CODE_URL_EXAM = "N121605";
        public static final String CODE_GRADE = "N121606";

    }

    public static class FileC {
        public static final String PICTURE_SAVE_DIR = "ndgy";
    }

}
