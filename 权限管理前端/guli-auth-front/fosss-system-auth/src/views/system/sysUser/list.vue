<template>
  <div class="app-container">

    <div class="search-div">
      <el-form label-width="70px" size="small">
        <el-row>
          <el-col :span="8">
            <el-form-item label="关 键 词">
              <el-input style="width: 95%" v-model="searchObj.keyword" placeholder="用户名/姓名/手机号码"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="操作时间">
              <el-date-picker
                v-model="createTimes"
                type="datetimerange"
                range-separator="至"
                start-placeholder="开始时间"
                end-placeholder="结束时间"
                value-format="yyyy-MM-dd HH:mm:ss"
                style="margin-right: 10px;width: 100%;"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row style="display:flex">
          <el-button type="primary" icon="el-icon-search" size="mini" @click="fetchData()">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetData">重置</el-button>
        </el-row>
      </el-form>
    </div>

    <!-- 工具条 -->
    <div class="tools-div">
      <el-button type="success" icon="el-icon-plus" size="mini" @click="add">添 加</el-button>
      <!--      批量删除-->
      <el-button class="btn-add" size="mini" @click="removeUsers()">批量删除</el-button>
    </div>

    <!-- 列表 -->
    <el-table
      v-loading="listLoading"
      :data="list"
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


        <el-table-column prop="username" label="用户名" width="180"/>
        <el-table-column prop="name" label="姓名" width="110"/>
        <el-table-column prop="phone" label="手机"/>
        <el-table-column label="状态" width="80">
          <template slot-scope="scope">
            <el-switch
              v-model="scope.row.status === 1"
              @change="switchStatus(scope.row)">
            </el-switch>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间"/>

        <el-table-column label="操作" align="center" fixed="right">
          <template slot-scope="scope">
            <el-button type="primary" icon="el-icon-edit" size="mini" @click="edit(scope.row.id)" title="修改"/>
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="removeDataById(scope.row.id)"
                       title="删除"/>
          </template>
        </el-table-column>
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

    <el-dialog title="添加/修改" :visible.sync="dialogVisible" width="40%">
      <el-form ref="dataForm" :model="sysUser" label-width="100px" size="small" style="padding-right: 40px;">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="sysUser.username"/>
        </el-form-item>
        <el-form-item v-if="!sysUser.id" label="密码" prop="password">
          <el-input v-model="sysUser.password" type="password"/>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="sysUser.name"/>
        </el-form-item>
        <el-form-item label="手机" prop="phone">
          <el-input v-model="sysUser.phone"/>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false" size="small" icon="el-icon-refresh-right">取 消</el-button>
        <el-button type="primary" icon="el-icon-check" @click="saveOrUpdate()" size="small">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import userApi from '@/api/system/user'
import roleApi from "@/api/system/role";

export default {
  data() {
    return {
      listLoading: true, // 数据是否正在加载
      list: null, // 列表
      total: 0, // 数据库中的总记录数
      page: 1, // 默认页码
      limit: 10, // 每页记录数
      searchObj: {}, // 查询表单对象

      createTimes: [],

      dialogVisible: false,
      sysUser: {},
    }
  },

  // 生命周期函数：内存准备完毕，页面尚未渲染
  created() {
    this.fetchData()
  },
  methods: {
    // 加载banner列表数据
    fetchData(page = 1) {
      this.page = page
      /*封装开始结束时间*/
      if (this.createTimes && this.createTimes.length == 2) {
        this.searchObj.createTimeBegin = this.createTimes[0]
        this.searchObj.createTimeEnd = this.createTimes[1]
      }

      userApi.getPageList(this.page, this.limit, this.searchObj).then(
        response => {
          // console.log(response)
          this.list = response.data.pageInfo.records
          this.total = response.data.pageInfo.total

          // 数据加载并绑定成功
          this.listLoading = false
        }
      )
    },

    // 重置查询表单
    resetData() {
      this.searchObj = {}
      this.createTimes = []
      this.fetchData()
    },

    // 根据id删除数据
    removeDataById(id) {
      // debugger
      this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => { // promise
        // 点击确定，远程调用ajax
        return userApi.removeDataById(id)
      }).then((response) => {
        this.fetchData(this.page)
        this.$message.success('删除成功')
      }).catch(() => {
        this.$message.info('取消删除')
      })
    },

    //弹出添加表单
    add() {
      this.dialogVisible = true
      this.sysUser = {}
    },
    //编辑
    edit(id) {
      this.dialogVisible = true
      userApi.getUserById(id).then(response => {
        this.sysUser = response.data.user
      })
    },

    //添加或更新
    saveOrUpdate() {
      if (!this.sysUser.id) {
        this.save()
      } else {
        this.update()
      }
    },

    //添加
    save() {
      userApi.addUser(this.sysUser).then(response => {
        this.$message.success(response.message)
        this.dialogVisible = false
        this.fetchData(this.page)
      })
    },

    //更新
    update() {
      userApi.updateUser(this.sysUser).then(response => {
        this.$message.success('修改成功')
        this.dialogVisible = false
        this.fetchData(this.page)
      })
    },
    //批量删除
    removeUsers() {
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
          userApi.removeUsers(ids).then(res => {
            this.$message({
              type: 'success',
              message: '删除成功!'
            });
            this.fetchData(this.page)
          })
        })
      }
    },
    //被选中的复选框
    handleSelectionChange(selection){
      // console.log(selection)
      this.selectValue=selection
    },
  }
}
</script>

<style scoped>

</style>
















