package com.zte.zshop.service;

import com.zte.zshop.entity.Sysuser;
import com.zte.zshop.exception.SysuserNotExistException;
import com.zte.zshop.params.SysuserParam;
import com.zte.zshop.vo.SysuserVo;

import java.util.List;

/**
 * Author:hellboy
 * Date:2018-11-09 11:25
 * Description:<描述>
 */
public interface SysuserService {

    public List<Sysuser> findAll();

    public Sysuser findById(int id);

    public void add(SysuserVo sysuserVo);

    public void modify(SysuserVo sysuserVo);

    public void modifyStatus(int id);


    public List<Sysuser> findByParams(SysuserParam sysuserParam);

    public boolean checkName(String loginName);

    public Sysuser findByLoginNameAndPassowrd(String loginName, String password)throws SysuserNotExistException;
}
