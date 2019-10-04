package com.dejiacheng.common.exception.user;

import com.dejiacheng.common.exception.base.BaseException;

/**
 * @author pcy
 * 用户信息异常类
 * @version 创建时间:2019年9月4日-下午5:21:27
 */
public class UserException extends BaseException
{
    private static final long serialVersionUID = 1L;

    public UserException(String code, Object[] args)
    {
        super("user", code, args, null);
    }
}
