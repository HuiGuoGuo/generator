package com.stone.generator;

import com.google.common.base.CaseFormat;
import com.stone.generator.util.CamelUtil;
import org.junit.Test;

/**
 * author : WH
 * time : 2018/11/20 3:26 PM
 */
public class GuavaTest {

    @Test
    public void testUp(){

        String str = "Useraa";

        System.out.println(CamelUtil.castUpper(str));
        System.out.println(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_UNDERSCORE,str));
    }
}
