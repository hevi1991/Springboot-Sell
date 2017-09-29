package com.hevi.service.impl;

import com.hevi.dao.ProductInfoDao;
import com.hevi.dataobject.ProductInfo;
import com.hevi.enums.ProductStatus;
import com.hevi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    ProductInfoDao dao;

    @Override
    public ProductInfo findOne(String productId) {
        return dao.findOne(productId);
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return dao.findByProductStatus(ProductStatus.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        //findAll传入Pageable参数后，返回值类型会变成Page
        return dao.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return dao.save(productInfo);
    }
}
