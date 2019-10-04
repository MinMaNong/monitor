package com.dejiacheng.common.exception.user;

/**
 * @author pcy
 * 用户锁定异常类
 * @version 创建时间:2019年9月4日-下午5:26:05
 */
public class UserBlockedException extends UserException
{
    private static final long serialVersionUID = 1L;

    public UserBlockedException()
    {
        super("user.blocked", null);
    }
}
