package com.hevi.dao;

import com.hevi.dataobject.ProductCategory;
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

/**
 * Created by Hevi on 2017/9/26.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryDaoTest {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ProductCategoryDao dao;

    @Test
    public void testDao() throws Exception {
        List<ProductCategory> all = dao.findAll();
        log.info(all.toString());
    }

    @Test
    @Transactional//测试完数据回滚
    public void testAdd() throws Exception {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName("大年轻最爱");
        productCategory.setCategoryType(13);
        ProductCategory save = dao.save(productCategory);
        log.info(save.toString());
    }

    @Test
    public void testUpdate() throws Exception {
        /*
        * 一般修改操作步骤
        * 1.根据id查询数据库是否存在这条数据，取出来，然后修改完存回去
        * */
        ProductCategory one = dao.findOne(2);
        one.setCategoryType(12);
        ProductCategory save = dao.save(one);

    }

    @Test
    public void findList() throws Exception {
        List<Integer> list = Arrays.asList(2, 3, 5);
        List<ProductCategory> result = dao.findByCategoryTypeIn(list);
        Assert.assertNotNull(result);
        log.info(result.toString());
    }
}