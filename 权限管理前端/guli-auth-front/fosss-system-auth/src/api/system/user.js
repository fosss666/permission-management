import request from '@/utils/request'

const api_name='/admin/system/sysUser'

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
  addUser(sysUser){
    return request({
      url:`${api_name}`,
      method:'post',
      data:sysUser
    })
  },
  //根据id查询角色信息
  getUserById(id){
    return request({
      url:`${api_name}/${id}`,
      method:'get'
    })
  },
  //修改角色
  updateUser(sysUser){
    return request({
      url:`${api_name}`,
      method:'put',
      data:sysUser
    })
  },
  //批量删除
  removeUsers(ids){
    return request({
      url:`${api_name}`,
      method:'delete',
      data:ids
    })
  }
}











