package com.kingjakeu.allkillcore.util;

import com.kingjakeu.allkillcore.common.enums.ServerUrl;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.Map;

/**
 * <b>  </b>
 *
 * @author jakeyoo
 * @version 0.1 : 최초작성
 * <hr>
 * <pre>
 *
 * description
 *
 * <b>History:</b>
 * ====================================================================
 *  버전  :    작성일    :  작성자  :  작성내역
 * --------------------------------------------------------------------
 *  0.1    2020/02/19    jakeyoo    최초작성
 * ====================================================================
 * </pre>
 * @date 2020/02/19
 */
@Slf4j
public class AutoSugangBot {

    public String sugangBotExecute(String userId, String userPassword, String courseId){
        String loginCookie = getLoginCookie(userId, userPassword);
        return sendSugangRequest(ServerUrl.SUGANG.getUrl()+courseId+ServerUrl.SUGANG_OPTION_END.getUrl(), loginCookie);
    }

    private String getLoginCookie(String userId, String userPassword){
        String loginCookie = "";
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36";
        try{
            Connection.Response response = Jsoup.connect(ServerUrl.LOGIN.getUrl()+userId+ServerUrl.LOGIN_OPTION_PASSWORD.getUrl()+userPassword+ServerUrl.LOGIN_OPTION_END.getUrl())
                    .userAgent(userAgent)
                    .timeout(3000)
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

    private String sendSugangRequest(String url, String loginCookie){
        try{
            String userAgent = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36";
            Connection.Response beforeResponse = Jsoup.connect("https://kupis.konkuk.ac.kr/sugang/acd/cour/aply/courLessinApplyReg.jsp")
                    .userAgent(userAgent)
                    .timeout(3000)
                    .header("Origin", "https://kupis.konkuk.ac.kr")
                    .header("Referer", "https://kupis.konkuk.ac.kr/sugang/acd/cour/aply/courLessinApplyReg.jsp")
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("Accept-Encoding", "gzip, deflate, br")
                    .header("Accept-Language", "ko-KR,ko;q=0.8,en-US;q=0.6,en;q=0.4")
                    .header("Cookie", loginCookie)
                    .method(Connection.Method.POST)
                    .execute();
            Document bfDoc = beforeResponse.parse();
            Element beforeElement = bfDoc.select("input[NAME=CuriCdt]").first();
            log.info("BEFORE: "+beforeElement.val());

            Connection.Response response = Jsoup.connect(url)
                    .userAgent(userAgent)
                    .timeout(3000)
                    .header("Origin", "https://kupis.konkuk.ac.kr")
                    .header("Referer", "https://kupis.konkuk.ac.kr/sugang/acd/cour/aply/courLessinApplyReg.jsp")
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("Accept-Encoding", "gzip, deflate, br")
                    .header("Accept-Language", "ko-KR,ko;q=0.8,en-US;q=0.6,en;q=0.4")
                    .header("Cookie", loginCookie)
                    .method(Connection.Method.POST)
                    .execute();
            Document doc = response.parse();
            Element inputElement = doc.select("input[NAME=CuriCdt]").first();
            if(!inputElement.val().equals(beforeElement.val())){
                log.info("SUCCUESS SUGANG");
                return "SUCCUESS";
            }
        }catch (IOException e){
            log.error(e.getMessage(), e);
        }
        return "FAIL";
    }
}
