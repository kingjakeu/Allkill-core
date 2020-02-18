package com.kingjakeu.allkillcore.domain.course.service;

import com.kingjakeu.allkillcore.common.enums.CrawlQuery;
import com.kingjakeu.allkillcore.common.enums.ServerUrl;
import com.kingjakeu.allkillcore.domain.course.dao.CourseCapacityRepository;
import com.kingjakeu.allkillcore.domain.course.dao.CourseLikeHistoryRepository;
import com.kingjakeu.allkillcore.domain.course.domain.Course;
import com.kingjakeu.allkillcore.domain.course.domain.CourseCapacity;
import com.kingjakeu.allkillcore.domain.course.domain.CourseLikeHistory;
import com.kingjakeu.allkillcore.util.Crawler;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
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

    @Autowired
    public CourseCapacityService(CourseLikeHistoryRepository courseLikeHistoryRepository, CourseCapacityRepository courseCapacityRepository){
        this.courseLikeHistoryRepository = courseLikeHistoryRepository;
        this.courseCapacityRepository = courseCapacityRepository;
    }

    @Scheduled(fixedRate = 10000)
    public void runScheduledCrawling(){
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
                if(remainedCapacity > courseCapacity.get().getRemainCapacity() || remainedCapacity > 0){
                    log.info("ALERT REMAIN1");
                }
                courseCapacity.get().updatedCapacity(enrolledCapacity, totalCapacity);
                courseCapacityRepository.save(courseCapacity.get());
            }else{
                if(remainedCapacity > 0){
                    log.info("ALERT REMAIN2");
                }
                courseCapacityRepository.save(CourseCapacity.builder()
                        .courseId(courseLikeHistory.getCourseId())
                        .enrolledCapacity(enrolledCapacity)
                        .totalCapacity(totalCapacity)
                        .build());
            }
        }
    }
}
