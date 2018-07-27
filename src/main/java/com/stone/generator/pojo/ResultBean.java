package com.stone.generator.pojo;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by Stone on 2018/7/27.
 */
@Data
@Accessors(chain = true)
public class ResultBean<T> {

    private String code;

    private String message;

    private T data;

    public static final String SUCCESS = "0";

    public static final String FAILED = "-1";

    public static final String SUCCESS_MESSAGE= "调用成功";

    public static ResultBean create(){
        ResultBean resultBean = new ResultBean();
        return resultBean.setCode(SUCCESS).setMessage(SUCCESS_MESSAGE);
    }
}
