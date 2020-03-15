package com.weirdo.example.mapper;


import com.weirdo.example.entity.UserOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户订单表(UserOrder)表数据库访问层
 *
 * @author makejava
 * @since 2020-03-13 10:58:42
 */
public interface UserOrderDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    UserOrder queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<UserOrder> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param userOrder 实例对象
     * @return 对象列表
     */
    List<UserOrder> queryAll(UserOrder userOrder);

    /**
     * 新增数据
     *
     * @param userOrder 实例对象
     * @return 影响行数
     */
    int insert(UserOrder userOrder);

    /**
     * 新增数据 字段为空的不新增
     *
     * @param record 实例对象
     * @return 影响行数
     */
    int insertSelective(UserOrder record);

    /**
     * 修改数据
     *
     * @param userOrder 实例对象
     * @return 影响行数
     */
    int update(UserOrder userOrder);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);


    UserOrder selectByPkAndStatus(@Param("id") Integer id, @Param("status") Integer status);
}