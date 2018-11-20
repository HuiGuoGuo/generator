package com.stone.generator.util;

import com.google.common.base.CaseFormat;
import com.google.common.base.Charsets;
import com.stone.generator.pojo.info.ColumnInfo;
import com.stone.generator.pojo.vo.TableVO;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.beans.BeanUtils;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static com.stone.generator.util.JDBCTypesUtils.getJdbcCode;

/**
 * Created by Stone on 2018/7/30.
 */
public final class GeneratorUtil {


    public static List<String> initTemplate() {
        List<String> templates = new ArrayList<>(6);
        templates.add("templates/controller.java.vm");
        templates.add("templates/service.java.vm");
        templates.add("templates/serviceImpl.java.vm");
        templates.add("templates/mapper.java.vm");
        templates.add("templates/entity.java.vm");
        templates.add("templates/mapper.xml.vm");
        return templates;
    }


    public static void generatorCode(TableVO tableInfo, List<ColumnInfo> columnInfoList, Map<String, Object> config,
                                     ZipOutputStream zip) {
        ColumnInfo pk = new ColumnInfo();
        String tableName = tableInfo.getTableName();
        String className = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableName);
        String classname = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, tableName);
        List<ColumnInfo> columnInfos = parseColumnInfo(columnInfoList, pk);
        String packageName = (String) config.get("groupId");
        String moduleName = (String) config.get("artifactId");
        //封装参数,表信息
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("packageName", packageName);
        paramMap.put("moduleName", moduleName);
        paramMap.put("tableName", tableName);
        paramMap.put("className", className);
        paramMap.put("classname", classname);
        paramMap.put("columns", columnInfos);
        paramMap.put("pk", pk);
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
                String $1 = sw.toString().replace("[ ]*(#if|#else|#elseif|#end|#set|#foreach)", "$1");
                IOUtils.write($1, zip, Charsets.UTF_8.toString());
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
    public static List<ColumnInfo> parseColumnInfo(List<ColumnInfo> columnInfoList, ColumnInfo pk) {
        for (ColumnInfo columnInfo : columnInfoList) {
            String columnKey = columnInfo.getColumnKey();
            String columnName = columnInfo.getColumnName();
            String dataType = columnInfo.getDataType().equals("int") ? "integer" : columnInfo.getDataType();
            columnInfo.setDataType(dataType.toUpperCase(Locale.CHINA));
            columnInfo.setProperty(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, columnName));
            columnInfo.setPropertyUp(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, columnName));
            columnInfo.setJavaType(JDBCTypesUtils.jdbcTypeToJavaType(getJdbcCode(dataType.toUpperCase())).getName());
            //设置主键
            if ("PRI".equalsIgnoreCase(columnKey) && pk.getColumnName() == null) {
                BeanUtils.copyProperties(columnInfo, pk);
            }
        }
        //没有主键则默认为第一列
        if (pk.getColumnName() == null) {
            BeanUtils.copyProperties(columnInfoList.get(0), pk);
        }
        return columnInfoList;
    }

    /**
     * @param template
     * @param className
     * @param packageName
     * @param moduleName
     * @return
     */
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
        } else if (template.contains("entity.java.vm")) {
            return classPath + "entity" + File.separator + className + ".java";
        } else if (template.contains("mapper.xml.vm")) {
            return basePath + "resources" + File.separator + "mapper" + File.separator + className + "Mapper.xml";
        }
        return null;

    }


}
