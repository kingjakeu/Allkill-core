package com.kingjakeu.allkillcore.common.enums;

import lombok.Getter;

@Getter
public enum CrawlQuery {

    DEPARTMENT_INFO("SELECT#openSust"),
    COURSE_INFO("tr.table_bg_white"),
    CAPACITY_INFO("td.table_bg_white")
    ;

    private String query;

    CrawlQuery(String query){
        this.query = query;
    }
}
