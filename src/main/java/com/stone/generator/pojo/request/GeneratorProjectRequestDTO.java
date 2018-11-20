package com.stone.generator.pojo.request;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * author : WH
 * time : 2018/11/19 3:55 PM
 */

@Data
@Accessors(chain = true)
public class GeneratorProjectRequestDTO implements Serializable {

    private static final long serialVersionUID = -739017512750057981L;

    private String groupId;

    private String artifactId;

    private String version;

    private String moduleName;
}
