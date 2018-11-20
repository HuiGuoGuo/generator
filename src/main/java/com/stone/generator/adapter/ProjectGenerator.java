package com.stone.generator.adapter;

import com.google.common.base.Charsets;
import com.stone.generator.pojo.info.BaseInfo;
import com.stone.generator.pojo.info.GeneratorInfo;
import com.stone.generator.pojo.info.ProjectInfo;
import com.stone.generator.util.IMap;
import lombok.Data;
import org.apache.commons.io.IOUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * author : WH
 * time : 2018/11/13 4:40 PM
 */
@Data
public class ProjectGenerator extends AbstractProjectGenerator {

    private final static String[] ARRAY_MODULE = new String[]{"adapter", "assembly", "client", "common", "facade", "persist", "web"};

    private final static String[] ARRAY_PROFILE = new String[]{"local", "dev", "test", "online"};

    private final static String[] ARRAY_DIRECT = new String[]{"main", "test"};

    private final static String PREFIX = "templates/project/";

    private final static String PREFIX_CONFIG = "templates/project/assembly/";

    private final static String PREFIX_MYBATIS = "templates/project/persist/";

    /**
     * 可以配置模板
     */
    private String[] arrayModule;
    /**
     * 可以配置环境
     */
    private String[] arrayProfile;
    /**
     * 可以配置模板前缀
     */
    private String prefix;
    /**
     * 可以配置配置文件前缀
     */
    private String prefixConfig;
    /**
     * Mybatis-config前缀
     */
    private String prefixMybatis;


    public ProjectGenerator() {
        prefix = PREFIX;
        arrayModule = ARRAY_MODULE;
        arrayProfile = ARRAY_PROFILE;
        prefixConfig = PREFIX_CONFIG;
        prefixMybatis = PREFIX_MYBATIS;
    }

    @Override
    public List<String> initTemplate() {
        List<String> list = Arrays.stream(arrayModule).map(e -> prefix + e + XIE + "pom.xml.vm").collect(Collectors.toList());
        list.add(prefix + "pom.xml.vm");
        return list;
    }

    @Override
    public List<String> initYmlTemplate() {
        List<String> list = Arrays.stream(arrayProfile).map(e -> prefixConfig + "application" + HENG + e + ".yml.vm").collect(Collectors.toList());
        list.add(prefixConfig + "application.yml.vm");
        return list;
    }

    @Override
    public List<String> initDirectTemplate() {
        return Arrays.asList(arrayModule);
    }

    @Override
    public List<String> initApplicationTemplate() {
        return Arrays.asList(PREFIX_CONFIG + "application.java.vm");
    }

    @Override
    public List<String> initMybatisConfigTemplate() {
        return Arrays.asList(prefixMybatis + "mybatis-config.xml.vm");
    }

    @Override
    protected <T extends BaseInfo> String getDirectName(T info, String template, String type) {
        StringBuilder sb = new StringBuilder();
        ProjectInfo projectInfo = (ProjectInfo) info;
        String moduleName = projectInfo.getModuleName();
        String moduleNameLow = projectInfo.getModuleNameLow();
        String packageName = projectInfo.getGroupId();
        String packagePath = StringUtils.isEmpty(packageName) ? "" : packageName.replace(".", SE);
        sb
                .append(moduleName).append(SE).append(moduleName).append(HENG).append(template).append(SE)
                .append("src").append(SE).append(type).append(SE).append("java").append(SE).append(packagePath)
                .append(SE).append(moduleNameLow).append(SE).append(template).append(SE);
        return sb.toString();
    }

    @Override
    protected void generatorDirect(GeneratorInfo info, VelocityContext context, List<String> template) {
        ZipOutputStream zip = (ZipOutputStream) info.getOutputStream();
        Arrays.stream(ARRAY_DIRECT).forEach(t ->
                template.forEach(e -> {
                    try {
                        zip.putNextEntry(new ZipEntry(getDirectName(info.getInfo(), e, t)));
                        zip.closeEntry();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                })
        );
    }

    @Override
    protected <T extends BaseInfo> String getYmlName(T info, String template) {
        String prefix = "application";
        StringBuilder sb = new StringBuilder();
        ProjectInfo projectInfo = (ProjectInfo) info;
        String moduleName = projectInfo.getModuleName();
        sb
                .append(moduleName).append(SE).append(moduleName).append(HENG).append("assembly").append(SE).append("src").append(SE)
                .append("main").append(SE).append("resources").append(SE).append("config").append(SE);

        for (String s : arrayProfile) {
            if (template.contains(prefix + HENG + s)) {
                return sb.append(prefix).append(HENG).append(s).append(".").append("yml").toString();
            }
        }
        return sb.append(prefix).append(".").append("yml").toString();

    }

    @Override
    protected void generatorYml(GeneratorInfo info, VelocityContext context, List<String> template) {
        ZipOutputStream zip = (ZipOutputStream) info.getOutputStream();
        template.forEach(e -> {
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(e, StandardCharsets.UTF_8.name());
            tpl.merge(context, sw);
            try {
                zip.putNextEntry(new ZipEntry(getYmlName(info.getInfo(), e)));
                IOUtils.write(sw.toString(), zip, Charsets.UTF_8.toString());
                IOUtils.closeQuietly(sw);
                zip.closeEntry();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    @Override
    protected <T extends BaseInfo> String getPomName(T info, String template) {
        StringBuilder sb = new StringBuilder();
        ProjectInfo projectInfo = (ProjectInfo) info;
        String moduleName = projectInfo.getModuleName();
        for (String s : arrayModule) {
            if (template.contains(s + XIE + "pom.xml.vm")) {
                return sb.append(moduleName).append(SE).append(moduleName).append(HENG).append(s).append(SE).append("pom.xml").toString();
            }
        }
        return sb.append(moduleName).append(SE).append("pom.xml").toString();
    }

    @Override
    protected void generatorPom(GeneratorInfo info, VelocityContext context, List<String> template) {
        ZipOutputStream zip = (ZipOutputStream) info.getOutputStream();
        template.forEach(e -> {
            //渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(e, StandardCharsets.UTF_8.name());
            tpl.merge(context, sw);
            try {
                zip.putNextEntry(new ZipEntry(getPomName(info.getInfo(), e)));
                IOUtils.write(sw.toString(), zip, Charsets.UTF_8.toString());
                IOUtils.closeQuietly(sw);
                zip.closeEntry();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }


    @Override
    protected <T extends BaseInfo> String getApplicationName(T info, String template) {
        StringBuilder sb = new StringBuilder();
        ProjectInfo projectInfo = (ProjectInfo) info;
        String modeleNameUp = projectInfo.getModuleNameUp();
        String moduleNameLow = projectInfo.getModuleNameLow();
        String moduleName = projectInfo.getModuleName();
        String packageName = projectInfo.getGroupId();
        String packagePath = StringUtils.isEmpty(packageName) ? "" : packageName.replace(".", SE);
        sb
                .append(moduleName).append(SE).append(moduleName).append(HENG).append("assembly").append(SE).append("src")
                .append(SE).append("main").append(SE).append("java").append(SE);
        return sb.append(packagePath).append(SE).append(moduleNameLow).append(SE).append(modeleNameUp).append("Application.java").toString();
    }

    @Override
    protected void generatorApplication(GeneratorInfo info, VelocityContext context, List<String> template) {
        ZipOutputStream zip = (ZipOutputStream) info.getOutputStream();
        template.forEach(e -> {
            //渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(e, StandardCharsets.UTF_8.name());
            tpl.merge(context, sw);
            try {
                zip.putNextEntry(new ZipEntry(getApplicationName(info.getInfo(), e)));
                IOUtils.write(sw.toString(), zip, Charsets.UTF_8.toString());
                IOUtils.closeQuietly(sw);
                zip.closeEntry();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    @Override
    protected <T extends BaseInfo> String getMybatisConfigName(T info, String template) {
        StringBuilder sb = new StringBuilder();
        ProjectInfo projectInfo = (ProjectInfo) info;
        String moduleName = projectInfo.getModuleName();
        sb
                .append(moduleName).append(SE).append(moduleName).append(HENG).append("persist").append(SE).append("src")
                .append(SE).append("main").append(SE).append("resources").append(SE);
        return sb.append(SE).append("mybatis").append(SE).append("mybatis-config.xml").toString();
    }

    @Override
    protected void generatorMybatisConfig(GeneratorInfo info, VelocityContext context, List<String> template) {
        ZipOutputStream zip = (ZipOutputStream) info.getOutputStream();
        template.forEach(e -> {
            //渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(e, StandardCharsets.UTF_8.name());
            tpl.merge(context, sw);
            try {
                zip.putNextEntry(new ZipEntry(getMybatisConfigName(info.getInfo(), e)));
                IOUtils.write(sw.toString(), zip, Charsets.UTF_8.toString());
                IOUtils.closeQuietly(sw);
                zip.closeEntry();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    @Override
    protected <T extends BaseInfo> Map<String, String> paramMap(T info) {
        ProjectInfo projectInfo = (ProjectInfo) info;
        IMap<String, String> map = new IMap<>(4);
        return map
                .set("project-groupId", projectInfo.getGroupId())
                .set("project-artifactId", projectInfo.getArtifactId())
                .set("project-version", projectInfo.getVersion())
                .set("module-name", projectInfo.getModuleName())
                .set("module-name-up", projectInfo.getModuleNameUp())
                .set("module-name-lower", projectInfo.getModuleNameLow())
                .set("package-name", projectInfo.getGroupId())
                .set("author", "WH")
                .set("date", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
    }

}
