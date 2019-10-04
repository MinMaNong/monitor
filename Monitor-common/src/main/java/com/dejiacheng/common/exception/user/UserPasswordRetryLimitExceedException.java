package com.dejiacheng.common.exception.user;


/**
 * @author pcy
 * 用户错误最大次数异常类
 * @version 创建时间:2019年9月4日-下午5:29:04
 */
public class UserPasswordRetryLimitExceedException extends UserException
{
    private static final long serialVersionUID = 1L;

    public UserPasswordRetryLimitExceedException(int retryLimitCount)
    {
        super("user.password.retry.limit.exceed", new Object[] { retryLimitCount });
    }
}
