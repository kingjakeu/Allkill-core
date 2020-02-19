package com.kingjakeu.allkillcore.domain.course.api;

import com.kingjakeu.allkillcore.domain.course.dao.CourseCapacityRepository;
import com.kingjakeu.allkillcore.domain.course.dao.CourseLikeHistoryRepository;
import com.kingjakeu.allkillcore.domain.course.dao.CourseRepository;
import com.kingjakeu.allkillcore.domain.course.domain.Course;
import com.kingjakeu.allkillcore.domain.course.domain.CourseCapacity;
import com.kingjakeu.allkillcore.domain.course.dto.CourseDto;
import com.kingjakeu.allkillcore.domain.course.dto.CourseLikeHistoryDto;
import com.kingjakeu.allkillcore.domain.course.service.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/course")
public class CourseApi {

    private CourseService courseService;
    private CourseRepository courseRepository;
    private CourseLikeHistoryRepository courseLikeHistoryRepository;
    private CourseCapacityRepository courseCapacityRepository;

    @Autowired
    public CourseApi(CourseService courseService, CourseRepository courseRepository, CourseLikeHistoryRepository courseLikeHistoryRepository, CourseCapacityRepository courseCapacityRepository){
        this.courseService = courseService;
        this.courseRepository = courseRepository;
        this.courseLikeHistoryRepository = courseLikeHistoryRepository;
        this.courseCapacityRepository = courseCapacityRepository;
    }

    @PostMapping("/crawl")
    public void crawlCourseInfo(@RequestBody CourseDto courseDto){
        List<Course> courseList = courseService.crawlCourseInfoFromSource(courseDto.getDepartmentId(), courseDto.getCourseType());
        courseRepository.saveAll(courseList);
    }

    @GetMapping("/crawl-list")
    public List<CourseCapacity> getCrawlingList(){
       return courseCapacityRepository.findAll();
    }


    @PostMapping("/like")
    public void likeCourse(@RequestBody CourseLikeHistoryDto courseLikeHistoryDto){
        Optional<Course> courseOptional = courseRepository.findById(courseLikeHistoryDto.getCourseId());

        String courseName = courseOptional.isPresent() ? courseOptional.get().getName() : "";
        courseLikeHistoryDto.setCourseName(courseName);
        courseLikeHistoryRepository.save(courseLikeHistoryDto.toEntity());
    }

    @PostMapping("/unlike")
    public void unlikeCourse(@RequestBody CourseLikeHistoryDto courseLikeHistoryDto){
        courseLikeHistoryRepository.delete(courseLikeHistoryDto.toEntity());
    }
}
