package com.stone.generator.dao;

import com.stone.generator.pojo.info.ColumnInfo;
import com.stone.generator.pojo.vo.TableVO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Stone on 2018/7/27.
 */
@Repository
public interface SysGeneratorDao {

    List<TableVO> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    TableVO queryTable(Map<String, Object> map);

    List<ColumnInfo> queryColumns(Map<String, Object> map);
}
