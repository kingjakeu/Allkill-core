package com.kingjakeu.allkillcore.common.enums;

import lombok.Getter;

@Getter
public enum ServerUrl {

    DEPARTMENT_INFO("https://kupis.konkuk.ac.kr/sugang/acd/cour/time/SeoulTimetableInfo.jsp"),
    COURSE_INFO("https://kupis.konkuk.ac.kr/sugang/acd/cour/time/SeoulTimetableInfo.jsp?ltYy="+ConstString.YEAR+"&ltShtm="+ConstString.SEMESTER+"&openSust="),
    COURSE_INFO_TYPE_OPTION("&pobtDiv="),
    CAPACITY_INFO("https://kupis.konkuk.ac.kr/sugang/acd/cour/aply/CourInwonInqTime.jsp?ltYy="+ ConstString.YEAR+"&ltShtm="+ConstString.SEMESTER+"&sbjtId="),
    LOGIN("https://kupis.konkuk.ac.kr/sugang/login/loginBtm.jsp?task=f_CourUserLogin&oldStdNo=&ltYy="+ ConstString.YEAR +"&ltShtm="+ ConstString.SEMESTER +"&campFg=1&stdNo="),
    LOGIN_OPTION_PASSWORD("&pwd="),
    LOGIN_OPTION_END("&idPassGubun=1"),
    SUGANG("https://kupis.konkuk.ac.kr/sugang/acd/cour/aply/courLessinApplyReg.jsp?strSaveCheck=Y&strBrowser=chrome&strSbjtId="),
    SUGANG_OPTION_END("&strKcuCount=0&CuriCdtWarnFg=11.0&MinCuriCnt=1&CuriCnt=4&CuriCdt=11.0&CuriMax=21&CuriAdd=0&PreSngj=3.85&Schdiv=1&strCorsNm=&strDeptCd=&strMultPobtDiv01="),
    ;

    private String url;

    ServerUrl(String url){
        this.url = url;
    }

    public static class ConstString{
        public static final String YEAR = "2020";
        public static final String SEMESTER = "B01011";
    }
}
