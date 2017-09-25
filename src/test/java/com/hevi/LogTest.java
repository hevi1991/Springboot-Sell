package com.hevi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Hevi on 2017/9/25.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LogTest {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Test
    public void test1() {

        String name = "Miki";
        String password = "!2#4%6";

        log.info("name:{},password:{}",name,password);
        log.error("Error:{}","ERRRRRRORRRR");


        /*
        ERROR(40, "ERROR"),
        WARN(30, "WARN"),
        INFO(20, "INFO"),
        DEBUG(10, "DEBUG"),
        TRACE(0, "TRACE");
        */
    }
}
