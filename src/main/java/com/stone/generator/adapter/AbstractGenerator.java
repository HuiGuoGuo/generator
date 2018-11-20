package com.stone.generator.adapter;

import com.stone.generator.pojo.info.BaseInfo;
import com.stone.generator.pojo.info.GeneratorInfo;
import com.stone.generator.pojo.info.ProjectInfo;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.File;
import java.io.Serializable;
import java.util.Map;
import java.util.Properties;

/**
 * author : WH
 * time : 2018/11/20 1:44 PM
 */
public abstract class AbstractGenerator implements GeneratorAdapter, Serializable {

    private static final long serialVersionUID = -5081428383341464782L;

    protected static final String SE = File.separator;

    protected static final String HENG = "-";

    protected static final String XIE = "/";

    static {
        Properties p = new Properties();
        p.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(p);
    }

    @Override
    public void generatorCode(GeneratorInfo info) {
        ProjectInfo projectInfo = (ProjectInfo) info.getInfo();
        Map<String, String> paramMap = paramMap(projectInfo);
        VelocityContext context = new VelocityContext(paramMap);
        mergeText(info, context);
    }

    protected abstract void mergeText(GeneratorInfo info, VelocityContext context);

    protected abstract <T extends BaseInfo> Map<String, String> paramMap(T info);
}
