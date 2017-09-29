package com.hevi.utils;

import java.util.Random;

public class KeyUtil {

    /**
     * 生成唯一的主键
     * 格式:时间+随机数
     * @return
     */
    public static synchronized String genUniqueKey(){//synchronized 写随机数,使用单线程
        //生成六位随机数
        Random random = new Random();
        Integer number = random.nextInt(900000)+100000;
        return System.currentTimeMillis() + String.valueOf(number);
    }
}
