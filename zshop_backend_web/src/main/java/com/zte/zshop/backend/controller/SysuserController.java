package com.zte.zshop.backend.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zte.zshop.Constants.Constant;
import com.zte.zshop.Constants.ResponseResult;
import com.zte.zshop.entity.Role;
import com.zte.zshop.entity.Sysuser;
import com.zte.zshop.exception.SysuserNotExistException;
import com.zte.zshop.params.SysuserParam;
import com.zte.zshop.service.RoleService;
import com.zte.zshop.service.SysuserService;
import com.zte.zshop.vo.SysuserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author:hellboy
 * Date:2018-10-31 9:50
 * Description:<描述>
 * 用户管理controller
 */
@Controller
@RequestMapping("/backend/sysuser")
public class SysuserController {

    @Autowired
    private SysuserService sysuserService;

    @Autowired
    private RoleService roleService;

    @RequestMapping("/main")
    public String main(){
        return "main";
    }

    @RequestMapping("/login")
    public String login(String loginName,String password,HttpSession session,Model model){

        try {
            Sysuser sysuser= sysuserService.findByLoginNameAndPassowrd(loginName, password);
            //将该sysuser存入session作用域
            session.setAttribute("sysuser",sysuser);

            //返回视图名称,通过视图解析器，将该名称拼接成完整的页面路径，从而实现页面的返回
            return "main";
        } catch (SysuserNotExistException e) {
            //e.printStackTrace();
            model.addAttribute("errorMsg",e.getMessage());
            return "login";
        }


    }

    @RequestMapping("/findAll")
    public String findAll(Integer pageNum,Model model){
        if(ObjectUtils.isEmpty(pageNum)){
            pageNum= Constant.PAGE_NUM;
        }
        PageHelper.startPage(pageNum,Constant.PAGE_SIZE);
        List<Sysuser> sysusers = sysuserService.findAll();
        PageInfo<Sysuser> pageInfo = new PageInfo<>(sysusers);
        model.addAttribute("data",pageInfo);

        return "sysuserManager";
    }
    //在执行该controller下的任意方式之前执行
    @ModelAttribute("roles")
    public List<Role> loadRoles(){
        return roleService.findAll();


    }

    @RequestMapping("/add")
    @ResponseBody
    public ResponseResult add(SysuserVo sysuserVo){

        try {
            sysuserService.add(sysuserVo);
            return  ResponseResult.success("添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.fail("添加失败");
        }

    }

    @RequestMapping("/findByParams")
    public String findByParams(SysuserParam sysuserParam,Integer pageNum,Model model){

        if(ObjectUtils.isEmpty(pageNum)){
            pageNum=Constant.PAGE_NUM;
        }
        PageHelper.startPage(pageNum,Constant.PAGE_SIZE);
        List<Sysuser> sysusers= sysuserService.findByParams(sysuserParam);
        PageInfo<Sysuser> pageInfo = new PageInfo<>(sysusers);
        model.addAttribute("data",pageInfo);
        model.addAttribute("sysuserParam",sysuserParam);

        return "sysuserManager";

    }

    //校验该登录账号是否已经存在
    @RequestMapping("/checkName")
    @ResponseBody
    //自动将被校验的值注入
    public Map<String,Object> checkName(String loginName){

        Map<String,Object> map = new HashMap<>();
        boolean res= sysuserService.checkName(loginName);
        //如果不存在该用户名，可用
        if(res){
            map.put("valid",true);
        }
        else{
            map.put("valid",false);
            map.put("message","账号【"+loginName+"】已经存在");
        }
        return  map;

    }

    @RequestMapping("/modifyStatus")
    @ResponseBody
    public ResponseResult modifyStatus(int id){
        try {
            sysuserService.modifyStatus(id);
            return ResponseResult.success("更新成功");
        } catch (Exception e) {
            e.printStackTrace();
            return  ResponseResult.fail("更新失败");
        }


    }








}
