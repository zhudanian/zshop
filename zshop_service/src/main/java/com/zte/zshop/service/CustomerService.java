package com.zte.zshop.service;

import com.zte.zshop.entity.Customer;
import com.zte.zshop.exception.LoginErrorException;
import com.zte.zshop.exception.PhoneNotExistException;

/**
 * Author:hellboy
 * Date:2018-11-16 16:14
 * Description:<描述>
 */
public interface CustomerService {
    public Customer login(String loginName, String password) throws LoginErrorException;

    public Customer findByPhone(String phone) throws PhoneNotExistException;
}
