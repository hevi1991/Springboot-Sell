package com.hevi.dto;

import lombok.Data;

/**
 * 购物车
 */
@Data
public class CartDTO {

    private String productId;
    private Integer productQuantity;

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
