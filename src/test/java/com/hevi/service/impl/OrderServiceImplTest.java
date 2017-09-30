package com.hevi.service.impl;

import com.hevi.dataobject.OrderDetail;
import com.hevi.dto.OrderDTO;
import com.hevi.enums.OrderStatusEnum;
import com.hevi.enums.PayStatusEnum;
import com.hevi.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {

    @Autowired
    OrderService orderService;

    private final String OPENID = "wx123456789";
    private final String ORDERID = "1506678308734624584";
    @Test
    public void create() throws Exception {

        List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProductId("123459");
        orderDetail.setProductQuantity(1);
        orderDetailList.add(orderDetail);

        orderDetail = new OrderDetail();
        orderDetail.setProductId("1234582");
        orderDetail.setProductQuantity(3);
        orderDetailList.add(orderDetail);

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderDetailList(orderDetailList);
        orderDTO.setBuyerName("楚中天");
        orderDTO.setBuyerPhone("1231234102");
        orderDTO.setBuyerAddress("上一单的隔壁宿舍的厕所");
        orderDTO.setBuyerOpenid(OPENID);

        OrderDTO result = orderService.create(orderDTO);
        log.info("[创建订单]{}",result);
        Assert.assertNotEquals(null,result);
    }

    @Test
    public void findOne() throws Exception {

        OrderDTO one = orderService.findOne(ORDERID);
        log.info("[查询订单]{}",one);
        Assert.assertNotNull(one);
    }

    @Test
    public void findList() throws Exception {
        PageRequest pageRequest = new PageRequest(0, 10);
        Page<OrderDTO> list = orderService.findList(OPENID, pageRequest);
        Assert.assertNotEquals(null,list);
    }

    @Test
    public void cancel() throws Exception {
        OrderDTO one = orderService.findOne(ORDERID);
        OrderDTO cancel = orderService.cancel(one);

        Assert.assertEquals(OrderStatusEnum.CANCEL.getCode(),cancel.getOrderStatus());
    }

    @Test
    public void finish() throws Exception {
        OrderDTO one = orderService.findOne(ORDERID);
        OrderDTO finish = orderService.finish(one);

        Assert.assertEquals(OrderStatusEnum.FINISHED.getCode(),finish.getOrderStatus());
    }

    @Test
    public void paid() throws Exception {

        OrderDTO one = orderService.findOne(ORDERID);
        OrderDTO paid = orderService.paid(one);

        Assert.assertEquals(PayStatusEnum.SUCCESS.getCode(),paid.getPayStatus());
    }

}