package com.hevi.dao;

import com.hevi.dataobject.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailDaoTest {

    @Autowired
    OrderDetailDao dao;

    @Test
    public void save() throws Exception {
        OrderDetail detail = new OrderDetail();
        detail.setDetailId("12314");
        detail.setOrderId("111111");
        detail.setProductIcon("http://j.jpg");
        detail.setProductId("12323");
        detail.setProductName("皮皮蟹");
        detail.setProductPrice(new BigDecimal(3.20));
        detail.setProductQuantity(1);

        OrderDetail save = dao.save(detail);
        Assert.assertNotEquals(null,save);
    }

    @Test
    public void findByOrderId() throws Exception {
        List<OrderDetail> details = dao.findByOrderId("111111");
        Assert.assertNotEquals(0,details.size());
    }

}