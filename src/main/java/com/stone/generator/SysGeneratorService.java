package com.stone.generator;

import com.stone.generator.dao.SysGeneratorDao;
import com.stone.generator.pojo.ResultBean;
import com.stone.generator.pojo.vo.TableVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.stone.generator.pojo.ResultBean.SUCCESS;
import static com.stone.generator.pojo.ResultBean.SUCCESS_MESSAGE;

/**
 * Created by Stone on 2018/7/27.
 */
@Service
public class SysGeneratorService {

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
}
