package com.stone.generator.pojo.info;

import lombok.Data;

import java.util.List;

/**
 * author : WH
 * time : 2018/11/19 10:07 AM
 */
@Data
public class ProjectInfo extends BaseInfo {

    private String version;

    private String moduleName;

    private String moduleNameUp;

    private String moduleNameLow;

    private List<String> profiles;
}
