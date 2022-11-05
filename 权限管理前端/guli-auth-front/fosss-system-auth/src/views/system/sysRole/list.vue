<template>

  <div class="app-container">

    <!--查询表单-->
    <div class="search-div">
      <el-form label-width="70px" size="small">
        <el-row>
          <el-col :span="24">
            <el-form-item label="角色名称">
              <el-input style="width: 100%" v-model="searchObj.roleName" placeholder="角色名称"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row style="display:flex">
          <el-button type="primary" icon="el-icon-search" size="mini" @click="getPageList()">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetData">重置</el-button>
        </el-row>
      </el-form>
    </div>
    <div>

      <!-- 添加角色-->
      <!-- 工具条 -->
      <div class="tools-div">
        <el-button type="success" icon="el-icon-plus" size="mini" @click="add">添 加</el-button>
        <!--      批量删除-->
        <el-button class="btn-add" size="mini" @click="removeRoles()">批量删除</el-button>
      </div>
    </div>

    <!-- 表格 -->
    <el-table
      v-loading="listLoading"
      :data="roleList"
      stripe
      border
      style="width: 100%;margin-top: 10px;"
      @selection-change="handleSelectionChange">
      <el-table-column type="selection"/>

      <el-table-column
        label="序号"
        width="70"
        align="center">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>

      <el-table-column prop="roleName" label="角色名称"/>
      <el-table-column prop="roleCode" label="角色编码"/>
      <el-table-column prop="createTime" label="创建时间" width="160"/>
      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <el-button type="primary" icon="el-icon-edit" size="mini" @click="edit(scope.row.id)" title="修改"/>
          <el-button type="danger" icon="el-icon-delete" size="mini" @click="removeDataById(scope.row.id)"
                     title="删除"/>
        </template>
      </el-table-column>
    </el-table>

    <!--      添加和修改的弹出框-->
    <el-dialog title="添加/修改" :visible.sync="dialogVisible" width="40%">
      <el-form ref="dataForm" :model="sysRole" label-width="150px" size="small" style="padding-right: 40px;">
        <el-form-item label="角色名称">
          <el-input v-model="sysRole.roleName"/>
        </el-form-item>
        <el-form-item label="角色编码">
          <el-input v-model="sysRole.roleCode"/>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false" size="small" icon="el-icon-refresh-right">取 消</el-button>
        <el-button type="primary" icon="el-icon-check" @click="saveOrUpdate()" size="small">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 分页组件 -->
    <el-pagination
      :current-page="page"
      :total="total"
      :page-size="limit"
      style="padding: 30px 0; text-align: center;"
      layout="total, prev, pager, next, jumper"
      @current-change="getPageList"
    />
  </div>
</template>

<script>
import roleApi from '@/api/system/role'

export default {
  data() {
    return {
      page: 1,//当前页
      limit: 4,//每页条数
      total: 0,//总页数
      searchObj: {},//查询条件
      roleList: [],//查询到的角色集合
      listLoading: true,//是否显示加载中这个图标
      dialogVisible: false,//添加修改弹出框是否显示
      sysRole: {},//封装添加的角色
      selectValue: [],//被选中的复选框
    }
  },
  created() {
    //调用
    this.getPageList()
  },
  methods: {
    //被选中的复选框
    handleSelectionChange(selection){
      // console.log(selection)
      this.selectValue=selection
    },
    //批量删除
    removeRoles() {
      if(this.selectValue.length>0){
        //弹出提示框
        this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          //封装被选中的id数组
          var ids=[]
          for(let i=0;i<this.selectValue.length;i++){
            ids[i]=this.selectValue[i].id
          }
          // console.log(ids)
          //调用删除接口
          roleApi.removeRoles(ids).then(res => {
            this.$message({
              type: 'success',
              message: '删除成功!'
            });
            this.getPageList(this.page)
          })
        })
      }
    },
    //弹出修改弹框，回显数据
    edit(id) {
      //弹出弹框
      this.dialogVisible = true
      //回显数据
      roleApi.getRoleById(id).then(res => {
        // console.log(res.data.sysRole)
        this.sysRole = res.data.sysRole
      })
    },
    //调用添加或修改方法
    saveOrUpdate() {
      if (this.sysRole.id) {
        this.updateRole()
      } else {
        this.addRole()
      }
    },
    //添加方法
    addRole() {
      roleApi.addRole(this.sysRole).then(res => {
        //关闭弹框
        this.dialogVisible = false
        //提示信息
        this.$message.success("添加成功")
        //刷新页面
        this.getPageList(this.page)
      })
    },
    //修改方法
    updateRole() {
      roleApi.updateRole(this.sysRole).then(res => {
        this.dialogVisible = false
        this.$message.success("修改成功")
        this.getPageList(this.page)
      })
    },
    //弹出添加表单
    add() {
      //显示表单
      this.dialogVisible = true
      //将表单清空
      this.sysRole = {}
    },

    getPageList(pageNum = 1) {
      this.page = pageNum//默认为第一页
      // console.log(this.searchObj)
      roleApi.getPageList(this.page, this.limit, this.searchObj).then(res => {
        // console.log(res)
        // console.log(res.data)
        this.total = res.data.pageInfo.total
        this.roleList = res.data.pageInfo.records
        // console.log(this.roleList)
        this.listLoading = false
      })
    },
    //搜索框重置方法
    resetData() {
      this.searchObj = {}
    },
    //根据id删除
    removeDataById(id) {
      //弹出提示框
      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        //调用删除接口
        roleApi.removeDataById(id).then(res => {
          // console.log(res.code)
          if (res.code === 20000) {
            this.$message({
              type: 'success',
              message: '删除成功!'
            });
            this.getPageList()
          } else {
            this.$message({
              type: 'error',
              message: '删除失败'
            });
          }
        })
      })
    }
  },

}
</script>

<style scoped>

</style>
