package com.kingjakeu.allkillcore.domain.course.dao;

import com.kingjakeu.allkillcore.domain.course.domain.CourseLikeHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CourseLikeHistoryRepository extends JpaRepository<CourseLikeHistory, Integer> {

    @Modifying
    @Query("delete from CourseLikeHistory c where c.courseId =:courseId")
    void deleteCourseLikeHistoryByCourseId(@Param("courseId") String courseId);
}
