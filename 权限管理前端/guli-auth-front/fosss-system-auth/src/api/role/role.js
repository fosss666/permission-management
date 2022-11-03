import request from '@/utils/request'

export default {
  //列表功能
  getPageList(page,limit,searchObj){
    return{
      url:`/admin/system/sysRole/${page}/${limit}`,
      method:get,
      data:searchObj
    }
  }
}
