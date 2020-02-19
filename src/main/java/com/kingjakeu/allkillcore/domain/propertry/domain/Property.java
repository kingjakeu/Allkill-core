package com.kingjakeu.allkillcore.domain.propertry.domain;

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
 *  0.1    2020/02/19    jakeyoo    최초작성
 * ====================================================================
 * </pre>
 * @date 2020/02/19
 */

@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Property {

    @Id
    private String propertyId;

    @Column
    private String value;

    @Builder
    public Property(String propertyId, String value){
        this.propertyId = propertyId;
        this.value = value;
    }
}
