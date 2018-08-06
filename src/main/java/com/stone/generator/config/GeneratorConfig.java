package com.stone.generator.config;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import static com.stone.generator.config.GeneratorConfig.PREFIX;

/**
 * Created by Stone on 2018/7/30.
 */
@Data
@Validated
@ConfigurationProperties(prefix = PREFIX)
public class GeneratorConfig {

    public static final String PREFIX = "spring.generator";

    /**
     * package name
     */
    @NotEmpty
    private String packageName;
    /**
     * module name
     */
    @NotEmpty
    private String moduleName;

}
