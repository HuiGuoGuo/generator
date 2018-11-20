package com.stone.generator.adapter;

import com.stone.generator.pojo.info.BaseInfo;
import com.stone.generator.pojo.info.GeneratorInfo;
import org.apache.velocity.VelocityContext;

import java.util.List;

/**
 * author : WH
 * time : 2018/11/13 2:17 PM
 */
public abstract class AbstractProjectGenerator extends AbstractGenerator {

    private static final long serialVersionUID = 7703397293274124896L;

    protected void mergeText(GeneratorInfo info, VelocityContext context) {
        generatorPom(info, context, initTemplate());
        generatorYml(info, context, initYmlTemplate());
        generatorDirect(info, context, initDirectTemplate());
        generatorApplication(info, context, initApplicationTemplate());
        generatorMybatisConfig(info, context, initMybatisConfigTemplate());
    }

    public List<String> initTemplate() {
        return null;
    }

    public List<String> initYmlTemplate() {
        return null;
    }

    public List<String> initDirectTemplate() {
        return null;
    }

    public List<String> initApplicationTemplate() {
        return null;
    }

    public List<String> initMybatisConfigTemplate() {
        return null;
    }

    protected <T extends BaseInfo> String getDirectName(T info, String template, String type) {
        return null;
    }

    protected void generatorDirect(GeneratorInfo info, VelocityContext context, List<String> template) {
        return;
    }

    protected <T extends BaseInfo> String getYmlName(T info, String template) {
        return null;
    }

    protected void generatorYml(GeneratorInfo info, VelocityContext context, List<String> template) {
        return;
    }

    protected <T extends BaseInfo> String getPomName(T info, String template) {
        return null;
    }

    protected void generatorPom(GeneratorInfo info, VelocityContext context, List<String> template) {
        return;
    }

    protected <T extends BaseInfo> String getApplicationName(T info, String template) {
        return null;
    }

    protected void generatorApplication(GeneratorInfo info, VelocityContext context, List<String> template) {
        return;
    }

    protected <T extends BaseInfo> String getMybatisConfigName(T info, String template) {
        return null;
    }

    protected void generatorMybatisConfig(GeneratorInfo info, VelocityContext context, List<String> template) {
        return;
    }


}
