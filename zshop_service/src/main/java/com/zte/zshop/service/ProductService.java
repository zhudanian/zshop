package com.zte.zshop.service;

import com.github.pagehelper.PageInfo;
import com.zte.zshop.cart.ShoppingCart;
import com.zte.zshop.dto.ProductDto;
import com.zte.zshop.entity.Product;
import com.zte.zshop.exception.FileUploadException;
import com.zte.zshop.params.ProductParam;

import java.io.OutputStream;
import java.util.List;

/**
 * Author:hellboy
 * Date:2018-11-06 16:10
 * Description:<描述>
 */
public interface ProductService {


    public void add(ProductDto productDto)throws FileUploadException;

    public boolean checkName(String name);

    public PageInfo<Product> findAll(Integer pageNum, int pageSize);

    public Product findById(Integer id);

    public void modifyProduct(ProductDto productDto) throws FileUploadException;

    public void removeById(Integer id);

    public void getImg(String path, OutputStream out);

    public List<Product> findByParams(ProductParam productParam);

    public boolean addToCart(int id, ShoppingCart sc);
}
