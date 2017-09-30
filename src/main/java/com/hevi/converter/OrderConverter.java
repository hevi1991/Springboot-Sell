package com.hevi.converter;

import com.hevi.dataobject.OrderDetail;
import com.hevi.dataobject.OrderMaster;
import com.hevi.dto.OrderDTO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class OrderConverter {

    public static OrderDTO convert(OrderMaster orderMaster, List<OrderDetail> orderDetailList){
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        if (orderDTO.getOrderDetailList() != null && orderDetailList != null){
            orderDTO.setOrderDetailList(orderDetailList);
        }
        return orderDTO;
    }

    public static List<OrderDTO> convert(List<OrderMaster> orderMasterList){
        return orderMasterList.stream().map(om->convert(om,null)).collect(Collectors.toList());
    }
}
