package com.zte.zshop.backend.controller;

import com.github.pagehelper.PageInfo;
import com.zte.zshop.Constants.Constant;
import com.zte.zshop.Constants.ResponseResult;
import com.zte.zshop.backend.vo.ProductVo;
import com.zte.zshop.dto.ProductDto;
import com.zte.zshop.entity.Product;
import com.zte.zshop.entity.ProductType;
import com.zte.zshop.service.ProductService;
import com.zte.zshop.service.ProductTypeService;
import org.apache.commons.beanutils.PropertyUtils;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author:hellboy
 * Date:2018-11-06 14:04
 * Description:<描述>
 */
@Controller
@RequestMapping("/backend/product")
public class ProductController {

    @Autowired
    private ProductTypeService productTypeService;

    @Autowired
    private ProductService productService;

    //显示商品列表
    @RequestMapping("/findAll")
    public String findAll(Integer pageNum,Model model){
        if(ObjectUtils.isEmpty(pageNum)){
            pageNum=Constant.PAGE_NUM;
        }
        PageInfo<Product> pageInfo= productService.findAll(pageNum, Constant.PAGE_SIZE);
        model.addAttribute("pageInfo",pageInfo);


        return "productManager";
    }

    //在修改模态框和添加模态框的下拉列表中显示启用状态下的商品类型名称
    @ModelAttribute("productTypes")
    //执行该controller下所有请求,先执行该方法，值存入key为productTypes中,默认作用相当于request
    public List<ProductType> loadProductTypes(){

        List<ProductType> productTypes= productTypeService.findEnable(Constant.PRODUCT_TYPE_ENABLE);
        return  productTypes;

    }

    //添加商品
    @RequestMapping("/add")
    //表单值通过name值与参数一一对应，
    public String add(ProductVo productVo,Integer pageNum,HttpSession session,Model model){

        //System.out.println(111);
        //return "productManager";

        //获取图片存放的物理路径
        //String uploadPath = session.getServletContext().getRealPath("/WEB-INF/upload");
        //将VO转换成DTO
        ProductDto productDto = new ProductDto();
        //productDto.setName(productVo.getName());
        //productDto.setPrice(productVo.getPrice());
        try {
            //将vo中属性值对应的拷贝到dto的相关属性中(属性必须一一对应),获取部分值
            PropertyUtils.copyProperties(productDto, productVo);
            //获取原始文件名
            productDto.setFileName(productVo.getFile().getOriginalFilename());
            productDto.setInputStream(productVo.getFile().getInputStream());
            //productDto.setUploadPath(uploadPath);
            productService.add(productDto);
            model.addAttribute("successMsg","添加成功");

        } catch (Exception e) {
            model.addAttribute("errorMsg", e.getMessage());
        }

        //返回列表页面
        return "forward:findAll?pageNum="+pageNum;//转发到findAll请求


    }

    //校验名称是否已经存在
    @RequestMapping("/checkName")
    @ResponseBody
    public Map<String,Object> checkName(String name){

        Map<String,Object> map = new HashMap<>();
        boolean res= productService.checkName(name);
        //如果名字不存在，可用
        if(res){
            map.put("valid",true);
        }
        else{
            //当valid值为true,不输出消息，为false,输出message所对应的值
            map.put("valid",false);
            map.put("message","商品("+name+")已经存在");
        }
        return  map;

    }

    //显示修改模态框
    @RequestMapping("/findById")
    @ResponseBody
    public ResponseResult findById(Integer id){

        Product product= productService.findById(id);
        return ResponseResult.success(product);

    }

    //获取图片
    /*
    逻辑：根据图片的路径，转成字节流，----》拷贝到输出流
     */
    @RequestMapping("/getImg")
    public void getImg(String path,OutputStream out){

        productService.getImg(path,out);

    }

    //修改商品
    @RequestMapping("/modify")
    //表单值通过name值与参数一一对应，
    public String modify(ProductVo productVo,Integer pageNum,HttpSession session,Model model){

        //System.out.println(111);
        //return "productManager";

        //获取图片存放的物理路径
        //String uploadPath = session.getServletContext().getRealPath("/WEB-INF/upload");
        //将VO转换成DTO
        ProductDto productDto = new ProductDto();
        //productDto.setName(productVo.getName());
        //productDto.setPrice(productVo.getPrice());
        try {
            //将vo中属性值对应的拷贝到dto的相关属性中(属性必须一一对应),获取部分值
            PropertyUtils.copyProperties(productDto, productVo);
            //获取原始文件名
            //System.out.println(productVo.getFile().getOriginalFilename());
            //如果没有选择图片getOriginalFilename值为空
            if(!"".equals(productVo.getFile().getOriginalFilename())) {
                productDto.setFileName(productVo.getFile().getOriginalFilename());
                productDto.setInputStream(productVo.getFile().getInputStream());
                //productDto.setUploadPath(uploadPath);
            }
            productService.modifyProduct(productDto);
            model.addAttribute("successMsg","修改成功");

        } catch (Exception e) {
            model.addAttribute("errorMsg", e.getMessage());
        }

        //返回列表页面
        return "forward:findAll?pageNum="+pageNum;//转发到findAll请求


    }

    //删除商品
    @RequestMapping("/removeById")
    @ResponseBody
    public ResponseResult removeById(int id){
        try {
            productService.removeById(id);
            return  ResponseResult.success("删除成功");
        } catch (Exception e) {
            return  ResponseResult.fail("删除失败");
        }
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
        int size=0;
        size = is.read(data);
        while (size!=-1){
            bos.write(data,0,size);
            size=is.read(data);
        }

        //关闭这些流
        is.close();
        bos.flush();
        bos.close();
    }
}


