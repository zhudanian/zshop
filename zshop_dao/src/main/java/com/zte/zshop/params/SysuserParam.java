package com.zte.zshop.params;

/**
 * Author:hellboy
 * Date:2018-11-12 15:16
 * Description:该类用于封装需要需要查询的字段
 * 注意点：对应表单中元素的name属性值和该bean中的属性名称一一对应，这样才可以通过springmvc完成数据的绑定
 */
public class SysuserParam {

    private String name;

    private String loginName;

    private String password;

    private String phone;

    private Integer roleId;

    private Integer isValid;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
