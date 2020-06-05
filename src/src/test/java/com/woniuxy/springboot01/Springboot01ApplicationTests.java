package com.woniuxy.springboot01;

import com.woniuxy.springboot01.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class Springboot01ApplicationTests {

    @Autowired
    ApplicationContext ac;

    @Autowired
    UserMapper userMapper;

    @Test
    void contextLoads() {
        System.out.println(ac.getBeanDefinitionNames());
    }

}
