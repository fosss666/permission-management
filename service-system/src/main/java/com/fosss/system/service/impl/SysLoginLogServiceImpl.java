package com.fosss.system.service.impl;

import com.fosss.model.system.SysLoginLog;
import com.fosss.system.mapper.SysLoginLogMapper;
import com.fosss.system.service.SysLoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysLoginLogServiceImpl implements SysLoginLogService {
    @Autowired
    private SysLoginLogMapper sysLoginLogMapper;
    @Override
    public void recordLoginLog(String username, Integer status, String ipaddr, String message) {
        SysLoginLog sysLoginLog = new SysLoginLog();
        sysLoginLog.setUsername(username);
        sysLoginLog.setStatus(status);
        sysLoginLog.setIpaddr(ipaddr);
        sysLoginLog.setMsg(message);
        sysLoginLogMapper.insert(sysLoginLog);
    }
}
















