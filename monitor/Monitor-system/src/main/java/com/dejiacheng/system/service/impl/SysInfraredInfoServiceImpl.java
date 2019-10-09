package com.dejiacheng.system.service.impl;

import java.util.List;
import java.util.ArrayList;
import com.dejiacheng.common.core.domain.Ztree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dejiacheng.system.mapper.SysInfraredInfoMapper;
import com.dejiacheng.system.domain.SysInfraredInfo;
import com.dejiacheng.system.service.ISysInfraredInfoService;
import com.dejiacheng.common.core.text.Convert;

/**
 * 红外设备信息Service业务层处理
 * 
 * @author yukai
 * @date 2019-10-09
 */
@Service
public class SysInfraredInfoServiceImpl implements ISysInfraredInfoService 
{
    @Autowired
    private SysInfraredInfoMapper sysInfraredInfoMapper;

    /**
     * 查询红外设备信息
     * 
     * @param infraredId 红外设备信息ID
     * @return 红外设备信息
     */
    @Override
    public SysInfraredInfo selectSysInfraredInfoById(Long infraredId)
    {
        return sysInfraredInfoMapper.selectSysInfraredInfoById(infraredId);
    }

    /**
     * 查询红外设备信息列表
     * 
     * @param sysInfraredInfo 红外设备信息
     * @return 红外设备信息
     */
    @Override
    public List<SysInfraredInfo> selectSysInfraredInfoList(SysInfraredInfo sysInfraredInfo)
    {
        return sysInfraredInfoMapper.selectSysInfraredInfoList(sysInfraredInfo);
    }

    /**
     * 新增红外设备信息
     * 
     * @param sysInfraredInfo 红外设备信息
     * @return 结果
     */
    @Override
    public int insertSysInfraredInfo(SysInfraredInfo sysInfraredInfo)
    {
        return sysInfraredInfoMapper.insertSysInfraredInfo(sysInfraredInfo);
    }

    /**
     * 修改红外设备信息
     * 
     * @param sysInfraredInfo 红外设备信息
     * @return 结果
     */
    @Override
    public int updateSysInfraredInfo(SysInfraredInfo sysInfraredInfo)
    {
        return sysInfraredInfoMapper.updateSysInfraredInfo(sysInfraredInfo);
    }

    /**
     * 删除红外设备信息对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysInfraredInfoByIds(String ids)
    {
        return sysInfraredInfoMapper.deleteSysInfraredInfoByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除红外设备信息信息
     * 
     * @param infraredId 红外设备信息ID
     * @return 结果
     */
    @Override
    public int deleteSysInfraredInfoById(Long infraredId)
    {
        return sysInfraredInfoMapper.deleteSysInfraredInfoById(infraredId);
    }

    /**
     * 查询红外设备信息树列表
     * 
     * @return 所有红外设备信息信息
     */
    @Override
    public List<Ztree> selectSysInfraredInfoTree()
    {
        List<SysInfraredInfo> sysInfraredInfoList = sysInfraredInfoMapper.selectSysInfraredInfoList(new SysInfraredInfo());
        List<Ztree> ztrees = new ArrayList<Ztree>();
        for (SysInfraredInfo sysInfraredInfo : sysInfraredInfoList)
        {
            Ztree ztree = new Ztree();
            ztree.setId(sysInfraredInfo.getInfraredId());
            ztree.setpId(sysInfraredInfo.getParentsId());
            ztree.setName(sysInfraredInfo.getInfraredName());
            ztree.setTitle(sysInfraredInfo.getInfraredName());
            ztrees.add(ztree);
        }
        return ztrees;
    }
}
