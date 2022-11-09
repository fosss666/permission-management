package com.fosss.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fosss.model.system.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author fosss
 * @since 2022-11-06
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {
    //根据用户id查询其菜单权限
    List<SysMenu> selectMenuByUserId(@Param("userId") String id);
}
