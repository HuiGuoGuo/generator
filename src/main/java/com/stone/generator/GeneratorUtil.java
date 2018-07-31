package com.stone.generator;

import com.google.common.base.CaseFormat;
import com.google.common.base.Charsets;
import com.stone.generator.config.GeneratorConfig;
import com.stone.generator.pojo.info.ColumnInfo;
import com.stone.generator.pojo.vo.TableVO;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import javax.validation.constraints.NotEmpty;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static com.stone.generator.JDBCTypesUtils.getJdbcCode;

/**
 * Created by Stone on 2018/7/30.
 */
public final class GeneratorUtil {


    public static List<String> initTemplate() {
        List<String> templates = new ArrayList<>(6);
//        templates.add("/templates/controller.java.vm" );
//        templates.add("/templates/service.java.vm" );
//        templates.add("/templates/serviceImpl.java.vm" );
//        templates.add("/templates/mapper.java.vm" );
        templates.add("templates/model.java.vm");
        templates.add("templates/mapper.xml.vm");
        return templates;
    }


    public static void generatorCode(TableVO tableInfo, List<ColumnInfo> columnInfoList, GeneratorConfig config,
                                     ZipOutputStream zip) {
        String className = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableInfo.getTableName());
        List<ColumnInfo> columnInfos = parseColumnInfo(columnInfoList);
        @NotEmpty String packageName = config.getPackageName();
        @NotEmpty String moduleName = config.getModuleName();
        //封装参数,表信息
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("packageName", packageName);
        paramMap.put("moduleName", moduleName);
        paramMap.put("tableName", tableInfo.getTableName());
        paramMap.put("className", className);
        paramMap.put("columns", columnInfos);
        Properties p = new Properties();
        p.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(p);
        VelocityContext context = new VelocityContext(paramMap);
        List<String> templates = initTemplate();
        for (String template : templates) {
            //渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, Charsets.UTF_8.toString());
            tpl.merge(context, sw);
            try {
                zip.putNextEntry(new ZipEntry(gerFileName(template, className, packageName, moduleName)));
                IOUtils.write(sw.toString(), zip, Charsets.UTF_8.toString());
                IOUtils.closeQuietly(sw);
                zip.closeEntry();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    /**
     * 生成column相关信息
     *
     * @param columnInfoList
     */
    public static List<ColumnInfo> parseColumnInfo(List<ColumnInfo> columnInfoList) {
        for (ColumnInfo columnInfo : columnInfoList) {
            String columnName = columnInfo.getColumnName();
            String dataType = columnInfo.getDataType().equals("int") ? "integer" : columnInfo.getDataType();
            columnInfo.setDataType(dataType.toUpperCase(Locale.CHINA));
            columnInfo.setProperty(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, columnName));
            columnInfo.setPropertyUp(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, columnName));
            columnInfo.setJavaType(JDBCTypesUtils.jdbcTypeToJavaType(getJdbcCode(dataType.toUpperCase())).getName());
        }
        return columnInfoList;
    }

    public static String gerFileName(String template, String className, String packageName, String moduleName) {
        String classPath = "";
        String basePath = "src" + File.separator + "main" + File.separator + "java" + File.separator;
        if (StringUtils.isNotBlank(packageName)) {
            classPath = basePath + packageName.replace(".", File.separator) + File.separator + moduleName + File.separator;
        }
        if (template.contains("controller.java.vm")) {
            return classPath + "controller" + File.separator + className + "Controller.java";
        } else if (template.contains("service.java.vm")) {
            return classPath + "service" + File.separator + className + "Service.java";
        } else if (template.contains("serviceImpl.java.vm")) {
            return classPath + "service" + File.separator + "impl" + File.separator + className + "ServiceImpl.java";
        } else if (template.contains("mapper.java.vm")) {
            return classPath + "mapper" + File.separator + className + "Mapper.java";
        } else if (template.contains("model.java.vm")) {
            return classPath + "entity" + File.separator + className + ".java";
        } else if (template.contains("mapper.xml.vm")) {
            return basePath + "resources" + File.separator + "mapper" + File.separator + className + "Mapper.xml";
        }
        return null;

    }


}
