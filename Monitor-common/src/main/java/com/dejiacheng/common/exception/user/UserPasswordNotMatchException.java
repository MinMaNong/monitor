package com.dejiacheng.common.exception.user;


/**
 * @author pcy
 * 用户密码不正确或不符合规范异常类
 * @version 创建时间:2019年9月4日-下午5:28:22
 */
public class UserPasswordNotMatchException extends UserException
{
    private static final long serialVersionUID = 1L;

    public UserPasswordNotMatchException()
    {
        super("user.password.not.match", null);
    }
}
