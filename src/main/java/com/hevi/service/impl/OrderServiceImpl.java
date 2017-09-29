package com.hevi.service.impl;

import com.hevi.dao.OrderDetailDao;
import com.hevi.dao.OrderMasterDao;
import com.hevi.dataobject.OrderDetail;
import com.hevi.dataobject.OrderMaster;
import com.hevi.dataobject.ProductInfo;
import com.hevi.dto.CartDTO;
import com.hevi.dto.OrderDTO;
import com.hevi.enums.OrderStatusEnum;
import com.hevi.enums.PayStatusEnum;
import com.hevi.enums.ResultEnum;
import com.hevi.exception.SellException;
import com.hevi.service.OrderService;
import com.hevi.service.ProductService;
import com.hevi.utils.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderDetailDao orderDetailDao;

    @Autowired
    OrderMasterDao orderMasterDao;

    @Override
    @Transactional//进行事务控制,若有异常,取消数据库操作
    public OrderDTO create(OrderDTO orderDTO) {
        //1.查询商品(数量,价格)
        String orderId = getUniqueOrderId();//订单id
        BigDecimal amount = new BigDecimal(BigInteger.ZERO);//总价
        for (OrderDetail orderDetail:
        orderDTO.getOrderDetailList()) {
            ProductInfo productInfo = productService.findOne(orderDetail.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            //2.计算总价
            amount = productInfo.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity())).add(amount);

            //3.写入订单数据库(OrderMaster,OrderDetail)
            //3.1 OrderDetail
            BeanUtils.copyProperties(productInfo,orderDetail);//复制同类同属性名
            orderDetail.setDetailId(getUniqueDetailId());
            orderDetail.setOrderId(orderId);
            orderDetailDao.save(orderDetail);
        }
        //3.2 OrderMaster
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);//复制同类同属性名
        orderMaster.setOrderId(orderId);
        orderMaster.setOrderAmount(amount);
        //注意,BeanUtils.copyProperties会覆盖
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterDao.save(orderMaster);

        //4.扣库存(事务)
        //使用Lambda表达式,获取需要扣库存的CartDTO列表
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().map(
                o -> new CartDTO(o.getProductId(), o.getProductQuantity())
        ).collect(Collectors.toList());
        productService.decreaseStock(cartDTOList);

        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        return null;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        return null;
    }

    @Override
    public OrderDTO cancel(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public OrderDTO finish(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public OrderDTO paid(OrderDTO orderDTO) {
        return null;
    }

    /**
     * 保证数据库的orderId唯一
     * @return
     */
    private String getUniqueOrderId(){
        String key;
        OrderMaster orderMaster;
        do {
            key = KeyUtil.genUniqueKey();
            orderMaster = orderMasterDao.findOne(key);
        } while (orderMaster != null);
        return key;
    }
    /**
     * 保证数据的detailId唯一
     * @return
     */
    private String getUniqueDetailId(){
        String key;
        OrderDetail orderDetail;
        do {
            key = KeyUtil.genUniqueKey();
            orderDetail = orderDetailDao.findOne(key);
        } while (orderDetail != null);
        return key;
    }
}
