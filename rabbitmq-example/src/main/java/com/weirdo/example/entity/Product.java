package com.weirdo.example.entity;


import lombok.Data;

import java.util.Date;

@Data
public class Product {

    private Integer id;

    private String product_no;

    private Integer total;


    private Date create_time;


    private Date update_time;


}