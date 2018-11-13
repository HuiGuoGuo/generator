package com.stone.generator;

import org.junit.Test;

import java.lang.reflect.Method;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class GeneratorApplicationTests {

    @Test
    public void test()  throws Exception{
        String format = String.format("attachment; filename=%s%s", System.currentTimeMillis(), ".xlsx");
        System.out.println(format);
//        System.out.println(anInt);
    }
}
