package com.stone.generator.pojo.request;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Stone on 2018/7/27.
 */
@Data
public class BaseRequestDTO implements Serializable {

    private static final long serialVersionUID = 663149078297706159L;

    private Integer page = 1;

    private Integer pageSize = 10;
}
