package com.kingjakeu.allkillcore.domain.course.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Course {

    @Id
    private String id;

    @Column
    private String name;

    @Column
    private String instructor;

    @Column
    private String courseType;

    @Column
    private int credit;

    @Column
    private int grade;

    @Column
    private String location;

    @Column
    private String departmentId;

    @Column
    private String classYear;

    @Column
    private String semester;

}
