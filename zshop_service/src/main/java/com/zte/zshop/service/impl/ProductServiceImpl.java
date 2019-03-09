package com.zte.zshop.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zte.zshop.cart.ShoppingCart;
import com.zte.zshop.dao.ProductDao;
import com.zte.zshop.dto.ProductDto;
import com.zte.zshop.entity.Product;
import com.zte.zshop.entity.ProductType;
import com.zte.zshop.exception.FileUploadException;
import com.zte.zshop.ftp.FtpConfig;
import com.zte.zshop.ftp.FtpUtils;
import com.zte.zshop.params.ProductParam;
import com.zte.zshop.service.ProductService;
import com.zte.zshop.utils.StringUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Author:hellboy
 * Date:2018-11-06 16:15
 * Description:<描述>
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private FtpConfig ftpConfig;

    @Override
    public void add(ProductDto productDto) throws FileUploadException {

        //获取文件名
        //处理该文件名，通过一种方式获取一个尽可能不冲突的文件名
        String fileName = StringUtils.renameFileName(productDto.getFileName());
        //String filePath = productDto.getUploadPath() + "\\" + fileName;

        //获取ftp服务器上的二级目录
        String picSavePath = StringUtils.generateRandomDir(fileName);


        String filePath="";
        //上传文件
        try {
            //StreamUtils.copy(productDto.getInputStream(), new FileOutputStream(filePath));
            filePath = FtpUtils.pictureUploadByConfig(ftpConfig, fileName, picSavePath, productDto.getInputStream());
        } catch (IOException e) {
            throw new FileUploadException("文件上传失败:" + e.getMessage());
        }

        //将相关值保存到数据库
        //dto--->pojo
        Product product = new Product();
        try {
            PropertyUtils.copyProperties(product, productDto);
            product.setImage(filePath);
            ProductType productType = new ProductType();
            productType.setId(productDto.getProductTypeId());

            product.setProductType(productType);

            productDao.insert(product);


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public boolean checkName(String name) {
        Product product = productDao.selectByName(name);
        if (product != null) {

            return false;
        }
        return true;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public PageInfo<Product> findAll(Integer pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Product> products= productDao.selectAll();
        PageInfo<Product> pageInfo = new PageInfo<>(products);
        return pageInfo;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Product findById(Integer id) {
        return productDao.selectById(id);
    }

    @Override
    public void modifyProduct(ProductDto productDto) throws FileUploadException {
        //获取文件名
        //处理该文件名，通过一种方式获取一个尽可能不冲突的文件名
        String filePath="";
        if(productDto.getFileName()!=null) {
            String fileName = StringUtils.renameFileName(productDto.getFileName());
            String picSavePath = StringUtils.generateRandomDir(fileName);
            //filePath = productDto.getUploadPath() + "\\" + fileName;
            //上传文件
            try {
                //StreamUtils.copy(productDto.getInputStream(), new FileOutputStream(filePath));
                filePath=FtpUtils.pictureUploadByConfig(ftpConfig,fileName,picSavePath,productDto.getInputStream());
            } catch (IOException e) {
                throw new FileUploadException("文件上传失败:" + e.getMessage());
            }
        }

        //将相关值保存到数据库
        //dto--->pojo
        Product product = new Product();
        try {
            PropertyUtils.copyProperties(product, productDto);
            if(productDto.getFileName()!=null) {
                product.setImage(filePath);
            }
            ProductType productType = new ProductType();
            productType.setId(productDto.getProductTypeId());

            product.setProductType(productType);

            productDao.update(product);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeById(Integer id) {

        productDao.deleteById(id);

    }

    @Override
    public void getImg(String path, OutputStream out) {

        try {
            StreamUtils.copy(new FileInputStream(path),out);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Product> findByParams(ProductParam productParam) {
        return productDao.selectByParams(productParam);
    }

    @Override
    /**
     * 查找该id对应的商品是否存在，如果存在，加入购物车，返回true,否则返回false
     */
    public boolean addToCart(int id, ShoppingCart sc) {
        Product product = productDao.selectById(id);
        if(product!=null){
            sc.addProduct(product);
            return true;

        }
        return false;
    }
}
