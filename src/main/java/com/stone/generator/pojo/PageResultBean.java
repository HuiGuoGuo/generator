package com.stone.generator.pojo;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by Stone on 2018/7/27.
 */
@Data
@Accessors(chain = true)
public class PageResultBean<T> extends ResultBean<T> {

    private Integer pages;

    private Integer total;

    private Integer page;

    public static PageResultBean create() {
        PageResultBean pageResultBean = new PageResultBean();
        pageResultBean.setCode(SUCCESS).setMessage(SUCCESS_MESSAGE);
        return pageResultBean;
    }

}
