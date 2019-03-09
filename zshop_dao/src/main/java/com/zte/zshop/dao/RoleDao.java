package com.zte.zshop.dao;

import com.zte.zshop.entity.Role;

import java.util.List;

/**
 * Author:hellboy
 * Date:2018-11-09 15:17
 * Description:<描述>
 */
public interface RoleDao {

    //获取所有角色
    public List<Role> selectAll();
}
