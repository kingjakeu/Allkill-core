package com.kingjakeu.allkillcore.domain.course.api;

import com.kingjakeu.allkillcore.domain.course.dao.CourseLikeHistoryRepository;
import com.kingjakeu.allkillcore.domain.course.domain.CourseLikeHistory;
import com.kingjakeu.allkillcore.domain.course.dto.CourseLikeHistoryDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/course")
public class CourseApi {

    private CourseLikeHistoryRepository courseLikeHistoryRepository;

    @PostMapping("/like")
    public void likeCourse(@RequestBody CourseLikeHistoryDto courseLikeHistoryDto){
        courseLikeHistoryRepository.save(courseLikeHistoryDto.toEntity());
    }

    @PostMapping("/unlike")
    public void unlikeCourse(@RequestBody CourseLikeHistoryDto courseLikeHistoryDto){
        courseLikeHistoryRepository.delete(courseLikeHistoryDto.toEntity());
    }
}
