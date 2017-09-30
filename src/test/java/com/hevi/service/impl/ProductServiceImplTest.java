package com.hevi.service.impl;

import com.hevi.dataobject.ProductInfo;
import com.hevi.service.ProductService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceImplTest {
    @Autowired
    ProductService productService;
    @Test
    public void findOne() throws Exception {
        ProductInfo one = productService.findOne("123456");
        Assert.assertNotEquals(null,one);
    }

    @Test
    public void findUpAll() throws Exception {
        List<ProductInfo> upAll = productService.findUpAll();
        Assert.assertNotEquals(0,upAll.size());
    }

    @Test
    public void findAll() throws Exception {
        //Pageable->go to implements
        PageRequest pageRequest = new PageRequest(0, 2);
        Page<ProductInfo> all = productService.findAll(pageRequest);
        Assert.assertNotEquals(0,all.getSize());
    }

    @Test
    @Transactional
    public void save() throws Exception {
        ProductInfo one = productService.findOne("123456");
        one.setProductPrice(new BigDecimal(5.00));
        ProductInfo save = productService.save(one);
        Assert.assertNotEquals(null,save);
    }

}