package com.hevi.dao;

import com.hevi.dataobject.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductInfoDao extends JpaRepository<ProductInfo,String >{

    /**
     * 查询商家商品
     * @param status
     * @return
     */
    List<ProductInfo> findByProductStatus(Integer status);
}
