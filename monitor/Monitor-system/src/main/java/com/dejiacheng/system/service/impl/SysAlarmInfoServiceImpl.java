package com.dejiacheng.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dejiacheng.system.mapper.SysAlarmInfoMapper;
import com.dejiacheng.system.domain.SysAlarmInfo;
import com.dejiacheng.system.service.ISysAlarmInfoService;
import com.dejiacheng.common.core.text.Convert;

/**
 * 系统报警信息Service业务层处理
 * 
 * @author yukai
 * @date 2019-10-06
 */
@Service
public class SysAlarmInfoServiceImpl implements ISysAlarmInfoService 
{
    @Autowired
    private SysAlarmInfoMapper sysAlarmInfoMapper;

    /**
     * 查询系统报警信息
     * 
     * @param alarmId 系统报警信息ID
     * @return 系统报警信息
     */
    @Override
    public SysAlarmInfo selectSysAlarmInfoById(Long alarmId)
    {
        return sysAlarmInfoMapper.selectSysAlarmInfoById(alarmId);
    }

    /**
     * 查询系统报警信息列表
     * 
     * @param sysAlarmInfo 系统报警信息
     * @return 系统报警信息
     */
    @Override
    public List<SysAlarmInfo> selectSysAlarmInfoList(SysAlarmInfo sysAlarmInfo)
    {
        return sysAlarmInfoMapper.selectSysAlarmInfoList(sysAlarmInfo);
    }

    /**
     * 新增系统报警信息
     * 
     * @param sysAlarmInfo 系统报警信息
     * @return 结果
     */
    @Override
    public int insertSysAlarmInfo(SysAlarmInfo sysAlarmInfo)
    {
        return sysAlarmInfoMapper.insertSysAlarmInfo(sysAlarmInfo);
    }

    /**
     * 修改系统报警信息
     * 
     * @param sysAlarmInfo 系统报警信息
     * @return 结果
     */
    @Override
    public int updateSysAlarmInfo(SysAlarmInfo sysAlarmInfo)
    {
        return sysAlarmInfoMapper.updateSysAlarmInfo(sysAlarmInfo);
    }

    /**
     * 删除系统报警信息对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysAlarmInfoByIds(String ids)
    {
        return sysAlarmInfoMapper.deleteSysAlarmInfoByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除系统报警信息信息
     * 
     * @param alarmId 系统报警信息ID
     * @return 结果
     */
    @Override
    public int deleteSysAlarmInfoById(Long alarmId)
    {
        return sysAlarmInfoMapper.deleteSysAlarmInfoById(alarmId);
    }
}
