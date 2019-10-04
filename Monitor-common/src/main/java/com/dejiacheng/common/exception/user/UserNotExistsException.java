package com.dejiacheng.common.exception.user;

/**
 * @author pcy
 * 用户不存在异常类
 * @version 创建时间:2019年9月4日-下午5:27:12
 */
public class UserNotExistsException extends UserException
{
    private static final long serialVersionUID = 1L;

    public UserNotExistsException()
    {
        super("user.not.exists", null);
    }
}
