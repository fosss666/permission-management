<template>
  <div class="app-container">
    角色管理
  </div>
</template>

<script>
import roleApi from '@/api/role/role'

export default {
  data() {
    return {
      page: 1,//当前页
      limit: 10,//每页条数
      total: 0,//总页数
      searchObj: {},//查询条件
      roleList: [],//查询到的角色集合
    }
  },
  created() {
    //调用
    this.getPageList()
  },
  methods: {
    getPageList(pageNum = 1) {
      this.page = pageNum//默认为第一页
      roleApi.getPageList(this.page, this.limit, this.searchObj).then(res => {
        // console.log(res)
        // console.log(res.data)
        this.total=res.data.pageInfo.total
        this.roleList=res.data.pageInfo.records
        console.log(this.roleList)

      })

    }
  }
}
</script>

<style scoped>

</style>
