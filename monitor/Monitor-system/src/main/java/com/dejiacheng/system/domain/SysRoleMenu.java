package com.dejiacheng.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author pcy
 * 角色和菜单关联 sys_role_menu
 * @version 创建时间:2019年9月5日-上午9:39:07
 */
public class SysRoleMenu
{
    /** 角色ID */
    private Long roleId;
    
    /** 菜单ID */
    private Long menuId;

    public Long getRoleId()
    {
        return roleId;
    }

    public void setRoleId(Long roleId)
    {
        this.roleId = roleId;
    }

    public Long getMenuId()
    {
        return menuId;
    }

    public void setMenuId(Long menuId)
    {
        this.menuId = menuId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("roleId", getRoleId())
            .append("menuId", getMenuId())
            .toString();
    }
}
