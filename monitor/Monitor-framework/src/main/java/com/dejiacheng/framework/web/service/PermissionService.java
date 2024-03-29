package com.dejiacheng.framework.web.service;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

import com.dejiacheng.common.utils.security.PermissionUtils;

/**
 * @author pcy
 * RuoYi首创 js调用 thymeleaf 实现按钮权限可见性
 * @version 创建时间:2019年9月5日-下午3:40:22
 */
@Service("permission")
public class PermissionService
{
    public String hasPermi(String permission)
    {
        return isPermittedOperator(permission) ? "" : "hidden";
    }

    public String hasRole(String role)
    {
        return hasRoleOperator(role) ? "" : "hidden";
    }

    /**
     * 判断用户是否拥有某个权限
     * 
     * @param permission 权限字符串
     * @return 结果
     */
    private boolean isPermittedOperator(String permission)
    {
        return SecurityUtils.getSubject().isPermitted(permission);
    }

    /**
     * 判断用户是否拥有某个角色
     * 
     * @param role 角色字符串
     * @return 结果
     */
    private boolean hasRoleOperator(String role)
    {
        return SecurityUtils.getSubject().hasRole(role);
    }

    /**
     * 返回用户属性值
     *
     * @param property 属性名称
     * @return 用户属性值
     */
    public Object getPrincipalProperty(String property)
    {
        return PermissionUtils.getPrincipalProperty(property);
    }
}
