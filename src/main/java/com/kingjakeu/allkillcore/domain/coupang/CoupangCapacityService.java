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


    @Scheduled(fixedRate = 4000)
    public void crawlCoupang() {
        Connection connection = this.getConnection();
        try{
            Document document = connection.get();
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
        return Jsoup.connect("https://www.coupang.com/vp/products/1384804427")
                .timeout(4000)
                .followRedirects(true)
                .header("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9'")
                .header("accept-encoding", "gzip, deflate, br")
                .header("accept-language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
                .header("cache-control", "no-cache")
                .header("pragma", "no-cache")
                .header("sec-fetch-dest", "document")
                .header("sec-fetch-mode", "navigate")
                .header("sec-fetch-site", "none")
                .header("user-agent", "Mozilla/5.0");
    }

    private String getSlackLink(){
        Optional<Property> slackUrl = propertyRepository.findById("SLACK");
        return slackUrl.isPresent() ? slackUrl.get().getValue() : "";
    }
}
