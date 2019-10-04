package com.dejiacheng.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dejiacheng.system.mapper.SysServiceInfoMapper;
import com.dejiacheng.system.domain.SysServiceInfo;
import com.dejiacheng.system.service.ISysServiceInfoService;
import com.dejiacheng.common.core.text.Convert;

/**
 * 系统服务信息Service业务层处理
 * 
 * @author yukai
 * @date 2019-09-24
 */
@Service
public class SysServiceInfoServiceImpl implements ISysServiceInfoService 
{
    @Autowired
    private SysServiceInfoMapper sysServiceInfoMapper;

    /**
     * 查询系统服务信息
     * 
     * @param serviceId 系统服务信息ID
     * @return 系统服务信息
     */
    @Override
    public SysServiceInfo selectSysServiceInfoById(Integer serviceId)
    {
        return sysServiceInfoMapper.selectSysServiceInfoById(serviceId);
    }

    /**
     * 查询系统服务信息列表
     * 
     * @param sysServiceInfo 系统服务信息
     * @return 系统服务信息
     */
    @Override
    public List<SysServiceInfo> selectSysServiceInfoList(SysServiceInfo sysServiceInfo)
    {
        return sysServiceInfoMapper.selectSysServiceInfoList(sysServiceInfo);
    }

    /**
     * 新增系统服务信息
     * 
     * @param sysServiceInfo 系统服务信息
     * @return 结果
     */
    @Override
    public int insertSysServiceInfo(SysServiceInfo sysServiceInfo)
    {
        return sysServiceInfoMapper.insertSysServiceInfo(sysServiceInfo);
    }

    /**
     * 修改系统服务信息
     * 
     * @param sysServiceInfo 系统服务信息
     * @return 结果
     */
    @Override
    public int updateSysServiceInfo(SysServiceInfo sysServiceInfo)
    {
        return sysServiceInfoMapper.updateSysServiceInfo(sysServiceInfo);
    }

    /**
     * 删除系统服务信息对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysServiceInfoByIds(String ids)
    {
        return sysServiceInfoMapper.deleteSysServiceInfoByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除系统服务信息信息
     * 
     * @param serviceId 系统服务信息ID
     * @return 结果
     */
    public int deleteSysServiceInfoById(Integer serviceId)
    {
        return sysServiceInfoMapper.deleteSysServiceInfoById(serviceId);
    }
}
