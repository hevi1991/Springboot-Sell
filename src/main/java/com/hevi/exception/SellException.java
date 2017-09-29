package com.hevi.exception;

import com.hevi.enums.ResultEnum;

/**
 * 自定义异常
 */
public class SellException extends RuntimeException{

    private Integer code;

    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());//传消息传给父类去初始化
        this.code = resultEnum.getCode();
    }
}
