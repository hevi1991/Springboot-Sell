package com.hevi.service;

import com.hevi.dataobject.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    public ProductInfo findOne(String productId);

    /**
     * 查询所有上架的商品列表
     * @return
     */
    List<ProductInfo> findUpAll();

    /**
     * 查找所有商品
     * @param pageable 分页
     * @return
     */
    Page<ProductInfo> findAll(Pageable pageable);

    /**
     * 新增或修改商品信息
     * @param productInfo
     * @return
     */
    ProductInfo save(ProductInfo productInfo);

    /**
     * TODO
     * 加库存
     * 减库存
     */
}