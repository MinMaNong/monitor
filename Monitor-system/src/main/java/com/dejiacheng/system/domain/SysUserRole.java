package com.dejiacheng.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author pcy
 * 用户和角色关联 sys_user_role
 * @version 创建时间:2019年9月5日-上午10:11:45
 */
public class SysUserRole
{
    /** 用户ID */
    private Long userId;
    
    /** 角色ID */
    private Long roleId;

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Long getRoleId()
    {
        return roleId;
    }

    public void setRoleId(Long roleId)
    {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("userId", getUserId())
            .append("roleId", getRoleId())
            .toString();
    }
}
