package com.dejiacheng.common.enums;

/**
 * @author pcy
 * 用户状态
 * @version 创建时间:2019年9月4日-上午10:58:05
 */
public enum UserStatus
{
    OK("0", "正常"), DISABLE("1", "停用"), DELETED("2", "删除");

    private final String code;
    private final String info;

    UserStatus(String code, String info)
    {
        this.code = code;
        this.info = info;
    }

    public String getCode()
    {
        return code;
    }

    public String getInfo()
    {
        return info;
    }
}
