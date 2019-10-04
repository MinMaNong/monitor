package com.dejiacheng.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.dejiacheng.common.annotation.Excel;
import com.dejiacheng.common.core.domain.BaseEntity;

/**
 * 系统设备信息对象 sys_device_info
 * 
 * @author yukai
 * @date 2019-09-24
 */
public class SysDeviceInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 摄像头ID */
    private Long deviceId;

    /** 摄像头用户名 */
    @Excel(name = "摄像头用户名")
    private String username;

    /** 摄像头密码 */
    @Excel(name = "摄像头密码")
    private String password;

    /** 码流形式 */
    @Excel(name = "码流形式")
    private Integer substream;

    /** 摄像头状态 */
    @Excel(name = "摄像头状态")
    private Integer status;

    /** 摄像头类型 */
    @Excel(name = "摄像头类型")
    private String type;

    public void setDeviceId(Long deviceId) 
    {
        this.deviceId = deviceId;
    }

    public Long getDeviceId() 
    {
        return deviceId;
    }
    public void setUsername(String username) 
    {
        this.username = username;
    }

    public String getUsername() 
    {
        return username;
    }
    public void setPassword(String password) 
    {
        this.password = password;
    }

    public String getPassword() 
    {
        return password;
    }
    public void setSubstream(Integer substream) 
    {
        this.substream = substream;
    }

    public Integer getSubstream() 
    {
        return substream;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }
    public void setType(String type) 
    {
        this.type = type;
    }

    public String getType() 
    {
        return type;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("deviceId", getDeviceId())
            .append("username", getUsername())
            .append("password", getPassword())
            .append("substream", getSubstream())
            .append("status", getStatus())
            .append("type", getType())
            .toString();
    }
}
