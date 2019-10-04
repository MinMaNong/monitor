package com.dejiacheng.common.exception.user;

/**
 * @author pcy
 * 用户账号已被删除
 * @version 创建时间:2019年9月5日-下午2:54:46
 */
public class UserDeleteException extends UserException
{
    private static final long serialVersionUID = 1L;

    public UserDeleteException()
    {
        super("user.password.delete", null);
    }
}
