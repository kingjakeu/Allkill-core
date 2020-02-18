package com.kingjakeu.allkillcore.domain.course.api;

import com.kingjakeu.allkillcore.domain.course.dao.CourseLikeHistoryRepository;
import com.kingjakeu.allkillcore.domain.course.dao.CourseRepository;
import com.kingjakeu.allkillcore.domain.course.domain.Course;
import com.kingjakeu.allkillcore.domain.course.domain.CourseLikeHistory;
import com.kingjakeu.allkillcore.domain.course.dto.CourseDto;
import com.kingjakeu.allkillcore.domain.course.dto.CourseLikeHistoryDto;
import com.kingjakeu.allkillcore.domain.course.service.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/course")
public class CourseApi {

    private CourseService courseService;
    private CourseRepository courseRepository;
    private CourseLikeHistoryRepository courseLikeHistoryRepository;

    @Autowired
    public CourseApi(CourseService courseService, CourseRepository courseRepository, CourseLikeHistoryRepository courseLikeHistoryRepository){
        this.courseService = courseService;
        this.courseRepository = courseRepository;
        this.courseLikeHistoryRepository = courseLikeHistoryRepository;
    }

    @PostMapping("/crawl")
    public void crawlCourseInfo(@RequestBody CourseDto courseDto){
        List<Course> courseList = courseService.crawlCourseInfoFromSource(courseDto.getDepartmentId(), courseDto.getCourseType());
        courseRepository.saveAll(courseList);
    }

    @PostMapping("/like")
    public void likeCourse(@RequestBody CourseLikeHistoryDto courseLikeHistoryDto){
        courseLikeHistoryRepository.save(courseLikeHistoryDto.toEntity());
    }

    @PostMapping("/unlike")
    public void unlikeCourse(@RequestBody CourseLikeHistoryDto courseLikeHistoryDto){
        courseLikeHistoryRepository.delete(courseLikeHistoryDto.toEntity());
    }
}
