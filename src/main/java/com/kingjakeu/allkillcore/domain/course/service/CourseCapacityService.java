package com.kingjakeu.allkillcore.domain.course.service;

import com.kingjakeu.allkillcore.common.enums.CrawlQuery;
import com.kingjakeu.allkillcore.common.enums.ServerUrl;
import com.kingjakeu.allkillcore.domain.course.dao.CourseCapacityRepository;
import com.kingjakeu.allkillcore.domain.course.dao.CourseLikeHistoryRepository;
import com.kingjakeu.allkillcore.domain.course.dao.CourseRepository;
import com.kingjakeu.allkillcore.domain.course.domain.Course;
import com.kingjakeu.allkillcore.domain.course.domain.CourseCapacity;
import com.kingjakeu.allkillcore.domain.course.domain.CourseLikeHistory;
import com.kingjakeu.allkillcore.util.Crawler;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
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

    private CourseRepository courseRepository;
    private CourseLikeHistoryRepository courseLikeHistoryRepository;
    private CourseCapacityRepository courseCapacityRepository;

    @Autowired
    public CourseCapacityService(CourseRepository courseRepository, CourseLikeHistoryRepository courseLikeHistoryRepository, CourseCapacityRepository courseCapacityRepository){
        this.courseRepository = courseRepository;
        this.courseLikeHistoryRepository = courseLikeHistoryRepository;
        this.courseCapacityRepository = courseCapacityRepository;
    }

    @Scheduled(fixedRate = 5000)
    public void runScheduledCrawling(){
        log.info("SCHEDULER RUN");
        Crawler crawler = new Crawler(ServerUrl.CAPACITY_INFO.getUrl());
        List<CourseLikeHistory> courseLikeHistoryList = courseLikeHistoryRepository.findAll();
        for(CourseLikeHistory courseLikeHistory : courseLikeHistoryList){
            crawler.setOptionalUrl(courseLikeHistory.getCourseId());
            Elements elements = crawler.crawl().getElementsByCssQuery(CrawlQuery.CAPACITY_INFO.getQuery());
            int totalCapacity = Integer.parseInt(elements.get(2).text());
            int enrolledCapacity = Integer.parseInt(elements.get(1).text());
            int remainedCapacity = totalCapacity - enrolledCapacity;

            Optional<CourseCapacity> courseCapacity = courseCapacityRepository.findById(courseLikeHistory.getCourseId());

            if(courseCapacity.isPresent()){
                courseCapacity.get().updatedCapacity(enrolledCapacity, totalCapacity);
                if(remainedCapacity > courseCapacity.get().getRemainCapacity()){
                    log.info("ALERT REMAIN1");
                    this.sendSlackMessage(courseCapacity.get());
                }
                courseCapacityRepository.save(courseCapacity.get());
            }else{
                Optional<Course> course = courseRepository.findById(courseLikeHistory.getCourseId());
                CourseCapacity newCourseCapacity = CourseCapacity.builder()
                        .courseId(courseLikeHistory.getCourseId())
                        .courseName(course.isPresent()?course.get().getName():"")
                        .enrolledCapacity(enrolledCapacity)
                        .totalCapacity(totalCapacity)
                        .build();
                if(remainedCapacity > 0){
                    log.info("ALERT REMAIN2");
                    //this.sendSlackMessage(newCourseCapacity);
                }
                courseCapacityRepository.save(newCourseCapacity);
            }
        }
        log.info("SCHEDULER DONE");
    }

    private void sendSlackMessage(CourseCapacity courseCapacity){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForEntity(ServerUrl.SLACK.getUrl(), courseCapacity.toSlackMessage(), String.class);
    }
}
