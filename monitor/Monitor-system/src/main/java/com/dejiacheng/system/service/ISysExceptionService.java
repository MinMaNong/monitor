package com.dejiacheng.system.service;

import com.dejiacheng.system.domain.SysException;
import java.util.List;

/**
 * 系统异常信息Service接口
 * 
 * @author yukai
 * @date 2019-09-28
 */
public interface ISysExceptionService 
{
    /**
     * 查询系统异常信息
     * 
     * @param id 系统异常信息ID
     * @return 系统异常信息
     */
    public SysException selectSysExceptionById(Long id);

    /**
     * 根据code查询系统异常信息
     * 
     * @param code 系统异常信息code
     * @return 系统异常信息
     */
    public SysException selectSysExceptionByCode(String code);
    
    
    /**
     * 查询系统异常信息列表
     * 
     * @param sysException 系统异常信息
     * @return 系统异常信息集合
     */
    public List<SysException> selectSysExceptionList(SysException sysException);

    /**
     * 新增系统异常信息
     * 
     * @param sysException 系统异常信息
     * @return 结果
     */
    public int insertSysException(SysException sysException);

    /**
     * 修改系统异常信息
     * 
     * @param sysException 系统异常信息
     * @return 结果
     */
    public int updateSysException(SysException sysException);

    /**
     * 批量删除系统异常信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysExceptionByIds(String ids);

    /**
     * 删除系统异常信息信息
     * 
     * @param id 系统异常信息ID
     * @return 结果
     */
    public int deleteSysExceptionById(Long id);
}
