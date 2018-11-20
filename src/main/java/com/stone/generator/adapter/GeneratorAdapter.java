package com.stone.generator.adapter;

import com.stone.generator.pojo.info.GeneratorInfo;

/**
 * author : WH
 * time : 2018/11/13 2:11 PM
 */
public interface GeneratorAdapter extends Generator {

    void generatorCode(GeneratorInfo info);
}
