package com.hevi.dao;

import com.hevi.dataobject.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoDaoTest {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ProductInfoDao dao;

    @Test
    public void addTest() throws Exception {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("123457");
        productInfo.setProductName("瑶柱田鸡粥");
        productInfo.setProductPrice(new BigDecimal(3.2));
        productInfo.setProductStock(50);
        productInfo.setProductDescription("瑶柱都是真的瑶柱，田鸡却不是真的田鸡");
        productInfo.setProductIcon("http://2.jpg");
        productInfo.setProductStatus(0);
        productInfo.setCategoryType(2);

        ProductInfo save = dao.save(productInfo);
        Assert.assertNotEquals(null,save);
        log.info(save.toString());
    }

    @Test
    public void findByProductStatus() throws Exception {
        List<ProductInfo> result = dao.findByProductStatus(0);
        Assert.assertNotEquals(0,result.size());
        log.info(result.toString());
    }

}