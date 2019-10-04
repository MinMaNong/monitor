package com.dejiacheng.common.exception.file;

import com.dejiacheng.common.exception.base.BaseException;

/**
 * @author pcy
 * 文件信息异常类
 * @version 创建时间:2019年9月6日-下午4:37:54
 */
public class FileException extends BaseException
{
    private static final long serialVersionUID = 1L;

    public FileException(String code, Object[] args)
    {
        super("file", code, args, null);
    }

}
