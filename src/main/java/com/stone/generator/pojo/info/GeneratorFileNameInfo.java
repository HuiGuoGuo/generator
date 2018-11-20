package com.stone.generator.pojo.info;

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.velocity.VelocityContext;

import java.util.List;

/**
 * author : WH
 * time : 2018/11/19 3:40 PM
 */
@Data
@Accessors(chain = true)
public class GeneratorFileNameInfo<T extends BaseInfo> extends GeneratorInfo {

    private VelocityContext context;

    private List<String> template;
}
