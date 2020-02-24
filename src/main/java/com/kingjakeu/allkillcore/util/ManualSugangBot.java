package com.kingjakeu.allkillcore.util;

import com.kingjakeu.allkillcore.common.enums.ServerUrl;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.Map;

@Slf4j
public class ManualSugangBot {

    public String requestLoginIn(String userId, String userPassword){
        String loginCookie = "";
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36";
        try{
            Connection.Response response = Jsoup.connect(ServerUrl.LOGIN.getUrl()+userId+ServerUrl.LOGIN_OPTION_PASSWORD.getUrl()+userPassword+ServerUrl.LOGIN_OPTION_END.getUrl())
                    .userAgent(userAgent)
                    .timeout(5000)
                    .header("Origin", "https://kupis.konkuk.ac.kr")
                    .header("Referer", "https://kupis.konkuk.ac.kr/sugang/login/loginTop.jsp")
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("Accept-Encoding", "gzip, deflate, br")
                    .header("Accept-Language", "ko-KR,ko;q=0.8,en-US;q=0.6,en;q=0.4")
                    .method(Connection.Method.POST)
                    .execute();
            Map<String , String> cookies = response.cookies();
            StringBuilder sb = new StringBuilder("WMONID=").append(cookies.get("WMONID")).append("; ")
                    .append("JSESSIONID=").append(cookies.get("JSESSIONID"));
            loginCookie = sb.toString();
            log.info("LOGIN COOKIE" + loginCookie);
        }catch (IOException e){
            log.error(e.getMessage(), e);
        }
        return loginCookie;
    }

    public void requestSugang(String cookie, String courseId){
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36";

        try {
            Connection.Response response = Jsoup.connect(ServerUrl.SUGANG.getUrl()+courseId+ServerUrl.SUGANG_OPTION_END.getUrl())
                    .userAgent(userAgent)
                    .timeout(5000)
                    .header("Origin", "https://kupis.konkuk.ac.kr")
                    .header("Referer", "https://kupis.konkuk.ac.kr/sugang/acd/cour/aply/courLessinApplyReg.jsp")
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("Accept-Encoding", "gzip, deflate, br")
                    .header("Accept-Language", "ko-KR,ko;q=0.8,en-US;q=0.6,en;q=0.4")
                    .header("Cookie", cookie)
                    .method(Connection.Method.POST)
                    .execute();
            Document doc = response.parse();
            Element inputElement = doc.select("input[NAME=CuriCdt]").first();
            log.info(inputElement.val());

        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    public String checkSession(String cookie){
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36";

        try {
            Connection.Response response = Jsoup.connect("https://kupis.konkuk.ac.kr/sugang/acd/cour/aply/courBasket1ApplyReg.jsp")
                    .userAgent(userAgent)
                    .timeout(5000)
                    .header("Origin", "https://kupis.konkuk.ac.kr")
                    .header("Referer", "https://kupis.konkuk.ac.kr/sugang/acd/cour/aply/courLessinApplyReg.jsp")
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("Accept-Encoding", "gzip, deflate, br")
                    .header("Accept-Language", "ko-KR,ko;q=0.8,en-US;q=0.6,en;q=0.4")
                    .header("Cookie", cookie)
                    .method(Connection.Method.POST)
                    .execute();
            Document doc = response.parse();
            log.info(doc.toString());
            Element inputElement = doc.select("input[NAME=CuriCdt]").first();
            log.info(inputElement.val());

            return inputElement.val();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return "";
    }
}
