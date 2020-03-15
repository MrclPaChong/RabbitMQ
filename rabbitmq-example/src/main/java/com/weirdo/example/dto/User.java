package com.weirdo.example.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/8/23.
 */
@Data
public class User implements Serializable{

    private Integer id;

    private String userName;

    private String name;

}