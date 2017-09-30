package com.hevi.service.impl;

import com.hevi.converter.OrderConverter;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
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

        //扣库存会存在线程安全问题
        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        OrderMaster orderMaster = orderMasterDao.findOne(orderId);
        if (orderMaster == null) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        List<OrderDetail> orderDetailList = orderDetailDao.findByOrderId(orderId);
        if (CollectionUtils.isEmpty(orderDetailList)){//Spring框架提供的集合工具
            throw new SellException(ResultEnum.ORDER_DETAIL_NOT_EXIST);
        }

        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);

        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterDao.findByBuyerOpenid(buyerOpenid, pageable);
        List<OrderMaster> content = orderMasterPage.getContent();

        List<OrderDTO> orderDTOList = OrderConverter.convert(content);
        return new PageImpl<OrderDTO>(orderDTOList,pageable,orderMasterPage.getTotalElements());
    }

    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        //1.判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("[取消订单]订单状态不正确,orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //2.修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());//先设置
        OrderMaster orderMaster = new OrderMaster();//再拷贝
        BeanUtils.copyProperties(orderDTO,orderMaster);//属性拷贝,时机要看准,修改完状态再拷贝
        OrderMaster updateResult = orderMasterDao.save(orderMaster);//拷贝完保存到数据库
        if (updateResult == null){
            log.error("[取消订单]更新失败,orderMaster={}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        //3.返还库存
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("[取消订单]订单无商品详情,orderDTO={}",orderDTO);
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().map(od->new CartDTO(od.getProductId(),od.getProductQuantity())).collect(Collectors.toList());
        productService.increaseStock(cartDTOList);

        //4.判断是否支付.是,退款
        if (orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())){
            //TODO
        }
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) {
        //判断订单状态(按道理应该是收货后,才完结订单)
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("[完结订单]订单状态不正确,orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //修改状态
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult = orderMasterDao.save(orderMaster);
        if (updateResult == null){
            log.error("[取消订单]更新失败,orderMaster={}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }

    @Override
    public OrderDTO paid(OrderDTO orderDTO) {
        //判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("[订单支付]订单状态不正确,orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //判断支付状态
        if (!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())){
            log.error("[订单支付]订单状态不正确,orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getPayStatus());
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }
        //修改支付状态
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult = orderMasterDao.save(orderMaster);
        if (updateResult == null) {
            log.error("[订单支付]更新失败,orderMaster={}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        return orderDTO;
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
