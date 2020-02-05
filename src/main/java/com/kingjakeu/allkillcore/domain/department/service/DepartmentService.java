package com.kingjakeu.allkillcore.domain.department.service;

import com.kingjakeu.allkillcore.common.enums.CrawlQuery;
import com.kingjakeu.allkillcore.common.enums.ServerUrl;
import com.kingjakeu.allkillcore.domain.department.domain.Department;
import com.kingjakeu.allkillcore.util.Crawler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Slf4j
@Service
public class DepartmentService {

    public List<Department> crawlDepartmentInfoFromSource(){
        Crawler crawler = new Crawler(ServerUrl.DEPARTMENT_INFO.getUrl());
        Elements elements = crawler.crawl().getElementsByCssQuery(CrawlQuery.DEPARTMENT_INFO.getQuery());
        elements = elements.select("OPTION");

        List<Department> departments = new LinkedList<>();
        String key = "value";
        for(Element element : elements){
            Department department = Department.builder()
                    .id(element.attr(key))
                    .name(element.text())
                    .build();
            if(StringUtils.isNotBlank(department.getId())) departments.add(department);
            //log.info(department.toString());
        }
        return departments;
    }
}
