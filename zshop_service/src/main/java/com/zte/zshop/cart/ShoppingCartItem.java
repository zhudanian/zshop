package com.zte.zshop.cart;

import com.zte.zshop.entity.Product;

/**
 * Author:hellboy
 * Date:2018-11-21 11:06
 * Description:
 * 购物车明细
 * 用于封装存放在购物车中的产品条目信息
 */
public class ShoppingCartItem {

    //产品对象
    private Product product;

    //数量
    private int quantity;

    public ShoppingCartItem() {
    }

    /*
    默认一次只能买一件商品
     */
    public ShoppingCartItem(Product product) {
        this.product = product;
        this.quantity=1;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    //获取该商品在购物车中的总价
    public double getItemMoney(){
        return product.getPrice()*this.quantity;
    }

    //商品数量+1
    public void increment(){
        this.quantity++;
    }




}
