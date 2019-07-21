package com.creatchen.util.sql;



import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
public class SqlVO {
    /**
     * 查询语句
     */
    private List<String> selectSqlList;

    /**
     * where语句
     */
    private List<String> whereSqlList;

    /**
     * having语句
     */
    private List<String> havingSqlList;

    /**
     * group by语句
     */
    private List<String> groupBySqlList;

    /**
     * order by 语句
     */
    private List<String> orderBySqlList;

    /**
     * 数据库名
     */
    private String databaseName;

    /**
     * 表名
     */
    private String tableName;

    /**
     * 限制条数
     */
    private Integer limit;

    /**
     * 起始位置
     */
    private Integer offset;

    public SqlVO addSelectSqls(String... sqls){
        return addSelectSqls(Lists.newArrayList(sqls));
    }

    public SqlVO addWhereSqls(String... sqls){
        return addWhereSqls(Lists.newArrayList(sqls));
    }

    public SqlVO addHavingSqls(String... sqls){
        return addHavingSqls(Lists.newArrayList(sqls));
    }

    public SqlVO addGroupBySqls(String... sqls){
        return addGroupBySqls(Lists.newArrayList(sqls));
    }

    public SqlVO addOrderBySqls(String... sqls){
        return addOrderBySqls(Lists.newArrayList(sqls));
    }

    public SqlVO addSelectSqls(Collection<String> sqls){
        selectSqlList = add(selectSqlList,sqls);
        return this;
    }

    public SqlVO addWhereSqls(Collection<String> sqls){
         whereSqlList = add(whereSqlList,sqls);
        return this;
    }

    public SqlVO addHavingSqls(Collection<String> sqls){
        havingSqlList = add(havingSqlList,sqls);
        return this;
    }

    public SqlVO addGroupBySqls(Collection<String> sqls){
        groupBySqlList = add(groupBySqlList,sqls);
        return this;
    }

    public SqlVO addOrderBySqls(Collection<String> sqls){
        orderBySqlList = add(orderBySqlList,sqls);
        return this;
    }

    public SqlVO limitOffset(int limit,int offset){
        this.limit = limit;
        this.offset = offset;
        return this;
    }

    public SqlVO setTableName(String databaseName,String tableName){
        this.databaseName = databaseName;
        this.tableName = tableName;
        return this;
    }

    /**
     * 添加
     * @param list list
     * @param values values
     * @return list
     */
    private List<String> add(List<String> list,Collection<String> values){
        if(CollectionUtils.isEmpty(list)){
            list =  new ArrayList<>();
        }
        for (String value : values) {
            if(Strings.isNullOrEmpty(value) || list.contains(value)){
                continue;
            }
            list.add(value);
        }
        return list;
    }

}
