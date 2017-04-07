package com.shenhua.nandagy;

import com.shenhua.nandagy.service.HttpService;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);

//        String num = "";
//        String id = "";
//        String name = "";
//
//        System.out.println(DESUtils.getInstance().encrypt(num) + "  " + DESUtils.getInstance().encrypt(id) + "  " + DESUtils.getInstance().encrypt(name));

    }

    private void fe() throws IOException {
        //第一次请求
        Connection con = Jsoup.connect("http://chaxun.neea.edu.cn/examcenter/query.cn?op=doQueryCond&sid=280&pram=results");//获取连接
        con.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.116 Safari/537.36");//配置模拟浏览器
        Connection.Response rs = con.execute();//获取响应
        Document d1 = Jsoup.parse(rs.body());//转换为Dom树
        Element form = d1.getElementsByTag("form").get(0);
        String action = "http://chaxun.neea.edu.cn" + form.attr("action");
//        Element f = form.getElementById("examselect").getElementsByTag("option").get(0)
//                .attr("selected", "selected");
        Element container = form.getElementById("container");
        List<Element> inputs = container.getElementsByTag("input");
        Map<String, String> datas = new HashMap<>();
        String[] inputValues = {"1234567890", "113467199409187534", "张三", "dk2r"};
        for (int i = 0; i < 4; i++) {
            Element e = inputs.get(i);
            e.attr("value", inputValues[i]);
            datas.put(e.attr("name"), e.attr("value"));
            System.out.println("shenhua sout:--->" + e);
        }
//        List<Element> et = d1.select("queryForm");//获取form表单，可以通过查看页面源码代码得知
//        if (form.isBlock()) {
//            System.out.println("shenhua sout:" + "获取form表单失败");
//            return;
//        }
//         获取，cooking和表单属性，下面map存放post时的数据
//        Map<String, String> datas = new HashMap<>();
//        for (Element e : et.get(0).getAllElements()) {
//            if (e.attr("name").equals("ksnf")) {
//                e.attr("value", "4263");//设置用户名
//            }
//            if (e.attr("name").equals("sf")) {
//                e.attr("value", "36");//设置用户名
//            }
//            if (e.attr("name").equals("bkjb")) {
//                e.attr("value", "3");//jb
//            }
//            if (e.attr("name").equals("zkzh")) {
//                e.attr("value", "1234567890"); //zkzh
//            }
//            if (e.attr("name").equals("sfzh")) {
//                e.attr("value", "113467199409187534"); //sfzh
//            }
//            if (e.attr("name").equals("name")) {
//                e.attr("value", "张三");//设置用户名
//            }
//            if (e.attr("name").equals("rand")) {
//                e.attr("value", "dk2r");//设置用户名
//            }
//            if (e.attr("name").length() > 0) {//排除空值表单属性
//                datas.put(e.attr("name"), e.attr("value"));
//            }
//        }
        /**
         * 第二次请求，post表单数据，以及cookie信息
         */
        Connection con2 = Jsoup.connect(action);
        con2.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.116 Safari/537.36");
        //设置cookie和post上面的map数据
        Connection.Response login = con2.ignoreContentType(true)
                .method(Connection.Method.POST)
                .data("state", "")
                .data("opt", "queryC")
                .data("ksnf", "4263")
                .data("sf", "36")
                .data("bkjb", "4")
                .data(datas)
                .cookies(rs.cookies())
                .execute();
        System.out.println(login.body());
    }

    private void te() throws IOException {
        Map<String, String> cookies;
        Connection.Response res = Jsoup.connect("http://chaxun.neea.edu.cn/examcenter/query.cn?op=doQueryCond&sid=280&pram=results")
                .userAgent("Mozilla/5.0 (Windows NT 10.0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.116 Safari/537.36")
                .timeout(30000)
                .execute();
        cookies = res.cookies();
        Document document = Jsoup.connect("http://chaxun.neea.edu.cn/examcenter/query.cn?op=doQueryResults")
                .data("state", "")
                .data("opt", "queryC")
                .data("ksnf", "4263")
                .data("sf", "36")
                .data("bkjb", "4")
                .data("zkzh", "1234567890")
                .data("name", "张三")
                .data("sfzh", "113467199409187534")
                .data("rand", "hc75")
                .data("ksxm", "280")
                .cookies(cookies)
                .timeout(5000)
                .post();
        System.out.println("shenhua sout:" + document);

    }

    private void jszgz() throws IOException {
// http://wx.233.com/u/hook/fork/o
// ?format=web
// &name=%E6%9D%8E%E6%98%8E
// &zjhm=116773199403248873
// &yzm=nwh5
// &api_hookId=7

        Document document = Jsoup.connect("http://wx.233.com/u/hook/fork/o")
                .data("format", "web")
//                .data("name", URLEncoder.encode("","utf-8"))
                .data("name", "")
                .data("zjhm", "116773199403248873")
                .data("yzm", "nwh5")
                .data("api_hookId", "7")
                .timeout(5000)
                .post();
        System.out.println("shenhua sout:" + document);
    }

    private void pth() throws IOException {
//        Document document = Jsoup.parse(new File("F:\\Users\\Shenhua\\Desktop\\4566.html"),"utf-8");
//        String element = document.getElementById("__VIEWSTATE").attr("value");
//        System.out.println("shenhua sout:" + element);
//        String scoreList = document.getElementById("LooUpSocreList_Div").text();
//        if (scoreList.equals("")){
//            System.out.println("shenhua sout:" + "true");
//        }


// __VIEWSTATE=%2FwEPDwUKMjExOTU2MjkwOQ9kFgICAw9kFghmDw8WAh4EVGV4dAUo5pma5LiK5aW977yB5LuK5aSp5pivOeaciDI35pelIOaYn%2Bacn%2BS6jGRkAgEPFgIeB1Zpc2libGVnZAICDw8WAh8BZ2RkAgMPFgIfAWgWAgIBDxYCHgtfIUl0ZW1Db3VudAL%2F%2F%2F%2F%2FD2Rk
// &txtStuID=3242342443134232525
// &txtName=%E6%9D%8E%E6%95%8F
// &txtIDCard=423423525252525252
// &btnLogin=%E6%9F%A5++%E8%AF%A2
// &txtCertificateNO=
// &txtCardNO=

        Map<String, String> cookies;
        Connection.Response res = Jsoup.connect(HttpService.SCORE_QUERY_URL_MANDARIN).timeout(30000).execute();
        cookies = res.cookies();
        String __VIEWSTATE = Jsoup.parse(res.body()).getElementById("__VIEWSTATE").attr("value");
        System.out.println("shenhua sout:" + __VIEWSTATE);
        Document doc = Jsoup.connect(HttpService.SCORE_QUERY_URL_MANDARIN)
                .data("__VIEWSTATE", __VIEWSTATE)
                .data("txtStuID", "3242342443134232525")
                .data("txtName", URLEncoder.encode("李敏", "utf-8"))
                .data("txtIDCard", "423423525252525252")
                .data("btnLogin", URLEncoder.encode("查  询", "utf-8"))
                .data("txtCertificateNO", "")
                .data("txtCardNO", "")
                .timeout(5000)
                .cookies(cookies)
                .post();
        String __VIEWSTATE2 = doc.getElementById("__VIEWSTATE").attr("value");
        System.out.println("shenhua sout:" + __VIEWSTATE2);
        String scoreList = doc.getElementById("LooUpSocreList_Div").text();
        if (scoreList.equals("")) System.out.println("shenhua sout:" + "查询失败，请确认所填写信息是否正确！");
    }

    private void c() throws UnsupportedEncodingException {
        double s = Math.random();
        String u = "http://cache.neea.edu.cn/Imgs.do?t=" + Double.toString(s);
        System.out.println("shenhua sout:" + u);
    }

//    void p() {
//        Document document = null;
//        Element divElement;
//        try {
//            document = Jsoup.parse(new File("F:\\Users\\Shenhua\\Desktop\\cet.html"), "utf-8");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try {
//            divElement = document.getElementsByClass("m_cnt_m").get(0).select("table").get(0).select("tbody").get(0);
//        } catch (Exception e) {
//            System.out.println("shenhua sout:" + "考号姓名输入错误");
//            return;
//        }
//
////        System.out.println("shenhua sout:" + divElement.select("td").get(0).text());
//        System.out.println("shenhua sout:" + divElement.select("td").get(5));
//    }

}