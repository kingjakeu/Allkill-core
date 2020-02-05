package com.kingjakeu.allkillcore.common.enums;

import lombok.Getter;

@Getter
public enum CrawlQuery {

    DEPARTMENT_INFO("SELECT#openSust")
    ;

    private String query;

    CrawlQuery(String query){
        this.query = query;
    }
}
