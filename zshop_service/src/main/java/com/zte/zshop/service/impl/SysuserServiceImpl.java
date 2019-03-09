package com.zte.zshop.service.impl;

import com.zte.zshop.Constants.Constant;
import com.zte.zshop.dao.SysuserDao;
import com.zte.zshop.entity.Role;
import com.zte.zshop.entity.Sysuser;
import com.zte.zshop.exception.SysuserNotExistException;
import com.zte.zshop.params.SysuserParam;
import com.zte.zshop.service.SysuserService;
import com.zte.zshop.vo.SysuserVo;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

/**
 * Author:hellboy
 * Date:2018-11-09 11:26
 * Description:<描述>
 */
@Service
@Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
public class SysuserServiceImpl implements SysuserService {

    @Autowired
    private SysuserDao sysuserDao;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<Sysuser> findAll() {
        return sysuserDao.selectAll();
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Sysuser findById(int id) {
        return sysuserDao.selectById(id);
    }

    @Override
    public void add(SysuserVo sysuserVo) {

        Sysuser sysuser = new Sysuser();

        try {
            //将vo中的数据拷贝到pojo中
            PropertyUtils.copyProperties(sysuser,sysuserVo);

            //默认用户为启用状态
            sysuser.setIsValid(Constant.SYSUSER_VALID);
            //默认时间为当前时间
            sysuser.setCreateDate(new Date());
            //设置角色id
            Role role = new Role();
            role.setId(sysuserVo.getRoleId());
            sysuser.setRole(role);

            sysuserDao.insert(sysuser);


        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void modify(SysuserVo sysuserVo) {

    }

    @Override
    public void modifyStatus(int id) {
        Sysuser sysuser = sysuserDao.selectById(id);
        Integer isValid = sysuser.getIsValid();
        if(isValid==Constant.SYSUSER_VALID){
            isValid=Constant.SYSUSER_INVALID;
        }
        else{
            isValid=Constant.SYSUSER_VALID;
        }
        sysuserDao.updateStatus(id,isValid);


    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<Sysuser> findByParams(SysuserParam sysuserParam) {
        return sysuserDao.selectByParams(sysuserParam);
    }

    @Override
    public boolean checkName(String loginName) {

        Sysuser sysuser= sysuserDao.selectByName(loginName);
        if(sysuser!=null){
            return false;
        }
        return true;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Sysuser findByLoginNameAndPassowrd(String loginName, String password) throws SysuserNotExistException {
        Sysuser sysuser= sysuserDao.selectByLoginNameAndPassword(loginName, password,Constant.SYSUSER_VALID);
        if(sysuser!=null){
            return sysuser;
        }

        throw  new SysuserNotExistException("用户或密码不正确");
    }
}
