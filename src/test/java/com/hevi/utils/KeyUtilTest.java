package com.hevi.utils;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class KeyUtilTest {
    @Test
    public void genUniqueKey() throws Exception {
        String number = KeyUtil.genUniqueKey();
        Assert.assertNotEquals(0,number.length());
        System.out.println(number);
    }

}