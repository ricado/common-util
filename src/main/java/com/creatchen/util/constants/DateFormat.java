package com.creatchen.util.constants;

/**
 * @author creatchen
 * @version 1.0
 * @date 2018/12/15
 */
public enum DateFormat {
    YYYY_MM_DD_HH_MM_SS("yyyy-MM-dd HH:mm:ss"),
    YYYY_MM_DD_HH_MM("yyyy-MM-dd HH:mm"),
    YYYY_MM_DD("yyyy-MM-dd"),
    YYYY("yyyy"),
    MM("MM"),
    DD("dd"),
    ;

    private String desc;

    DateFormat(String desc) {
        this.desc = desc;
    }

    public String desc() {
        return this.desc;
    }

    @Override
    public String toString() {
        return desc();
    }
}
