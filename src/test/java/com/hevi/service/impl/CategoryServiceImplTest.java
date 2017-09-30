package com.hevi.service.impl;

import com.hevi.dataobject.ProductCategory;
import com.hevi.service.CategoryService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceImplTest {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    CategoryService categoryService;

    @Test
    public void findOne() throws Exception {
        ProductCategory one = categoryService.findOne(5);
        Assert.assertNotEquals(null,one);
    }

    @Test
    public void findAll() throws Exception {

        List<ProductCategory> all = categoryService.findAll();
        Assert.assertNotEquals(0,all.size());
    }

    @Test
    public void findByCategoryTypeIn() throws Exception {
        List<Integer> integers = Arrays.asList(1,2,3,4,5,6);
        List<ProductCategory> result = categoryService.findByCategoryTypeIn(integers);
        Assert.assertNotEquals(0,result.size());
    }

    @Test
    @Transactional
    public void save() throws Exception {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName("最新套餐");
        productCategory.setCategoryType(99);
        ProductCategory save = categoryService.save(productCategory);
        Assert.assertNotEquals(null,save);
    }

}