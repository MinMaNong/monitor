package com.dejiacheng.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.dejiacheng.common.annotation.Excel;
import com.dejiacheng.common.core.domain.BaseEntity;

/**
 * 系统设备通道信息对象 sys_channel_info
 * 
 * @author yukai
 * @date 2019-10-06
 */
public class SysChannelInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 通道ID */
    private Long channelId;

    /** 摄像头类型 */
    @Excel(name = "摄像头类型")
    private Integer ncameratype;

    /** 通道编码 */
    @Excel(name = "通道编码")
    private String channelcode;

    /** 通道名称 */
    @Excel(name = "通道名称")
    private String channelname;

    /** 通道类型 */
    @Excel(name = "通道类型")
    private Integer nchnltype;

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
    public void setNcameratype(Integer ncameratype) 
    {
        this.ncameratype = ncameratype;
    }

    public Integer getNcameratype() 
    {
        return ncameratype;
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
    public void setNchnltype(Integer nchnltype) 
    {
        this.nchnltype = nchnltype;
    }

    public Integer getNchnltype() 
    {
        return nchnltype;
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
            .append("ncameratype", getNcameratype())
            .append("channelcode", getChannelcode())
            .append("channelname", getChannelname())
            .append("nchnltype", getNchnltype())
            .append("status", getStatus())
            .toString();
    }
}
