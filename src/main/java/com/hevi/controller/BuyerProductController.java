package com.hevi.controller;

import com.hevi.dataobject.ProductCategory;
import com.hevi.dataobject.ProductInfo;
import com.hevi.service.CategoryService;
import com.hevi.service.ProductService;
import com.hevi.utils.ResultVOUtil;
import com.hevi.viewobejct.ProductInfoVO;
import com.hevi.viewobejct.ProductVO;
import com.hevi.viewobejct.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 买家商品
 */
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;

    @GetMapping("/list")
    public ResultVO list(){

        /**
         * 1.查询所有上架的商品
         * 2.查询所有类目
         * 3.拼接数据
        * */
        List<ProductInfo> upAllProduct = productService.findUpAll();
        List<ProductCategory> allCategory = categoryService.findAll();
        //传统拼接
        /*
        List<ProductVO> productVOList = new ArrayList<ProductVO>();
        for (ProductCategory category : allCategory){
            ProductVO productVO = new ProductVO();
            productVO.setCategoryName(category.getCategoryName());
            productVO.setCategoryType(category.getCategoryType());

            List<ProductInfoVO> productInfoVOList = new ArrayList<ProductInfoVO>();
            for (ProductInfo productInfo : upAllProduct){
                if (productInfo.getCategoryType().equals(productVO.getCategoryType())){
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    //Spring框架提供的复制属性（属性名一致才可复制）
                    BeanUtils.copyProperties(productInfo,productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }
        ResultVO resultVO = new ResultVO(0,"没问题",productVOList);
        */
        //lambda精简版
        List<ProductVO> productVOList = allCategory.stream().map(c->{
            ProductVO productVO = new ProductVO();
            productVO.setCategoryName(c.getCategoryName());
            productVO.setCategoryType(c.getCategoryType());

            List<ProductInfoVO> productInfoVOList = upAllProduct.stream().filter(p->p.getCategoryType().equals(c.getCategoryType())
            ).map(pi -> {
                ProductInfoVO productInfoVO = new ProductInfoVO();
                BeanUtils.copyProperties(pi, productInfoVO);
                return productInfoVO;
            }).collect(Collectors.toList());

            productVO.setProductInfoVOList(productInfoVOList);
            return productVO;
        }).collect(Collectors.toList());

        return ResultVOUtil.success(productVOList);
    }

}
