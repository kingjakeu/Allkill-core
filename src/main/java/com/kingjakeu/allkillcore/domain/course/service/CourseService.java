package com.kingjakeu.allkillcore.domain.course.service;

import com.kingjakeu.allkillcore.common.enums.CrawlQuery;
import com.kingjakeu.allkillcore.common.enums.ServerUrl;
import com.kingjakeu.allkillcore.domain.course.domain.Course;
import com.kingjakeu.allkillcore.util.Crawler;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Slf4j
@Service
public class CourseService {

    public List<Course> crawlCourseInfoFromSource(String departmentId, String courseType){
        Crawler crawler = new Crawler(ServerUrl.COURSE_INFO.getUrl()+departmentId+ServerUrl.COURSE_INFO_TYPE_OPTION.getUrl()+courseType);
        Elements elements = crawler.crawl().getElementsByCssQuery(CrawlQuery.COURSE_INFO.getQuery());
        String key = "td";
        elements = elements.select(key);

        int size = elements.size();
        List<Course> courses = new LinkedList<>();
        for (int i = 1; i < size; i+=18) {
            Course course = Course.builder()
                    .id(elements.get(i+3).text())
                    .name(elements.get(i+4).text())
                    .instructor(elements.get(i+9).text())
                    .courseType(elements.get(i+2).text())
                    .credit(Integer.parseInt(elements.get(i+5).text()))
                    .grade(Integer.parseInt(elements.get(i).text()))
                    .location(elements.get(i+8).text())
                    .classYear(ServerUrl.ConstString.YEAR)
                    .semester(ServerUrl.ConstString.SEMESTER)
                    .departmentId(departmentId)
                    .build();
            //log.info("\n"+course.toString());
            courses.add(course);
        }
        return courses;
    }
}
