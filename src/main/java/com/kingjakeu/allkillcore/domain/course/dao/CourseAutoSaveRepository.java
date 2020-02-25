package com.kingjakeu.allkillcore.domain.course.dao;

import com.kingjakeu.allkillcore.domain.course.domain.CourseAutoSave;
import org.springframework.data.jpa.repository.JpaRepository;

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
public interface CourseAutoSaveRepository extends JpaRepository<CourseAutoSave, String> {
}
