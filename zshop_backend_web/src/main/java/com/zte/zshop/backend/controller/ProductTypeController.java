package com.zte.zshop.backend.controller;

import com.github.pagehelper.PageInfo;
import com.zte.zshop.Constants.Constant;
import com.zte.zshop.Constants.ResponseResult;
import com.zte.zshop.entity.ProductType;
import com.zte.zshop.exception.ProductTypeExistException;
import com.zte.zshop.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Author:hellboy
 * Date:2018-10-31 10:10
 * Description:<描述>
 */
@Controller
@RequestMapping("/backend/productType")
public class ProductTypeController {

    @Autowired
    private ProductTypeService productTypeService;

    @RequestMapping("/findAll")
    //查询所有记录
    public String findAll(Model model) {

        //调用service获取产品类型列表
        List<ProductType> productTypes = productTypeService.findAll();
        //将该列表存入model,相当于request
        model.addAttribute("data", productTypes);


        //返回产品类型管理视图
        return "productTypeManager";


    }

    @RequestMapping("/findAllByPage")
    //分页查询记录
    public String findAllByPage(Integer pageNum, Model model) {

        if (ObjectUtils.isEmpty(pageNum)) {
            pageNum = Constant.PAGE_NUM;
        }
        //调用service获取产品类型列表
        PageInfo<ProductType> pageInfo = productTypeService.findAllByPage(pageNum, Constant.PAGE_SIZE);
        //将该列表存入model,相当于request
        model.addAttribute("data", pageInfo);
        //返回产品类型管理视图
        return "productTypeManager1";
    }

    //添加商品类型
    @RequestMapping("/add")
    //用于返回json对象，使用fastjson自动将java对象转成json字符串
    @ResponseBody
    public ResponseResult add(@RequestParam("name") String productTypeName) {
        ResponseResult result = new ResponseResult();
        try {
            productTypeService.add(productTypeName);
            result.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
            result.setMessage("添加成功");
        } catch (ProductTypeExistException e) {
            result.setStatus(Constant.RESPONSE_STATUS_FAILURE);
            result.setMessage("商品类型已经存在");
        }
        //使用json的一个转换器，将该对象自动转换成一个json字符串
        return result;

    }

    //显示修改商品界面
    @RequestMapping("/findById")
    @ResponseBody
    public ResponseResult findById(Integer id) {
        ProductType productType = productTypeService.findById(id);
        return  ResponseResult.success(productType);
    }

    //修改商品类型名称
    @RequestMapping("/modify")
    @ResponseBody
    public ResponseResult modifyName(Integer id,String name){

        try {
            productTypeService.modifyName(id,name);
            return  ResponseResult.success("修改商品类型成功");
        } catch (ProductTypeExistException e) {
            //e.printStackTrace();
            return ResponseResult.fail(e.getMessage());
        }

    }

    //删除商品类型
    @RequestMapping("/deleteById")
    @ResponseBody
    public ResponseResult deleteById(int id){

        productTypeService.deleteById(id);
        return  ResponseResult.success("删除成功");

    }

    //更新状态
    @RequestMapping("/modifyStatus")
    @ResponseBody
    public ResponseResult modifyStatus(int id){
        productTypeService.modifyStatus(id);
        return  ResponseResult.success("更新商品类型状态");//这个地方并没有去接收该返回值


    }

}
