package com.stone.generator.service;

import com.stone.generator.util.GeneratorUtil;
import com.stone.generator.config.GeneratorConfig;
import com.stone.generator.dao.SysGeneratorDao;
import com.stone.generator.pojo.ResultBean;
import com.stone.generator.pojo.info.ColumnInfo;
import com.stone.generator.pojo.vo.TableVO;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

import static com.stone.generator.pojo.ResultBean.SUCCESS;
import static com.stone.generator.pojo.ResultBean.SUCCESS_MESSAGE;

/**
 * Created by Stone on 2018/7/27.
 */
@Service
public class SysGeneratorService {

    @Autowired
    private GeneratorConfig config;
    @Autowired
    private SysGeneratorDao sysGeneratorDao;

    public ResultBean<List<TableVO>> listTable(Map<String, Object> paramMap) {
        ResultBean<List<TableVO>> resultBean = ResultBean.create();
        List<TableVO> tables = sysGeneratorDao.queryList(paramMap);
        return resultBean.setCode(SUCCESS).setMessage(SUCCESS_MESSAGE).setData(tables);
    }


    public ResultBean<Integer> countTalbe(Map<String, Object> paramMap) {
        ResultBean<Integer> resultBean = ResultBean.create();
        int count = sysGeneratorDao.queryTotal(paramMap);
        return resultBean.setCode(SUCCESS).setMessage(SUCCESS_MESSAGE).setData(count);
    }

    public byte[] download(Map<String, Object> paramMap) {

        ByteArrayOutputStream outputStream  = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        //查询表信息
        TableVO tableVO = sysGeneratorDao.queryTable(paramMap);
        //查询表列信息
        List<ColumnInfo> columnInfoList = sysGeneratorDao.queryColumns(paramMap);
        GeneratorUtil.generatorCode(tableVO,columnInfoList,paramMap,zip);
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }








}
