package com.kingjakeu.allkillcore.domain.course.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class CourseLikeHistory {
    @Id
    @Generated
    private String id;

    @Column
    private String courseId;

    @Column
    private String memberId;

}
