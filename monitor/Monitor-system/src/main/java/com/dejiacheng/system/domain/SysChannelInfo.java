package com.dejiacheng.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.dejiacheng.common.annotation.Excel;
import com.dejiacheng.common.core.domain.BaseEntity;

/**
 * 系统设备通道信息对象 sys_channel_info
 * 
 * @author yukai
 * @date 2019-09-24
 */
public class SysChannelInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 通道ID */
    private Long channelId;

    /** 设备ID */
    @Excel(name = "设备ID")
    private Long deviceId;

    /** 通道编码 */
    @Excel(name = "通道编码")
    private String channelcode;

    /** 通道名称 */
    @Excel(name = "通道名称")
    private String channelname;

    /** 通道状态 */
    @Excel(name = "通道状态")
    private Integer status;

    public void setChannelId(Long channelId) 
    {
        this.channelId = channelId;
    }

    public Long getChannelId() 
    {
        return channelId;
    }
    public void setDeviceId(Long deviceId) 
    {
        this.deviceId = deviceId;
    }

    public Long getDeviceId() 
    {
        return deviceId;
    }
    public void setChannelcode(String channelcode) 
    {
        this.channelcode = channelcode;
    }

    public String getChannelcode() 
    {
        return channelcode;
    }
    public void setChannelname(String channelname) 
    {
        this.channelname = channelname;
    }

    public String getChannelname() 
    {
        return channelname;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("channelId", getChannelId())
            .append("deviceId", getDeviceId())
            .append("channelcode", getChannelcode())
            .append("channelname", getChannelname())
            .append("status", getStatus())
            .toString();
    }
}
