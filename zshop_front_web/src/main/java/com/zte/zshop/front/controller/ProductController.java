package com.zte.zshop.front.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zte.zshop.Constants.Constant;
import com.zte.zshop.Constants.ResponseResult;
import com.zte.zshop.cart.ShoppingCart;
import com.zte.zshop.entity.Product;
import com.zte.zshop.entity.ProductType;
import com.zte.zshop.front.cart.ShoppingCartUtils;
import com.zte.zshop.params.ProductParam;
import com.zte.zshop.service.ProductService;
import com.zte.zshop.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * Author:hellboy
 * Date:2018-11-14 14:31
 * Description:<描述>
 */
@Controller
@RequestMapping("/front/product")
public class ProductController {

    @Autowired
    private ProductTypeService productTypeService;

    @Autowired
    private ProductService productService;

    //分页显示首页数据
    @RequestMapping("main")
    public String main(ProductParam productParam, Integer pageNum,Model model){
        if(ObjectUtils.isEmpty(pageNum)){
            pageNum=Constant.PAGE_NUM;
        }
        PageHelper.startPage(pageNum,4);

        List<Product> products= productService.findByParams(productParam);
        PageInfo<Product> pageInfo = new PageInfo<>(products);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("productParam",productParam);
        return "main";
    }

    @ModelAttribute("productTypes")
    public List<ProductType> loadProductTypes(){
        List<ProductType> productTypes = productTypeService.findEnable(Constant.PRODUCT_TYPE_ENABLE);
        return productTypes;

    }

    @RequestMapping("/showPic")
    public void showPic(String image,OutputStream out) throws IOException {

        //将http请求读取为流
        URL url = new URL(image);
        URLConnection urlConnection = url.openConnection();
        InputStream is = urlConnection.getInputStream();

        BufferedOutputStream bos = new BufferedOutputStream(out);

        //创建缓冲字节流
        //将输入流写入输出流
        byte[] data = new byte[4096];
        int size = is.read(data);
        while (size!=-1){
            bos.write(data,0,size);
            size=is.read(data);
        }

        //关闭这些流
        is.close();
        bos.flush();
        bos.close();
    }

    //订单页面
    @RequestMapping("toOrder")
    public String toOrder(){
        return "myOrders";
    }

    //购物车页面
    @RequestMapping("/toCart")
    public String toCart(){
        return "cart";
    }


    //会员中心页面
    @RequestMapping("/toCenter")
    public String toCenter(){
        return  "center";
    }

    //将商品放入购物车
    @RequestMapping("/addToCart")
    @ResponseBody
    public ResponseResult addToCart(int id, HttpSession session){
        boolean flag=false;
        //获取购物车对象
        ShoppingCart sc = ShoppingCartUtils.getShoppingCart(session);
        //调用业务层的addToCart方法完成放入购物车逻辑,返回true:表示放入购物车成功，否则返回false
        flag=productService.addToCart(id,sc);

        if(flag){
            return ResponseResult.success("放入购物车成功");

        }
        else{
            return ResponseResult.fail("放入购物车失败");
        }

    }



}
