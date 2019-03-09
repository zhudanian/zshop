package com.zte.zshop.dto;

import java.io.InputStream;

/**
 * Author:hellboy
 * Date:2018-11-06 15:51
 * Description:dto:数据传输对象，用于封装需要操作数据库的对象
 */
public class ProductDto {

    private Integer id;

    private String name;

    private Double price;

    private Integer productTypeId;

    //springmvc上传附件必须使用CommonsMultipartFile
    //private CommonsMultipartFile file;

    //文件的输入流
    private InputStream inputStream;

    //文件的名称
    private String fileName;

    //文件的上传路径
    private String uploadPath;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(Integer productTypeId) {
        this.productTypeId = productTypeId;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUploadPath() {
        return uploadPath;
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }
}
