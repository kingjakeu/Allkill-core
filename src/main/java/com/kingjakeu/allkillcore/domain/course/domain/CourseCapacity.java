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
 *  0.1    2020/02/18    jakeyoo    최초작성
 * ====================================================================
 * </pre>
 * @date 2020/02/18
 */
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class CourseCapacity {

    @Id
    private String courseId;

    @Column
    private String courseName;

    @Column
    private String location;

    @Column
    private int remainCapacity;

    @Column
    private int enrolledCapacity;

    @Column
    private int totalCapacity;


    @Builder
    public CourseCapacity(String courseId,  int enrolledCapacity, int totalCapacity){
        this.courseId = courseId;
        this.enrolledCapacity = enrolledCapacity;
        this.totalCapacity = totalCapacity;
        this.remainCapacity = totalCapacity - enrolledCapacity;
    }

    public void setCourseName(String courseName){
        this.courseName = courseName;
    }

    public void setLocation(String location){ this.location = location;}

    public void updatedCapacity(int enrolledCapacity, int totalCapacity){
        this.enrolledCapacity = enrolledCapacity;
        this.totalCapacity = totalCapacity;
        this.remainCapacity = totalCapacity - enrolledCapacity;
    }

    public String toSlackMessage(){
        return this.courseId + " " +courseName +"\n"+ location +"\n" +remainCapacity+" 자리 열림";
    }

    public String toSlackMessage(String addMessage) {
        return this.toSlackMessage() + addMessage;
    }

    public CourseAutoSave toCourseAutoSave(){
        return CourseAutoSave.builder()
                .courseId(this.courseId)
                .courseName(this.courseName)
                .build();
    }
}
