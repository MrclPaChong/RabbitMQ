package com.weirdo.example.mapper;

import com.weirdo.example.entity.Product;
import org.apache.ibatis.annotations.Param;


public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    Product selectByProductNo(@Param("productNo") String productNo);

    int updateTotal(@Param("productNo") String productNo);
}