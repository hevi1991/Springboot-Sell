package com.hevi.enums;

import lombok.Getter;

/**
 * 商品状态，上架或下架
 */
@Getter
public enum ProductStatus {

    UP(0,"上架"),
    DOWN(1,"下架");

    private Integer code;
    private String message;

    ProductStatus(Integer code, String message) {
        this.code = code;
    }

}
