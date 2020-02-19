package com.kingjakeu.allkillcore.domain.course.service;

import com.kingjakeu.allkillcore.common.enums.CrawlQuery;
import com.kingjakeu.allkillcore.common.enums.ServerUrl;
import com.kingjakeu.allkillcore.domain.course.dao.CourseCapacityRepository;
import com.kingjakeu.allkillcore.domain.course.dao.CourseLikeHistoryRepository;
import com.kingjakeu.allkillcore.domain.course.domain.CourseCapacity;
import com.kingjakeu.allkillcore.domain.course.domain.CourseLikeHistory;
import com.kingjakeu.allkillcore.domain.propertry.dao.PropertyRepository;
import com.kingjakeu.allkillcore.domain.propertry.domain.Property;
import com.kingjakeu.allkillcore.util.Crawler;
import com.kingjakeu.allkillcore.util.SlackSender;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
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
 *  0.1    2020/02/18    jakeyoo    최초작성
 * ====================================================================
 * </pre>
 * @date 2020/02/18
 */
@Slf4j
@Service
public class CourseCapacityService {

    private CourseLikeHistoryRepository courseLikeHistoryRepository;
    private CourseCapacityRepository courseCapacityRepository;
    private PropertyRepository propertyRepository;

    @Autowired
    public CourseCapacityService(CourseLikeHistoryRepository courseLikeHistoryRepository, CourseCapacityRepository courseCapacityRepository, PropertyRepository propertyRepository){
        this.courseLikeHistoryRepository = courseLikeHistoryRepository;
        this.courseCapacityRepository = courseCapacityRepository;
        this.propertyRepository = propertyRepository;
    }

    @Scheduled(fixedRate = 5000)
    public void runScheduledCrawling(){
        log.info("SCHEDULER RUN");
        List<CourseLikeHistory> courseLikeHistoryList = courseLikeHistoryRepository.findAll();
        SlackSender slackSender = new SlackSender(this.getSlackLink());

        for(CourseLikeHistory courseLikeHistory : courseLikeHistoryList){
            CourseCapacity crawlCapacityInfo = this.crawlCapacityInfo(courseLikeHistory.getCourseId());
            crawlCapacityInfo.setCourseName(courseLikeHistory.getCourseName());

            Optional<CourseCapacity> capacityData = courseCapacityRepository.findById(courseLikeHistory.getCourseId());
            if(capacityData.isPresent()){
                if(capacityData.get().getRemainCapacity() < crawlCapacityInfo.getRemainCapacity()){
                    log.info("ALERT SEAT REMAIN");
                    slackSender.sendMessage(crawlCapacityInfo.toSlackMessage());
                }
            }else{
                if(crawlCapacityInfo.getRemainCapacity() > 0){
                    log.info("ALERT REMAIN2");
                    //this.sendSlackMessage(newCourseCapacity);
                }
                log.info("ALERT SAVED :"+crawlCapacityInfo.toString());
            }
            courseCapacityRepository.save(crawlCapacityInfo);
        }
        log.info("SCHEDULER DONE");
    }

    private CourseCapacity crawlCapacityInfo(String id){
        Crawler crawler = new Crawler(ServerUrl.CAPACITY_INFO.getUrl());
        crawler.setOptionalUrl(id);
        Elements elements = crawler.crawl().getElementsByCssQuery(CrawlQuery.CAPACITY_INFO.getQuery());
        int totalCapacity = Integer.parseInt(elements.get(2).text());
        int enrolledCapacity = Integer.parseInt(elements.get(1).text());

        return CourseCapacity.builder()
                .courseId(id)
                .enrolledCapacity(enrolledCapacity)
                .totalCapacity(totalCapacity)
                .build();
    }

    private String getSlackLink(){
        Optional<Property> slackUrl = propertyRepository.findById("SLACK");
        return slackUrl.isPresent() ? slackUrl.get().getValue() : "";
    }
}
