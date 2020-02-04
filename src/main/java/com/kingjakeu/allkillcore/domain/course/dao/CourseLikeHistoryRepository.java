package com.kingjakeu.allkillcore.domain.course.dao;

import com.kingjakeu.allkillcore.domain.course.domain.CourseLikeHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseLikeHistoryRepository extends JpaRepository<CourseLikeHistory, Integer> {
}
