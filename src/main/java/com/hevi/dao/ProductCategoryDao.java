package com.hevi.dao;

import com.hevi.dataobject.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Hevi on 2017/9/26.
 */
public interface ProductCategoryDao extends JpaRepository<ProductCategory,Integer> {

    /**
     * 查询在Category List数组以内的所有分类
     * @return
     */
    List<ProductCategory> findByCategoryTypeIn(List list);
}
