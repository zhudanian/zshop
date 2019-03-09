package com.zte.zshop.dao;

import com.zte.zshop.entity.Sysuser;
import com.zte.zshop.params.SysuserParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Author:hellboy
 * Date:2018-11-09 11:10
 * Description:<描述>
 */
public interface SysuserDao {

    public List<Sysuser> selectAll();

    public Sysuser selectById(int id);

    public void insert(Sysuser sysuser);

    public void update(Sysuser sysuser);

    public void updateStatus(@Param("id")int id,@Param("isValid")int isValid);


    public List<Sysuser> selectByParams(SysuserParam sysuserParam);

    public Sysuser selectByName(String loginName);

    public Sysuser selectByLoginNameAndPassword(@Param("loginName")String loginName, @Param("password")String password, @Param("isValid")int isValid);
}
