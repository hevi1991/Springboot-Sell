package com.hevi.dao;

import com.hevi.dataobject.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderMasterDao extends JpaRepository<OrderMaster,String > {

    Page<OrderMaster> findByBuyerOpenid(String buyerOpenid, Pageable pageable);
}
