package com.dejiacheng.common.exception.user;

/**
 * @author pcy
 * 角色锁定异常类
 * @version 创建时间:2019年9月4日-下午5:24:58
 */
public class RoleBlockedException extends UserException
{
    private static final long serialVersionUID = 1L;

    public RoleBlockedException()
    {
        super("role.blocked", null);
    }
}
