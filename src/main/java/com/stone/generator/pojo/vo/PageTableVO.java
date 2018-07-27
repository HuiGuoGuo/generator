package com.stone.generator.pojo.vo;

import lombok.Data;

import java.util.List;

/**
 * Created by Stone on 2018/7/27.
 */
@Data
public class PageTableVO  {

    /**
     * 当前页
     */
    private Integer currPage;
    /**
     * 总页码
     */
    private Integer pages;
    /**
     * 每页大小
     */
    private Integer pageSize;
    /**
     * 总条数
     */
    private Integer totals;
    /**
     * 数据
     */
    private List<?> data;

    public PageTableVO(Integer currPage,Integer pageSize,Integer totals, List<?> data) {
        this.currPage = currPage;
        this.totals = totals;
        this.data = data;
        this.pageSize = pageSize;
        this.pages = (int)Math.ceil((double)totals/pageSize);
    }
}
