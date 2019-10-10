package com.dejiacheng.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.dejiacheng.common.annotation.Excel;
import com.dejiacheng.common.core.domain.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

/**
 * 系统设备通道信息对象 sys_channel_info
 * 
 * @author yukai
 * @date 2019-10-06
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("通道实体")
//public class SysChannelInfo
public class SysChannelInfo
{
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("通道ID")
    /** 通道ID */
    private Long channelId;

    @ApiModelProperty("摄像头类型")
    /** 摄像头类型 */
    @Excel(name = "摄像头类型")
    private Integer ncameratype;

    @ApiModelProperty("通道编码")
    /** 通道编码 */
    @Excel(name = "通道编码")
    private String channelcode;

    @ApiModelProperty("通道名称")
    /** 通道名称 */
    @Excel(name = "通道名称")
    private String channelname;

    @ApiModelProperty("通道类型")
    /** 通道类型 */
    @Excel(name = "通道类型")
    private Integer nchnltype;

    @ApiModelProperty("通道状态")
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

//    @Override
//    public String toString() {
//        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
//            .append("channelId", getChannelId())
//            .append("ncameratype", getNcameratype())
//            .append("channelcode", getChannelcode())
//            .append("channelname", getChannelname())
//            .append("nchnltype", getNchnltype())
//            .append("status", getStatus())
//            .toString();
//    }
}
