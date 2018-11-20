package com.stone.generator.pojo.info;

import lombok.Data;

import java.io.Serializable;

/**
 * author : WH
 * time : 2018/11/19 10:02 AM
 */
@Data
public class BaseInfo implements Serializable {

    private static final long serialVersionUID = 7778498204165340478L;

    private String groupId;

    private String artifactId;

}
