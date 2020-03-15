package com.weirdo.example.dto;



import lombok.Data;

import java.io.Serializable;


/**
 * Created by Administrator on 2018/8/30.
 */
@Data
public class LogDto implements Serializable {

    private String methodName;

    private String operateData;

    //构造器
    public LogDto(String methodName, String operateData) {
        this.methodName = methodName;
        this.operateData = operateData;
    }

    public LogDto() {
    }
}