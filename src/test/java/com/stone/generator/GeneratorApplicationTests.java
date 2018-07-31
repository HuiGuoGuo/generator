package com.stone.generator;

import com.google.common.base.CaseFormat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Method;
import java.sql.Types;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class GeneratorApplicationTests {

    @Test
    public void test()  throws Exception{
        Class clazz = Class.forName("com.stone.generator.JDBCTypesUtils");
        Object o = clazz.newInstance();
        Method method = clazz.getMethod("getJdbcCode",String.class);
        Object result = method.invoke(o, "int");
        System.out.println(result);
//        System.out.println(anInt);
    }
}
