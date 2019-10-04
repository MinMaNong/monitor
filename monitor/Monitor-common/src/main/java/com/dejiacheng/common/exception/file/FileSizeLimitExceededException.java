package com.dejiacheng.common.exception.file;

/**
 * @author pcy
 * 文件名大小限制异常类
 * @version 创建时间:2019年9月6日-下午4:38:22
 */
public class FileSizeLimitExceededException extends FileException
{
    private static final long serialVersionUID = 1L;

    public FileSizeLimitExceededException(long defaultMaxSize)
    {
        super("upload.exceed.maxSize", new Object[] { defaultMaxSize });
    }
}
