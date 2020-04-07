package com.kingjakeu.allkillcore.domain.coupang;

import com.kingjakeu.allkillcore.domain.propertry.dao.PropertyRepository;
import com.kingjakeu.allkillcore.domain.propertry.domain.Property;
import com.kingjakeu.allkillcore.util.SlackSender;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

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
 *  0.1    2020/04/07    jakeyoo    최초작성
 * ====================================================================
 * </pre>
 * @date 2020/04/07
 */
@Slf4j
@Service
public class CoupangCapacityService {

    private PropertyRepository propertyRepository;

    @Autowired
    public CoupangCapacityService(PropertyRepository propertyRepository){
        this.propertyRepository = propertyRepository;
    }


    @Scheduled(fixedRate = 2000)
    public void crawlCoupang() {
        Connection connection = this.getConnection();
        try{
            Connection.Response response = connection.execute();
            Document document = response.parse();
            Element element = document.getElementsByClass("prod-quantity__input").first();
            String cap = element.attr("value");
            log.info("Remained : " + cap);

            if(cap.equals("1")){
                SlackSender slackSender = new SlackSender(this.getSlackLink());
                slackSender.sendMessage("GO GO COUPANG");
            }
        }catch (IOException e){
            log.error(e.getMessage());
        }
    }

    public Connection getConnection(){
        return Jsoup.connect("https://www.coupang.com/vp/products/1384804427?itemId=2419615336&vendorItemId=70413795361&isAddedCart=")
                .userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36")
                .timeout(3000)
                .header("accept", "*/*")
                .header("accept-encoding", "gzip, deflate, br")
                .header("accept-language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
                .header("cache-control", "no-cache")
                .header("pragma", "no-cache")
                .header("referer", "https://www.coupang.com/vp/products/1384804427?itemId=2419615336&vendorItemId=70413795361&isAddedCart=")
                .header("sec-fetch-dest", "empty")
                .header("sec-fetch-mode", "cors")
                .header("sec-fetch-site", "same-origin")
                .header("x-requested-with", "")
                .method(Connection.Method.GET);
    }

    private String getSlackLink(){
        Optional<Property> slackUrl = propertyRepository.findById("SLACK");
        return slackUrl.isPresent() ? slackUrl.get().getValue() : "";
    }
}
