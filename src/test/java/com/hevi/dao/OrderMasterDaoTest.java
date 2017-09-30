package com.hevi.dao;

import com.hevi.dataobject.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterDaoTest {

    @Autowired
    private OrderMasterDao dao;

    private final String OPENID = "wx123333";

    @Test
    public void save() throws Exception {
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("991212");
        orderMaster.setBuyerName("独孤求败");
        orderMaster.setBuyerPhone("120301889");
        orderMaster.setBuyerAddress("华山1号302楼一座");
        orderMaster.setBuyerOpenid(OPENID);
        orderMaster.setOrderAmount(new BigDecimal(33.00));

        OrderMaster save = dao.save(orderMaster);
        Assert.assertNotEquals(null,save);
    }

    @Test
    public void findByBuyerOpenid() throws Exception {
        PageRequest pageRequest = new PageRequest(0,2);
        Page<OrderMaster> page = dao.findByBuyerOpenid(OPENID, pageRequest);
        Assert.assertNotEquals(0,page.getContent().size());
    }

}