package com.zte.zshop.front.controller;

import com.zte.zshop.Constants.Constant;
import com.zte.zshop.Constants.ResponseResult;
import com.zte.zshop.entity.Customer;
import com.zte.zshop.exception.LoginErrorException;
import com.zte.zshop.exception.PhoneNotExistException;
import com.zte.zshop.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Author:hellboy
 * Date:2018-11-16 16:10
 * Description:<描述>
 */
@Controller
@RequestMapping("/front/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @RequestMapping("/loginByAccount")
    @ResponseBody
    public ResponseResult loginByAccount(String loginName, String password, HttpSession session) {

        try {
            Customer customer = customerService.login(loginName, password);
            //将用户对象存入session作用域
            session.setAttribute("customer", customer);
            return ResponseResult.success(customer);

        } catch (LoginErrorException e) {
            //e.printStackTrace();
            return ResponseResult.fail(e.getMessage());
        }

    }

    //退出
    @RequestMapping("/loginOut")
    @ResponseBody
    public ResponseResult loginOut(HttpSession session) {
        //使session失效
        session.invalidate();
        return ResponseResult.success("退出成功");

    }

    //短信快捷登录
    @RequestMapping("/loginBySms")
    @ResponseBody
    public ResponseResult loginBySms(String phone, int verificationCode, HttpSession session) {
        ResponseResult result = new ResponseResult();
        try {
            //判断手机号是否注册
            Customer customer = customerService.findByPhone(phone);
            //判断校验码是否存在
            Object randCode = session.getAttribute("randCode");
            //如果校验码存在，进行判断比对
            if (!ObjectUtils.isEmpty(randCode)) {
                int code = (Integer) randCode;
                if (code == verificationCode) {

                    //需要设置你好XX
                    session.setAttribute("customer", customer);
                    //result.success(customer);
                    result.setObj(customer);
                    result.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
                } else {
                    result.setMessage("验证码不正确");
                }


            } else {
                result.setMessage("验证码不存在或者已经过期，请重新输入");

            }


        } catch (PhoneNotExistException e) {
            //e.printStackTrace();
            result.setMessage(e.getMessage());
        }

        return result;

    }

}
