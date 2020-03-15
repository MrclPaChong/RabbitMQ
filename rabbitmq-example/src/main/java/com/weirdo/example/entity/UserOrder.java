package com.weirdo.example.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户订单表(UserOrder)实体类
 *
 * @author makejava
 * @since 2020-03-13 10:58:40
 */
@Data
public class UserOrder implements Serializable {
    private static final long serialVersionUID = -93272292694220695L;
    
    private Integer id;
    /**
    * 订单编号
    */
    private String orderNo;
    /**
    * 用户id
    */
    private Integer userId;
    /**
    * 状态(1=已保存；2=已付款；3=已取消)
    */
    private Integer status;
    /**
    * 下单时间
    */
    private Date createTime;
    
    private Date updateTime;


}