package com.hevi.viewobejct;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * 商品详情，前端用
 */
@Data
public class ProductInfoVO {

    @JsonProperty("id")
    private String  productId;
    //名字
    @JsonProperty("name")
    private String  productName;
    //单价
    @JsonProperty("price")
    private BigDecimal productPrice;
    //描述
    @JsonProperty("description")
    private String  productDescription;
    //小图url
    @JsonProperty("icon")
    private String  productIcon;

}
