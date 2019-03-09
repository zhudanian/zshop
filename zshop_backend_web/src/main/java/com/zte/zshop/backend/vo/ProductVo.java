package com.zte.zshop.backend.vo;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * Author:hellboy
 * Date:2018-11-06 14:44
 * Description:
 * controller层的数据封装对象，用于封装在页面中提交过来的元素的值
 * 注意使用springmvc完成数据的自动注入：元素name的值必须和vo中属性值一致，才能完成注入
 */
public class ProductVo {

    private Integer id;

    private String name;

    private Double price;

    private Integer productTypeId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    //springmvc上传附件必须使用CommonsMultipartFile
    private CommonsMultipartFile file;

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

    public CommonsMultipartFile getFile() {
        return file;
    }

    public void setFile(CommonsMultipartFile file) {
        this.file = file;
    }
}
