package com.stone.generator.pojo.info;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.OutputStream;

/**
 * author : WH
 * time : 2018/11/19 10:17 AM
 */
@Data
@Accessors(chain = true)
public class GeneratorInfo<T extends BaseInfo> {

    private T info ;

    private OutputStream outputStream;

}
