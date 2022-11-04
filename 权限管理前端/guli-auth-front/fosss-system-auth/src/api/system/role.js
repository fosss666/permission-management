import request from '@/utils/request'

const api_name='/admin/system/sysRole'

export default {

  //列表功能
  getPageList(page,limit,searchObj){
    return request({
      url:`${api_name}/${page}/${limit}`,
      method:'get',
      params:searchObj
    })
  },
  //根据id删除功能
  removeDataById(id){
    return request({
      url:`${api_name}/${id}`,
      method:'delete'
    })
  },
  //添加
  addRole(sysRole){
    return request({
      url:`${api_name}`,
      method:'post',
      data:sysRole
    })
  },
  //根据id查询角色信息
  getRoleById(id){
    return request({
      url:`${api_name}/${id}`,
      method:'get'
    })
  },
  //修改角色
  updateRole(sysRole){
    return request({
      url:`${api_name}`,
      method:'put',
      data:sysRole
    })
  }
}











