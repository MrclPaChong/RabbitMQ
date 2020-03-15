package com.weirdo.example.entity;

import lombok.Data;

import java.util.Date;

@Data
public class ProductRobbingRecord {

    private Integer id;

    private String mobile;

    private Integer productId;

    private Date robbingTime;

    private Date updateTime;

}