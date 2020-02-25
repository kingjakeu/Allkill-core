package com.kingjakeu.allkillcore.domain.propertry.api;

import com.kingjakeu.allkillcore.domain.propertry.dao.PropertyRepository;
import com.kingjakeu.allkillcore.domain.propertry.domain.Property;
import com.kingjakeu.allkillcore.domain.propertry.dto.PropertyDto;
import com.kingjakeu.allkillcore.util.SlackSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

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
@Slf4j
@RestController
@RequestMapping("/property")
public class PropertyApi {

    private PropertyRepository propertyRepository;

    @Autowired
    public PropertyApi(PropertyRepository propertyRepository){
        this.propertyRepository = propertyRepository;
    }

    @PostMapping("/register")
    public void registerPropertyInfo(@RequestBody PropertyDto propertyDto){
        propertyRepository.save(propertyDto.toEntity());
    }

    @PostMapping("/slack")
    public void sendTestSlackMessage(){
        Optional<Property> slackUrl = propertyRepository.findById("SLACK");
        SlackSender slackSender = new SlackSender(slackUrl.isPresent() ? slackUrl.get().getValue() : "");
        slackSender.sendMessage(" 테스트 입니다 ");
    }
}
