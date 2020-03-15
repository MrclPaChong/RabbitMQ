package com.weirdo.example.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * 用户操作日志(UserLog)实体类
 *
 * @author makejava
 * @since 2020-03-13 17:20:18
 */
public class UserLog implements Serializable {
    private static final long serialVersionUID = -36719952080195613L;
    
    private Integer id;
    /**
    * 用户名
    */
    private String userName;
    /**
    * 模块类型
    */
    private String module;
    /**
    * 操作
    */
    private String operation;
    /**
    * 操作数据
    */
    private String data;
    
    private Date createTime;
    
    private Date updateTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public UserLog(String userName, String module, String operation, String data) {
        this.userName = userName;
        this.module = module;
        this.operation = operation;
        this.data = data;
    }
    public UserLog() {
    }
}