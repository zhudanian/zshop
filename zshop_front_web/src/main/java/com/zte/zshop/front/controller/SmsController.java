package com.zte.zshop.front.controller;

import com.zte.zshop.Constants.ResponseResult;
import com.zte.zshop.utils.HttpClientUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Author:hellboy
 * Date:2018-11-19 14:08
 * Description:<描述>
 */
@Controller
@RequestMapping("/sms")
public class SmsController {

    @Value("${sms.url}")
    private String url;

    @Value("${sms.key}")
    private String key;

    @Value("${sms.tpl_id}")
    private String tplId;

    @Value("${sms.tpl_value}")
    private String tplValue;

    @RequestMapping("/sendVerificationCode")
    @ResponseBody
    public ResponseResult sendVerificationCode(String phone,HttpSession session){

        try {
            //随机生成6位数字
            int randCode=(int)((Math.random()*9+1)*100000);
            session.setAttribute("randCode",randCode);

            Map<String,String> params = new HashMap<>();
            params.put("mobile",phone);
            params.put("key",key);
            params.put("tpl_id",tplId);
            params.put("tpl_value",tplValue+randCode);

            //返送消息
            String result = HttpClientUtils.doPost(url, params);
            System.out.println(result);


            return  ResponseResult.success("验证码发送成功");
        } catch (Exception e) {
            //e.printStackTrace();
            return  ResponseResult.fail("验证码发送失败");
        }
    }
}


