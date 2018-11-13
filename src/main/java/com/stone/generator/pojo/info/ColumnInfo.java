package com.stone.generator.pojo.info;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by Stone on 2018/7/30.
 */
@Data
@Accessors(chain = true)
public class ColumnInfo {
    /**
     * 字段名称
     */
    private String columnName;
    /**
     * 字段对应驼峰名称
     */
    private String property;
    /**
     * 字段对应驼峰名称，首字段大写
     */
    private String propertyUp;
    /**
     * 字段Java类型
     */
    private String javaType;
    /**
     * 字段jdbc类型
     */
    private String dataType;
    /**
     * 字段说明,描述
     */
    private String columnComment;
    /**
     * 主键说明
     */
    private String columnKey;
    /**
     * 其它的
     */
    private String extra;
}
