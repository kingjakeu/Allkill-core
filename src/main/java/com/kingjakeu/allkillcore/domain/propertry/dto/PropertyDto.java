package com.kingjakeu.allkillcore.domain.propertry.dto;

import com.kingjakeu.allkillcore.domain.propertry.domain.Property;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
@Getter
@Setter
@NoArgsConstructor
public class PropertyDto {
    private String id;
    private String value;

    public Property toEntity(){
        return Property.builder().propertyId(id).value(value).build();
    }
}
