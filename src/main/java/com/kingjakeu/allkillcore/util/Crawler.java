package com.kingjakeu.allkillcore.util;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

@Slf4j
public class Crawler {

    private String url;
    private Document document;

    public Crawler(String url){
        this.url = url;
    }

    public Crawler crawl(){
        try {
            this.document = Jsoup.connect(this.url).post();
            //log.info(document.toString());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return this;
    }

    public Elements getElementsByCssQuery(String cssQuery){
        return this.document.select(cssQuery);
    }
}
