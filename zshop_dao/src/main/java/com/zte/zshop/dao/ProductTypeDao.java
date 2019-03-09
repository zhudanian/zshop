package com.zte.zshop.dao;

import com.zte.zshop.entity.ProductType;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Author:hellboy
 * Date:2018-10-31 13:48
 * Description:<描述>
 */
@Repository
public interface ProductTypeDao {

    //获取产品类型列表
    public List<ProductType> selectAll();



    //根据id查找
    public ProductType selectById(Integer id);

    //根据名称查找
    public ProductType selectByName(String name);

    //添加
    public void insert(@Param("name") String name, @Param("status") Integer status);


    //修改名称
    public void updateName(@Param("id") Integer id,@Param("name") String name);

    //修改状态
    public void updateStatus(@Param("id") Integer id,@Param("status") Integer status);

    //删除商品类型
    public void deleteById(Integer id);

    public List<ProductType> selectByStatus(int status);


}
