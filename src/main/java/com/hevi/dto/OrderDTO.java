package com.hevi.dto;

import com.hevi.dataobject.OrderDetail;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 专门处理数据与对象结构的类
 * DTO:Data Transfer Object
 * 联合:OrderMaster + OrderDetail
 */
@Data
public class OrderDTO {

    private String orderId;

    //买家姓名
    private String buyerName;

    //买家电话
    private String buyerPhone;

    //买家地址
    private String buyerAddress;

    //买家微信Openid
    private String buyerOpenid;

    //订单总金额
    private BigDecimal orderAmount;

    //订单状态 默认0新订单
    private Integer orderStatus;

    //支付状态 默认0未支付
    private Integer payStatus;

    //创建时间
    private Date createTime;

    //更新时间
    private Date updateTime;

    //OrderDetail和OrderMaster数据是关联的,放在一起好处理
    List<OrderDetail> orderDetailList;
}
