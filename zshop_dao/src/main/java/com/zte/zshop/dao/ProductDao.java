package com.zte.zshop.dao;

import com.zte.zshop.entity.Product;
import com.zte.zshop.params.ProductParam;

import java.util.List;

/**
 * Author:hellboy
 * Date:2018-11-06 14:22
 * Description:<描述>
 */
public interface ProductDao {


    public void insert(Product product);

    Product selectByName(String name);

    public List<Product> selectAll();


    //根据id查找
    public  Product selectById(Integer id);

    //修改
    public void update(Product product);

    //删除
    public void deleteById(Integer id);

    public List<Product> selectByParams(ProductParam productParam);
}
