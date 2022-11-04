import request from '@/utils/request'

export default {
  //列表功能
  getPageList(page,limit,searchObj){
    return request({
      url:`/admin/system/sysRole/${page}/${limit}`,
      method:'get',
      params:searchObj
    })
  },
  //根据id删除功能
  removeDataById(id){
    return request({
      url:`/admin/system/sysRole/${id}`,
      method:'delete'
    })
  }
}











