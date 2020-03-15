package com.weirdo.example.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/8/30.
 */
@Data
public class UserOrderDto implements Serializable{

    private String orderNo;

    private Integer userId;

}
