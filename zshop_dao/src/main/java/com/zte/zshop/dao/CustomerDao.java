package com.zte.zshop.dao;

import com.zte.zshop.entity.Customer;
import org.apache.ibatis.annotations.Param;

/**
 * Author:hellboy
 * Date:2018-11-16 16:20
 * Description:<描述>
 */
public interface CustomerDao {
    public Customer selectByLoginNameAndPass(@Param("loginName")String loginName, @Param("password")String password,@Param("isValid")Integer isValid);

    public Customer selectByPhone(String phone);
}
