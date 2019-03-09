package com.zte.zshop.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zte.zshop.Constants.Constant;
import com.zte.zshop.dao.ProductTypeDao;
import com.zte.zshop.entity.ProductType;
import com.zte.zshop.exception.ProductTypeExistException;
import com.zte.zshop.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Author:hellboy
 * Date:2018-10-31 14:34
 * Description:<描述>
 * 1:使用spring的声明式事务(aop)
 * 2:使用sping的ioc注入dao
 *
 * @Service:标记该类被spring管理
 * @Transactional:标记该类具备事务，这个标记也可以作用在对应的方法上,表示这个方法具备某个事务
 *
 * @Autowired
 * 自动注入
 * 需要注意点：service和dao必须被spring管理，dao的id默认为类名第一个字母小写
 * 这里需要注入的dao的名称需要和被管理的dao的id的名称一致，完成注入
 *
 *
 */
@Service
@Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
public class ProductTypeServiceImpl implements ProductTypeService {

    @Autowired
    private ProductTypeDao productTypeDao;

    @Override
    @Transactional(readOnly = true)
    //查询所有记录
    public List<ProductType> findAll() {
        return productTypeDao.selectAll();
    }


    @Override
    @Transactional(readOnly = true)
    //分页查询商品类型信息
    public PageInfo<ProductType> findAllByPage(Integer page,Integer rows) {

        //设置分页
        PageHelper.startPage(page,rows);


        List<ProductType> productTypes = productTypeDao.selectAll();
        PageInfo<ProductType> pageInfo = new PageInfo<>(productTypes);

        //pageInfo.getList();//获取集合
        //pageInfo.getPageNum();//获取当前页
        //pageInfo.getPages();//获取总页数
        //pageInfo.getNextPage();//获取下一页

        return  pageInfo;
    }

    @Override
    public void add(String name) throws ProductTypeExistException {
        //判断该产品类型是否已经存在
        ProductType productType = productTypeDao.selectByName(name);
        if(productType!=null){
            throw  new ProductTypeExistException("商品类型已经存在");
        }
        productTypeDao.insert(name, Constant.PRODUCT_TYPE_ENABLE);

    }

    @Override
    @Transactional(readOnly = true)
    public ProductType findById(Integer id) {
        return productTypeDao.selectById(id);
    }

    @Override
    public void modifyName(Integer id, String name) throws ProductTypeExistException {

        ProductType productType = productTypeDao.selectByName(name);
        if(productType!=null){
            throw new ProductTypeExistException("商品类型名称已经存在");
        }
        productTypeDao.updateName(id,name);

    }

    @Override
    public void deleteById(int id) {
        productTypeDao.deleteById(id);

    }

    @Override
    public void modifyStatus(int id) {
        ProductType productType = productTypeDao.selectById(id);
        Integer status = productType.getStatus();
        if(status==Constant.PRODUCT_TYPE_ENABLE){
            status=Constant.PRODUCT_TYPE_DISABLE;
        }
        else if(status==Constant.PRODUCT_TYPE_DISABLE){
            status=Constant.PRODUCT_TYPE_ENABLE;
        }
        productTypeDao.updateStatus(id,status);



    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<ProductType> findEnable(int status) {
        return productTypeDao.selectByStatus(status);
    }
}
