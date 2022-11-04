<template>

  <div class="app-container">
    <!-- 表格 -->
    <el-table
      v-loading="listLoading"
      :data="roleList"
      stripe
      border
      style="width: 100%;margin-top: 10px;">

      <el-table-column
        label="序号"
        width="70"
        align="center">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>

      <el-table-column prop="roleName" label="角色名称" />
      <el-table-column prop="roleCode" label="角色编码" />
      <el-table-column prop="createTime" label="创建时间" width="160"/>
      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <el-button type="primary" icon="el-icon-edit" size="mini" @click="edit(scope.row.id)" title="修改"/>
          <el-button type="danger" icon="el-icon-delete" size="mini" @click="removeDataById(scope.row.id)" title="删除"/>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页组件 -->
    <el-pagination
      :current-page="page"
      :total="total"
      :page-size="limit"
      style="padding: 30px 0; text-align: center;"
      layout="total, prev, pager, next, jumper"
      @current-change="fetchData"
    />
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
      listLoading:true,//是否显示加载中这个图标
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
        // console.log(this.roleList)
        this.listLoading=false
      })

    }
  }
}
</script>

<style scoped>

</style>
