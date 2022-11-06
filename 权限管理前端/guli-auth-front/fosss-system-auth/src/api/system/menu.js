import request from '@/utils/request'

/*
菜单管理相关的API请求函数
*/
const api_name = '/admin/system/sysMenu'

export default {

  /*
  获取权限(菜单/功能)列表
  */
  findNodes() {
    return request({
      url: `${api_name}`,
      method: 'get'
    })
  },

  /*
  删除
  */
  removeById(id) {
    return request({
      url: `${api_name}/${id}`,
      method: "delete"
    })
  },

  /*
  保存
  */
  save(sysMenu) {
    return request({
      url: `${api_name}`,
      method: "post",
      data: sysMenu
    })
  },

  /*
  更新
  */
  updateById(sysMenu) {
    return request({
      url: `${api_name}`,
      method: "put",
      data: sysMenu
    })
  },
  /**
   * 更改状态
   */
  updateStatus(id, status) {
    return request({
      url: `${api_name}/${id}/${status}`,
      method: 'put'
    })
  },
}
