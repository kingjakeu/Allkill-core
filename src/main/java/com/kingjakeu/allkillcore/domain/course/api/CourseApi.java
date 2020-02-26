package com.kingjakeu.allkillcore.domain.course.api;

import com.kingjakeu.allkillcore.domain.course.dao.CourseAutoSaveRepository;
import com.kingjakeu.allkillcore.domain.course.dao.CourseCapacityRepository;
import com.kingjakeu.allkillcore.domain.course.dao.CourseLikeHistoryRepository;
import com.kingjakeu.allkillcore.domain.course.dao.CourseRepository;
import com.kingjakeu.allkillcore.domain.course.domain.Course;
import com.kingjakeu.allkillcore.domain.course.domain.CourseAutoSave;
import com.kingjakeu.allkillcore.domain.course.domain.CourseCapacity;
import com.kingjakeu.allkillcore.domain.course.domain.CourseLikeHistory;
import com.kingjakeu.allkillcore.domain.course.dto.CourseDto;
import com.kingjakeu.allkillcore.domain.course.dto.CourseLikeHistoryDto;
import com.kingjakeu.allkillcore.domain.course.dto.ManualSugangDto;
import com.kingjakeu.allkillcore.domain.course.service.CourseService;
import com.kingjakeu.allkillcore.util.ManualSugangBot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
    private CourseAutoSaveRepository courseAutoSaveRepository;

    @Autowired
    public CourseApi(CourseService courseService, CourseRepository courseRepository,
                     CourseLikeHistoryRepository courseLikeHistoryRepository, CourseCapacityRepository courseCapacityRepository,
                     CourseAutoSaveRepository courseAutoSaveRepository){
        this.courseService = courseService;
        this.courseRepository = courseRepository;
        this.courseLikeHistoryRepository = courseLikeHistoryRepository;
        this.courseCapacityRepository = courseCapacityRepository;
        this.courseAutoSaveRepository = courseAutoSaveRepository;
    }

    @PostMapping("/crawl")
    public void crawlCourseInfo(@RequestBody CourseDto courseDto){
        List<Course> courseList = courseService.crawlCourseInfoFromSource(courseDto.getDepartmentId(), courseDto.getCourseType());
        courseRepository.saveAll(courseList);
    }

    @GetMapping("like-list")
    public List<CourseLikeHistory> getCourseLikeList(){
        return courseLikeHistoryRepository.findAll();
    }

    @GetMapping("/crawl-list")
    public List<CourseCapacity> getCrawlingList(){
       return courseCapacityRepository.findAll();
    }

    @GetMapping("/auto-list")
    public List<CourseAutoSave> getAutoSaveList(){
        return courseAutoSaveRepository.findAll();
    }

    @PostMapping("/like")
    public void likeCourse(@RequestBody CourseLikeHistoryDto courseLikeHistoryDto){
        Optional<Course> courseOptional = courseRepository.findById(courseLikeHistoryDto.getCourseId());

        String courseName = courseOptional.isPresent() ? courseOptional.get().getName() : "";
        String courseLocation = courseOptional.isPresent() ? courseOptional.get().getLocation() : "";
        courseLikeHistoryDto.setCourseName(courseName);
        courseLikeHistoryDto.setCourseLocation(courseLocation);
        courseLikeHistoryRepository.save(courseLikeHistoryDto.toEntity());
    }

    @PostMapping("/auto-save")
    public void saveAutoCourse(@RequestBody CourseLikeHistoryDto courseLikeHistoryDto){
       Optional<CourseCapacity> courseCapacityOptional = courseCapacityRepository.findById(courseLikeHistoryDto.getCourseId());
       courseCapacityOptional.ifPresent(courseCapacity -> courseAutoSaveRepository.save(courseCapacity.toCourseAutoSave()));
    }

    @PostMapping("/dislike")
    public void dislikeCourse(@RequestBody CourseLikeHistoryDto courseLikeHistoryDto){
        courseLikeHistoryRepository.delete(courseLikeHistoryDto.toEntity());
    }

    @PostMapping("/delete")
    @Transactional
    public void deleteCourse(@RequestBody CourseLikeHistoryDto courseLikeHistoryDto){
        courseLikeHistoryRepository.deleteCourseLikeHistoryByCourseId(courseLikeHistoryDto.getCourseId());
        courseAutoSaveRepository.deleteById(courseLikeHistoryDto.getCourseId());
        courseCapacityRepository.deleteById(courseLikeHistoryDto.getCourseId());
    }

    @PostMapping("/login")
    public String doLoginManually(@RequestBody ManualSugangDto manualSugangDto){
        ManualSugangBot sugangBot = new ManualSugangBot();
        return sugangBot.requestLoginIn(manualSugangDto.getUserId(), manualSugangDto.getUserPassword());
    }

    @PostMapping("/session")
    public String checkSession(@RequestBody ManualSugangDto manualSugangDto){
        ManualSugangBot sugangBot = new ManualSugangBot();
        return sugangBot.checkSession(manualSugangDto.getCookie());
    }

    @PostMapping("/sugang")
    public void doSugangManually(@RequestBody ManualSugangDto manualSugangDto){
        ManualSugangBot sugangBot = new ManualSugangBot();
        sugangBot.requestSugang(manualSugangDto.getCookie(), manualSugangDto.getCourseId());
    }
}
