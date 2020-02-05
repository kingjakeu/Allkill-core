package com.kingjakeu.allkillcore.common.enums;

import lombok.Getter;

@Getter
public enum ServerUrl {

    DEPARTMENT_INFO("https://kupis.konkuk.ac.kr/sugang/acd/cour/time/SeoulTimetableInfo.jsp"),
    COURSE_INFO("https://kupis.konkuk.ac.kr/sugang/acd/cour/time/SeoulTimetableInfo.jsp?ltYy="+ConstString.YEAR+"&ltShtm="+ConstString.SEMESTER+"&openSust="),
    CAPACITY_INFO("https://kupis.konkuk.ac.kr/sugang/acd/cour/aply/CourInwonInqTime.jsp?ltYy="+ ConstString.YEAR+"&ltShtm="+ConstString.SEMESTER+"&sbjtId=")
    ;

    private String url;

    ServerUrl(String url){
        this.url = url;
    }

    public static class ConstString{
        public static final String YEAR = "2020";
        public static final String SEMESTER = "B01010";
    }
}
