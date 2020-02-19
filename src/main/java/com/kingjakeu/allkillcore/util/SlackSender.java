package com.kingjakeu.allkillcore.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

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
public class SlackSender {
    private RestTemplate restTemplate;
    private HttpHeaders httpHeaders;
    private String url;

    public SlackSender(String url){
        this.url = url;
        this.restTemplate = new RestTemplate();
        this.httpHeaders = new HttpHeaders();
        this.httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
    }

    public void sendMessage(String message){
        HttpEntity<String> httpEntity = new HttpEntity<>(message, this.httpHeaders);
        this.restTemplate.exchange(this.url, HttpMethod.POST, httpEntity, String.class);
    }
}
