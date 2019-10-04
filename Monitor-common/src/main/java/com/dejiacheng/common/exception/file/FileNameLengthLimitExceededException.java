package com.dejiacheng.common.exception.file;

/**
 * @author pcy
 * 文件名称超长限制异常类
 * @version 创建时间:2019年9月6日-下午4:38:10
 */
public class FileNameLengthLimitExceededException extends FileException
{
    private static final long serialVersionUID = 1L;

    public FileNameLengthLimitExceededException(int defaultFileNameLength)
    {
        super("upload.filename.exceed.length", new Object[] { defaultFileNameLength });
    }
}
