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

    public CourseLikeHistory toEntity(){
        return CourseLikeHistory.builder()
                .id("a")
                .courseId(courseId)
                .memberId(memberId)

                .build();
    }
}
