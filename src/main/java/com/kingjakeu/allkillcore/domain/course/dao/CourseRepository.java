package com.kingjakeu.allkillcore.domain.course.dao;

import com.kingjakeu.allkillcore.domain.course.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, String> {
}
