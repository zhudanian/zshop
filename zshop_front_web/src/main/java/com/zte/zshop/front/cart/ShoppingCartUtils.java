package com.zte.zshop.front.cart;

import com.zte.zshop.cart.ShoppingCart;

import javax.servlet.http.HttpSession;

/**
 * Author:hellboy
 * Date:2018-11-21 14:18
 * Description:
 * 逻辑
 * 封装购物车在session中的操作

 */
public class ShoppingCartUtils {

    /**
     * 从session作用域中获取购物车对象,若session中还没有该购物车对象，创建一个新的对象，
     * 放入session作用域，若有，直接返回该购物车对象(单例)
     */

    public static ShoppingCart getShoppingCart(HttpSession session){
        ShoppingCart sc = (ShoppingCart) session.getAttribute("shoppingcart");
        if(sc==null){
            sc  = new ShoppingCart();
            session.setAttribute("shoppingcart",sc);
        }
        return  sc;

    }


}
