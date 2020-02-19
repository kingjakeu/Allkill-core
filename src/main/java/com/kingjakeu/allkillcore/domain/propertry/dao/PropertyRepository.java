package com.kingjakeu.allkillcore.domain.propertry.dao;

import com.kingjakeu.allkillcore.domain.course.domain.CourseLikeHistory;
import com.kingjakeu.allkillcore.domain.propertry.domain.Property;
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
 *  0.1    2020/02/19    jakeyoo    최초작성
 * ====================================================================
 * </pre>
 * @date 2020/02/19
 */
public interface PropertyRepository extends JpaRepository<Property, String> {
}
