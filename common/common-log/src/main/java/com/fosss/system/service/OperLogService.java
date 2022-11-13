package com.fosss.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fosss.model.system.SysOperLog;
import com.fosss.model.vo.SysOperLogQueryVo;
import org.springframework.stereotype.Repository;

@Repository
public interface OperLogService{

    public void saveSysLog(SysOperLog sysOperLog);

    //操作日志分页查询
    IPage<SysOperLog> selectPage(Long page, Long limit, SysOperLogQueryVo sysOperLogQueryVo);
}
