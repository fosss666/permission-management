import request from '@/utils/request'

const api_name = '/admin/system/sysRole'

export default {

  //列表功能
  getPageList(page, limit, searchObj) {
    return request({
      url: `${api_name}/${page}/${limit}`,
      method: 'get',
      params: searchObj
    })
  },
  //根据id删除功能
  removeDataById(id) {
    return request({
      url: `${api_name}/${id}`,
      method: 'delete'
    })
  },
  //添加
  addRole(sysRole) {
    return request({
      url: `${api_name}`,
      method: 'post',
      data: sysRole
    })
  },
  //根据id查询角色信息
  getRoleById(id) {
    return request({
      url: `${api_name}/${id}`,
      method: 'get'
    })
  },
  //修改角色
  updateRole(sysRole) {
    return request({
      url: `${api_name}`,
      method: 'put',
      data: sysRole
    })
  },
  //批量删除
  removeRoles(ids) {
    return request({
      url: `${api_name}`,
      method: 'delete',
      data: ids
    })
  },

  //查看某个角色的权限列表
  toAssign(roleId) {
    return request({
      url: `${api_name}/doAssign/${roleId}`,
      method: 'get'
    })
  },

  //给某个角色授权
  doAssign(assignMenuVo) {
    return request({
      url: `${api_name}/doAssign`,
      method: "post",
      data: assignMenuVo
    })
  }
}











