package com.stone.generator.pojo;

import com.stone.generator.pojo.request.ListTableRequestDTO;
import lombok.Data;

import java.util.LinkedHashMap;

/**
 * Created by Stone on 2018/7/27.
 */
@Data
public class Query extends LinkedHashMap<String, Object> {

    private Integer page;

    private Integer pageSize;

    public Query(ListTableRequestDTO requestDTO) {
        this.put("tableName", requestDTO.getTableName());
        this.page = requestDTO.getPage() ;
        this.pageSize = requestDTO.getPageSize() ;
        this.put("offset", (page - 1) * pageSize);
        this.put("page", page);
        this.put("limit", pageSize);
    }
}
