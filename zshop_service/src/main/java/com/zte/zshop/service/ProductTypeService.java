package com.zte.zshop.service;

import com.github.pagehelper.PageInfo;
import com.zte.zshop.entity.ProductType;
import com.zte.zshop.exception.ProductTypeExistException;

import java.util.List;

/**
 * Author:hellboy
 * Date:2018-10-31 14:32
 * Description:<描述>
 */
public interface ProductTypeService {

    //查询所有商品类型信息
    public List<ProductType> findAll();


    //分页查询商品类型信息
    public PageInfo<ProductType> findAllByPage(Integer page,Integer rows);

    public void add(String productTypeName) throws ProductTypeExistException;

    public  ProductType findById(Integer id);

    public void modifyName(Integer id, String name) throws ProductTypeExistException;

    public void deleteById(int id);

    public void modifyStatus(int id);

    public List<ProductType> findEnable(int status);
}
