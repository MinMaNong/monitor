package com.dejiacheng.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.dejiacheng.common.annotation.Excel;
import com.dejiacheng.common.core.domain.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * 系统报警信息对象 sys_alarm_info
 * 
 * @author yukai
 * @date 2019-10-06
 */
@ApiModel("报警信息实体")
//public class SysAlarmInfo extends BaseEntity
public class SysAlarmInfo
{
    private static final long serialVersionUID = 1L;

    /** 报警id，主键 */
    @ApiModelProperty("报警id")
    private Long alarmId;

    /** 下发人员 */
    @Excel(name = "下发人员")
    @ApiModelProperty("下发人员")
    private String alarmPerson;

    /** 时间 */
    @Excel(name = "时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty("时间")
    private Date alarmTime;

    /** 位置 */
    @Excel(name = "位置")
    @ApiModelProperty("位置")
    private String alarmPlace;

    /** 类型 */
    @Excel(name = "类型")
    @ApiModelProperty("类型")
    private String alarmType;

    /** 处理人员 */
    @Excel(name = "处理人员")
    @ApiModelProperty("处理人员")
    private String solvePerson;

    /** 处理状态 */
    @Excel(name = "处理状态")
    @ApiModelProperty("处理状态")
    private String solveStatus;

    public void setAlarmId(Long alarmId) 
    {
        this.alarmId = alarmId;
    }

    public Long getAlarmId() 
    {
        return alarmId;
    }
    public void setAlarmPerson(String alarmPerson) 
    {
        this.alarmPerson = alarmPerson;
    }

    public String getAlarmPerson() 
    {
        return alarmPerson;
    }
    public void setAlarmTime(Date alarmTime) 
    {
        this.alarmTime = alarmTime;
    }

    public Date getAlarmTime() 
    {
        return alarmTime;
    }
    public void setAlarmPlace(String alarmPlace) 
    {
        this.alarmPlace = alarmPlace;
    }

    public String getAlarmPlace() 
    {
        return alarmPlace;
    }
    public void setAlarmType(String alarmType) 
    {
        this.alarmType = alarmType;
    }

    public String getAlarmType() 
    {
        return alarmType;
    }
    public void setSolvePerson(String solvePerson) 
    {
        this.solvePerson = solvePerson;
    }

    public String getSolvePerson() 
    {
        return solvePerson;
    }
    public void setSolveStatus(String solveStatus) 
    {
        this.solveStatus = solveStatus;
    }

    public String getSolveStatus() 
    {
        return solveStatus;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("alarmId", getAlarmId())
            .append("alarmPerson", getAlarmPerson())
            .append("alarmTime", getAlarmTime())
            .append("alarmPlace", getAlarmPlace())
            .append("alarmType", getAlarmType())
            .append("solvePerson", getSolvePerson())
            .append("solveStatus", getSolveStatus())
            .toString();
    }
}
