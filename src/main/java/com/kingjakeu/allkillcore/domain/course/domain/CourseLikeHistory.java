package com.kingjakeu.allkillcore.domain.course.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class CourseLikeHistory {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String courseId;

    @Column
    private String courseName;

    @Column
    private String memberId;

    @Builder
    public CourseLikeHistory(String courseId, String courseName, String memberId){
        this.courseId = courseId;
        this.courseName = courseName;
        this.memberId = memberId;
    }
}
