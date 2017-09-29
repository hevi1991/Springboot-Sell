package com.hevi.service;

import com.hevi.dataobject.ProductCategory;

import java.util.List;

public interface CategoryService {

    ProductCategory findOne(Integer categoryId);

    List<ProductCategory> findAll();

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    /**
     * 新增或者更新
     * @param productCategory
     * @return
     */
    ProductCategory save(ProductCategory productCategory);
}
