package com.hevi.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@DynamicUpdate
@Data
public class ProductInfo {
    /*
CREATE TABLE `product_info` (
  `product_id` varchar(32) NOT NULL COMMENT 'id',
  `product_name` varchar(64) NOT NULL COMMENT '商品名称',
  `product_price` decimal(8,2) NOT NULL COMMENT '商品单价',
  `product_stock` int(11) NOT NULL COMMENT '库存',
  `product_description` varchar(64) DEFAULT NULL COMMENT '描述',
  `product_icon` varchar(512) DEFAULT NULL COMMENT '小图片链接',
  `product_status` tinyint(3) DEFAULT '0' COMMENT '商品状态，0正常1下架',
  `category_type` int(11) NOT NULL COMMENT '类目编号',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间，更新时，自动刷新',
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品表';
    *
    * */
    @Id
    private String  productId;
    //名字
    private String  productName;
    //单价->对应sql decimal()类型
    private BigDecimal productPrice;
    //库存
    private Integer productStock;
    //描述
    private String  productDescription;
    //小图url
    private String  productIcon;
    //状态 0正常1下架
    private Integer productStatus;
    //类目编号
    private Integer categoryType;
}
