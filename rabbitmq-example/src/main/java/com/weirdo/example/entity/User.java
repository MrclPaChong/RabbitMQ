package com.weirdo.example.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * 用户信息表(User)实体类
 *
 * @author makejava
 * @since 2020-03-13 17:20:15
 */
public class User implements Serializable {
    private static final long serialVersionUID = 759491087928515685L;
    
    private Integer id;
    /**
    * 用户名
    */
    private String userName;
    /**
    * 密码
    */
    private String password;
    /**
    * 性别（1=男；2=女）
    */
    private Integer sex;
    /**
    * 是否有效（1=是；0=否）
    */
    private Integer isActive;
    /**
    * 创建时间
    */
    private Date createTime;
    /**
    * 更新时间
    */
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
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

}