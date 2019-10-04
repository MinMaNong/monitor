package com.dejiacheng.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dejiacheng.system.mapper.SysExceptionMapper;
import com.dejiacheng.system.domain.SysException;
import com.dejiacheng.system.service.ISysExceptionService;
import com.dejiacheng.common.core.text.Convert;

/**
 * 系统异常信息Service业务层处理
 * 
 * @author yukai
 * @date 2019-09-28
 */
@Service
public class SysExceptionServiceImpl implements ISysExceptionService 
{
    @Autowired
    private SysExceptionMapper sysExceptionMapper;

    /**
     * 查询系统异常信息
     * 
     * @param id 系统异常信息ID
     * @return 系统异常信息
     */
    @Override
    public SysException selectSysExceptionById(Long id)
    {
        return sysExceptionMapper.selectSysExceptionById(id);
    }

    /**
     * 根据code查询系统异常信息
     * 
     * @param code 系统异常信息code
     * @return 系统异常信息
     */
    @Override
    public SysException selectSysExceptionByCode(String code) {
    	return sysExceptionMapper.selectSysExceptionByCode(code);
    }
    
    /**
     * 查询系统异常信息列表
     * 
     * @param sysException 系统异常信息
     * @return 系统异常信息
     */
    @Override
    public List<SysException> selectSysExceptionList(SysException sysException)
    {
        return sysExceptionMapper.selectSysExceptionList(sysException);
    }

    /**
     * 新增系统异常信息
     * 
     * @param sysException 系统异常信息
     * @return 结果
     */
    @Override
    public int insertSysException(SysException sysException)
    {
        return sysExceptionMapper.insertSysException(sysException);
    }

    /**
     * 修改系统异常信息
     * 
     * @param sysException 系统异常信息
     * @return 结果
     */
    @Override
    public int updateSysException(SysException sysException)
    {
        return sysExceptionMapper.updateSysException(sysException);
    }
    /**
     * 删除系统异常信息对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysExceptionByIds(String ids)
    {
        return sysExceptionMapper.deleteSysExceptionByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除系统异常信息信息
     * 
     * @param id 系统异常信息ID
     * @return 结果
     */
    public int deleteSysExceptionById(Long id)
    {
        return sysExceptionMapper.deleteSysExceptionById(id);
    }
}
