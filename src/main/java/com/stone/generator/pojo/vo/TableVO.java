package com.stone.generator.pojo.vo;

import lombok.Data;

import java.util.Date;

/**
 * Created by Stone on 2018/7/27.
 */
@Data
public class TableVO {

    private String tableName;

    private String engine;

    private String tableComment;

    private Date createTime;

}
