package com.dejiacheng.system.service;

import com.dejiacheng.system.domain.SysAlarmInfo;
import java.util.List;

/**
 * 系统报警信息Service接口
 * 
 * @author yukai
 * @date 2019-10-06
 */
public interface ISysAlarmInfoService 
{
    /**
     * 查询系统报警信息
     * 
     * @param alarmId 系统报警信息ID
     * @return 系统报警信息
     */
    public SysAlarmInfo selectSysAlarmInfoById(Long alarmId);

    /**
     * 查询系统报警信息列表
     * 
     * @param sysAlarmInfo 系统报警信息
     * @return 系统报警信息集合
     */
    public List<SysAlarmInfo> selectSysAlarmInfoList(SysAlarmInfo sysAlarmInfo);

    /**
     * 新增系统报警信息
     * 
     * @param sysAlarmInfo 系统报警信息
     * @return 结果
     */
    public int insertSysAlarmInfo(SysAlarmInfo sysAlarmInfo);

    /**
     * 修改系统报警信息
     * 
     * @param sysAlarmInfo 系统报警信息
     * @return 结果
     */
    public int updateSysAlarmInfo(SysAlarmInfo sysAlarmInfo);

    /**
     * 批量删除系统报警信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysAlarmInfoByIds(String ids);

    /**
     * 删除系统报警信息信息
     * 
     * @param alarmId 系统报警信息ID
     * @return 结果
     */
    public int deleteSysAlarmInfoById(Long alarmId);
}
