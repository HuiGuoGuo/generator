package com.stone.generator;

import org.junit.Test;

import java.lang.reflect.Method;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class GeneratorApplicationTests {

    @Test
    public void test()  throws Exception{
        Class clazz = Class.forName("com.stone.generator.util.JDBCTypesUtils");
        Object o = clazz.newInstance();
        Method method = clazz.getMethod("getJdbcCode",String.class);
        Object result = method.invoke(o, "int");
        System.out.println(result);
//        System.out.println(anInt);
    }
}
