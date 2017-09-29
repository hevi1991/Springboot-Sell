package com.hevi.viewobejct;

import lombok.Data;

/**
 * http请求最外层对象
 */
@Data
public class ResultVO<T> {
    /**状态码*/
    private Integer code;
    /**提示信息*/
    private String msg;
    /**内容*/
    private T data;

    public ResultVO(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResultVO(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResultVO() {

    }
}
