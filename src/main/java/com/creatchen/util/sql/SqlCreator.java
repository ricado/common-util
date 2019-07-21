package com.creatchen.util.sql;


import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

public class SqlCreator extends SqlVO{

    private final static String SELECT = " select ";
    private final static String FROM = " from ";
    private final static String WHERE = " where ";
    private final static String LIMIT = " limit ";
    private final static String OFFSET = " offset ";
    private final static String GROUP_BY = " group by ";
    private final static String ORDER_BY = " order by ";
    private final static String HAVING = " having ";

    private final static String PUNCTUATION_COMMA = ",";
    private final static String PUNCTUATION_DOT = ".";
    private final static String PUNCTUATION_SEMICOLON = ";";



    public String create(){
        check();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(SELECT).append(getSelectSql())
                .append(FROM).append(getDatabaseTableName())
                .append(getWhereSql())
                .append(getGroupBySql())
                .append(getHavingSql())
                .append(getOrderBySql());
        if(null != getLimit()){
            stringBuffer.append(LIMIT).append(getLimit());
            if(null != getOffset()){
                stringBuffer.append(PUNCTUATION_COMMA).append(getOffset());
            }
        }
        stringBuffer.append(PUNCTUATION_SEMICOLON);
        return stringBuffer.toString();
    }

    private String getSelectSql(){
        return joinListString(getSelectSqlList());
    }

    private String getDatabaseTableName(){
        if(Strings.isNullOrEmpty(getDatabaseName())){
            return getTableName();
        }
        return getDatabaseName() + PUNCTUATION_DOT + getTableName();
    }

    private String getWhereSql(){
        if(CollectionUtils.isEmpty(getWhereSqlList())){
            return "";
        }
        return WHERE + joinListString(getWhereSqlList());
    }

    private String getGroupBySql(){
        if(CollectionUtils.isEmpty(getGroupBySqlList())){
            return "";
        }
        return GROUP_BY + joinListString(getGroupBySqlList());
    }

    private String getOrderBySql(){
        if(CollectionUtils.isEmpty(getOrderBySqlList())){
            return "";
        }
        return ORDER_BY + joinListString(getOrderBySqlList());
    }

    private String getHavingSql(){
        if(CollectionUtils.isEmpty(getHavingSqlList())){
            return "";
        }
        return HAVING + joinListString(getHavingSqlList());
    }

    private String joinListString(List<String> list){
        return " " + Joiner.on(",").skipNulls().join(list) + " ";
    }

    /**
     * 检查是否缺少必要的sql条件
     */
    public void check(){
        if(Strings.isNullOrEmpty(getTableName())){
            throw new RuntimeException("表名为空");
        }

        if(CollectionUtils.isEmpty(getSelectSqlList())){
            throw new RuntimeException("select 为空");
        }

        if(CollectionUtils.isEmpty(getGroupBySqlList()) && !CollectionUtils.isEmpty(getHavingSqlList())){
            throw new RuntimeException("没有group by语句不能添加having语句");
        }
    }

}
