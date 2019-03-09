package com.zte.zshop.entity;

import java.io.Serializable;

/**
 * Author:hellboy
 * Date:2018-11-06 16:38
 * Description:<描述>
 */
public class Product implements Serializable {

    private Integer id;

    private String name;

    private Double price;

    private String info;

    private String image;

    private ProductType productType;

    public Product() {
    }

    public Product(Integer id, String name, Double price, String info, String image, ProductType productType) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.info = info;
        this.image = image;
        this.productType = productType;
    }

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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }
}
