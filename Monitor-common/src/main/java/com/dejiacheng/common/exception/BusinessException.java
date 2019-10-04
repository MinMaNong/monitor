package com.dejiacheng.common.exception;

/**
 * @author pcy
 * 业务异常
 * @version 创建时间:2019年9月5日-下午3:08:10
 */
public class BusinessException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    protected final String message;

    public BusinessException(String message)
    {
        this.message = message;
    }

    public BusinessException(String message, Throwable e)
    {
        super(message, e);
        this.message = message;
    }

    @Override
    public String getMessage()
    {
        return message;
    }
}
