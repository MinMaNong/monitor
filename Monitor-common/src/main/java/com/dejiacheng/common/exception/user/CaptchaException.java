package com.dejiacheng.common.exception.user;

/**
 * @author pcy
 * 验证码错误异常类x`
 * @version 创建时间:2019年9月4日-下午4:44:12
 */
public class CaptchaException extends UserException
{
    private static final long serialVersionUID = 1L;

    public CaptchaException()
    {
        super("user.jcaptcha.error", null);
    }
}
