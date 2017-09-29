package com.hevi.utils;

import com.hevi.viewobejct.ResultVO;

public class ResultVOUtil {

    /**
     * 有数据的成功响应
     * @param object
     * @return
     */
    public static ResultVO success(Object object){
        return new ResultVO(0,"成功",object);
    }

    /**
     * 没数据的成功响应
     * @return
     */
    public static ResultVO success(){
        return success(null);
    }

    /**
     * 失败响应
     * @param code 错误代码
     * @param msg 提示信息
     * @return
     */
    public static ResultVO error(Integer code, String msg){
        return new ResultVO(code,msg);
    }
}
