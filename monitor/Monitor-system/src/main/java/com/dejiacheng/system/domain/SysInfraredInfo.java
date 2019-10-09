package com.dejiacheng.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.dejiacheng.common.annotation.Excel;
import com.dejiacheng.common.core.domain.TreeEntity;

/**
 * 红外设备信息对象 sys_infrared_info
 * 
 * @author yukai
 * 
 * @date 2019-10-09
 */
public class SysInfraredInfo extends TreeEntity
{
    private static final long serialVersionUID = 1L;

    /** 红外id */
    private Long infraredId;

    /** 父id */
    @Excel(name = "父id")
    private Long parentsId;

    /** 红外名称 */
    @Excel(name = "红外名称")
    private String infraredName;

    /** 祖级列表 */
    @Excel(name = "祖级列表")
    private Long channelId;
    
    /** 通道id */
    @Excel(name = "通道id")
    private String ancestors;

    public String getAncestors() {
		return ancestors;
	}

	public void setAncestors(String ancestors) {
		this.ancestors = ancestors;
	}

	/** 状态(0:正常，1:异常) */
    @Excel(name = "状态(0:正常，1:异常)")
    private Integer infraredStatus;

    public void setInfraredId(Long infraredId) 
    {
        this.infraredId = infraredId;
    }

    public Long getInfraredId() 
    {
        return infraredId;
    }
    public void setParentsId(Long parentsId) 
    {
        this.parentsId = parentsId;
    }

    public Long getParentsId() 
    {
        return parentsId;
    }
    public void setInfraredName(String infraredName) 
    {
        this.infraredName = infraredName;
    }

    public String getInfraredName() 
    {
        return infraredName;
    }
    public void setChannelId(Long channelId) 
    {
        this.channelId = channelId;
    }

    public Long getChannelId() 
    {
        return channelId;
    }
    public void setInfraredStatus(Integer infraredStatus) 
    {
        this.infraredStatus = infraredStatus;
    }

    public Integer getInfraredStatus() 
    {
        return infraredStatus;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("infraredId", getInfraredId())
            .append("parentsId", getParentsId())
            .append("ancestors", getAncestors())
            .append("infraredName", getInfraredName())
            .append("orderNum", getOrderNum())
            .append("channelId", getChannelId())
            .append("infraredStatus", getInfraredStatus())
            .toString();
    }
}
