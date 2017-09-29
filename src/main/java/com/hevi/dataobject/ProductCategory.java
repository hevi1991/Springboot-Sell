package com.hevi.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by Hevi on 2017/9/26.
 */
@Entity//少了个@Entity注解..
@DynamicUpdate//自动刷新update时间
@Data//自动生成getter，setter和toString
public class ProductCategory {
    @Id
    @GeneratedValue
    private Integer categoryId;
    private String categoryName;
    private Integer categoryType;

}
