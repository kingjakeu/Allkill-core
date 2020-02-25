package com.kingjakeu.allkillcore.domain.course.dto;

import com.kingjakeu.allkillcore.domain.course.domain.CourseLikeHistory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CourseLikeHistoryDto {
    private String courseId;
    private String memberId;
    private String courseName;
    private String courseLocation;

    public CourseLikeHistory toEntity(){
        return CourseLikeHistory.builder()
                .courseId(courseId)
                .courseName(courseName)
                .memberId(memberId)
                .courseLocation(courseLocation)
                .build();
    }
}
