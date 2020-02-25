package com.kingjakeu.allkillcore.domain.course.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

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
 *  0.1    2020/02/25    jakeyoo    최초작성
 * ====================================================================
 * </pre>
 * @date 2020/02/25
 */
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class CourseAutoSave {
    @Id
    private String courseId;

    @Column
    private String courseName;

    @Builder
    public CourseAutoSave(String courseId, String courseName){
        this.courseId = courseId;
        this.courseName = courseName;
    }
}
