<template>
  <div class="app-container">

    <el-form :inline="true" :model="filters" class="demo-form-inline">
      <el-form-item label="角色名称">
        <el-input v-model="filters.keyword"  @keyup.enter.native="queryData"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="queryData">查询</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-plus" @click="handelAdd">新增</el-button>
      </el-form-item>
    </el-form>

    <el-table
      v-loading="listLoading"
      :data="list"
      element-loading-text="Loading"
      border
      fit
      highlight-current-row
    >
      <el-table-column
              label="序号"
              type="index"
              width="50"
              align="center">
          <template slot-scope="scope">
              <span>{{(startPage - 1) * pageSize + scope.$index + 1}}</span>
          </template>
      </el-table-column>
      </el-table-column>
      <el-table-column label="角色名称" align="center">
        <template slot-scope="scope">
          {{ scope.row.name }}
        </template>
      </el-table-column>
      <el-table-column label="角色描述"  align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.description }}</span>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.createTime | dateFormat}}</span>
        </template>
      </el-table-column>
      <el-table-column label="修改时间" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.updateTime | dateFormat}}</span>
        </template>
      </el-table-column>
      <el-table-column label="是否有效" align="center">
			      <template slot-scope="scope">
							<el-switch
							  @change="switchChange(scope.row.isValid,scope.row.id)"
							  v-model="scope.row.isValid"
							  active-color="#13ce66"
							  inactive-color="#ff4949"
								:active-value="activeValue"
                :inactive-value="inactiveValue">
							</el-switch>
			 </template>
      </el-table-column>
      <el-table-column
            align="center"
            fixed="right"
            label="操作">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="primary" icon="el-icon-edit"
                @click="handleEdit(scope.row)">编辑</el-button>
              <el-button
                size="mini"
                type="danger" icon="el-icon-delete"
                @click="handleDelete(scope.row.id)">删除</el-button>
            </template>
      </el-table-column>
    </el-table>

    <el-col>
    		<div class="block" style="float: right;margin-right: 10px;margin-top: 10px;">
    			<el-pagination
    				@size-change="handleSizeChange"
    				@current-change="handleCurrentChange"
    				:current-page="startPage"
    				:page-sizes="pageSizes"
    				:page-size="pageSize"
    				layout="total, sizes, prev, pager, next, jumper"
    				:total="total">
    			</el-pagination>
    	 </div>
    </el-col>

    <!-- 　　新增和编辑 弹框 -->
    <el-dialog
        :title="titleMap[currentType]"
        :visible.sync="dialogFormVisible" >
          <div style="width:80%;margin: 0 auto">
            <el-form :model="ruleForm" status-icon :rules="rules"  ref="ruleForm" :inline="false" label-width="90px" class="demo-ruleForm">
                <el-form-item label="角色名称"   prop="name">
                  <el-input type="text"  auto-complete="off" v-model="ruleForm.name"></el-input>
                </el-form-item>
                <el-form-item label="角色描述" prop="description">
                  <el-input type="text" auto-complete="off" v-model="ruleForm.description"></el-input>
                </el-form-item>
                <el-form-item label="权限">
                  <el-tree
                    :data="permissionList"
                    show-checkbox
                    default-expand-all
                    ref="mytree"
                    node-key="id"
                    :props="treeProps"
                    :default-checked-keys="defaultCheckedKeyArr"

                    >
                  </el-tree>
                </el-form-item>
                <el-form-item label="是否有效">
                  <el-switch
                    v-model="ruleForm.isValid"
                    active-color="#13ce66"
                    inactive-color="#ff4949"
                    :active-value="activeValue"
                    :inactive-value="inactiveValue">
                  </el-switch>
                </el-form-item>
              </el-form>
           </div>
           <div slot="footer" class="dialog-footer">
             <el-button @click="dialogFormVisible = false">取 消</el-button>
             <el-button type="primary" @click="submitForm('ruleForm')">确 定</el-button>
           </div>
   </el-dialog>


  </div>
</template>

<script>
import { getList,doAdd,doUpdate,doDelete } from '@/api/role'
import { getAllPermission } from '@/api/permission'
import { formatDate } from '@/utils/date'

export default {
  filters: {
    statusFilter(status) {
      const statusMap = {
        published: 'success',
        draft: 'gray',
        deleted: 'danger'
      }
      return statusMap[status]
    },
    dateFormat(time){
      return formatDate(new Date(time),'yyyy-MM-dd hh:mm:ss')
    }
  },
  data() {
    return {
     fileList: [],
      filters: {
      	keyword: ''
      },
      activeValue: 1,
      inactiveValue: 0,
      listLoading: false, // 加载等待
      pageSizes: [6, 10, 20, 40],
      startPage: 1,
      pageSize: 6,
      total: 0,
      list: null,
      listLoading: true,
      currentType: 'add',  //新增、查看、编辑公用一个弹窗，当前弹框类型，'add','view','edit'，默认为新增
      dialogFormVisible:false,
      ruleForm: {
        id: '',
      	name: '',
      	description: '',
        permissionIds: '',
      	isValid: 1
      },
      // 表单验证
      rules: {
        name: [
          { required: true, message: '请输入权限名称'},
          { min: 1, max: 50, message: '长度在 1 到 50 个字符'}
        ]
      },
     //新增or编辑弹框标题(根据点击的新增or编辑按钮显示不同的标题)
     titleMap: {
         add:'新增',
         view: "查看信息",
         edit: "修改信息"
     },
     treeProps: {
       label: 'name',
       children: 'children'
     },
     isExpandAll: true,
     defaultCheckedKeyArr: [],
     permissionList: [], //权限集合
    }
  },
  created() {
    this.fetchData()
    this.getPermissions()
  },
  methods: {
    fetchData() {
      this.listLoading = true
      let params = {
        startPage: this.startPage,
        pageSize: this.pageSize,
        name: this.filters.keyword
      }
      getList(params).then(response => {
        this.list = response.data
        this.total = response.total
        this.listLoading = false
        // debugger
      }).catch(err =>{
        this.listLoading = false
      })
    },
    //获取权限集合
    getPermissions(){
      let params = {
        type: '1'
      }
      getAllPermission(params).then(response => {
        this.permissionList = response.data
      }).catch(err =>{
         console.log("获取权限树失败")
      })
    },
    //查询
    queryData(){
      this.fetchData()
    },

    // // 树形下拉单选
    // handleCheckChange (data, checked, indeterminate) {
    //   /* 主要通过checked进行判断 */
    //   if (checked) {
    //     this.ruleForm.parentId = data.id
    //     let arr = [data.id]
    //     this.$refs.mytree.setCheckedKeys(arr)
    //   }
    // },

    // 表单提交
    submitForm(formName) {
    	this.$refs[formName].validate((valid) => {
    		if (valid) {
          // 填充权限集合
          this.fillPermissionIds()
    			if(this.currentType == 'add'){
            this.doAdd()
          }else if(this.currentType == 'edit'){
            this.doUpdate()
          }
    		} else {
    			console.log('error submit!!');
    			return false
    		}
    	})
    },
    // 填充权限集合
    fillPermissionIds(){
      this.ruleForm.permissionIds = this.$refs.mytree.getCheckedKeys().join(',')
    },
     //新增
    handelAdd() {
      this.currentType = 'add'
      this.ruleForm.permissionIds = ''
      this.defaultCheckedKeyArr = []
      this.$nextTick(() => {
        this.$refs.mytree.setCheckedKeys(this.defaultCheckedKeyArr)
      });
      //显示弹框
      this.dialogFormVisible = true;
     },
     //编辑
    handleEdit(obj) {
      this.currentType = 'edit'
      this.ruleForm = obj
      this.defaultCheckedKeyArr = []
      debugger
      if(obj.permissionIds){
        this.defaultCheckedKeyArr = obj.permissionIds.split(',')
      }
      this.$nextTick(() => {
        this.$refs.mytree.setCheckedKeys(this.defaultCheckedKeyArr)
      });
      //显示弹框
      this.dialogFormVisible = true;
     },
     // 执行添加
    doAdd(){
       doAdd(this.ruleForm).then(response => {
         let msg = "添加失败"
         let msgType = 'error'
         if(response.code == 200){
           msg = "添加成功"
           msgType = 'success'
         }
         this.$message({
                   message: msg,
                   type: msgType
                 });
         // 关闭弹窗
         this.dialogFormVisible = false
         //刷新列表
         this.fetchData()
       }).catch(err =>{
         this.$message({
                   message: '添加失败',
                   type: 'error'
                 });
       })
     },
    // 执行更新
    doUpdate(){
      doUpdate(this.ruleForm).then(response => {
        let msg = "修改失败"
        let msgType = 'error'
        if(response.code == 200){
          msg = "修改成功"
          msgType = 'success'
        }
        this.$message({
                  message: msg,
                  type: msgType
                });
        // 关闭弹窗
        this.dialogFormVisible = false
        //刷新列表
        this.fetchData()
      }).catch(err =>{
        this.$message({
                  message: '修改失败',
                  type: 'error'
                });
      })
    },
    // 删除
    handleDelete(id){
      let params = {
        id: id
      }
      debugger
      doDelete(params).then(response => {
        let msg = "删除失败"
        let msgType = 'error'
        debugger
        if(response.code == 200 && response.msg == '1'){
          msg = "删除成功"
          msgType = 'success'
        }
        this.$message({
                  message: msg,
                  type: msgType
                });
        //刷新列表
        this.fetchData()
      }).catch(err =>{
        this.$message({
                  message: '删除失败',
                  type: 'error'
                });
      })
    },
    // 每页大小改变时触发
    handleSizeChange (val) {
    	this.pageSize = val
    	this.fetchData()
    },
    // 当前页码改变时触发
    handleCurrentChange (val) {
    	this.startPage = val
    	this.fetchData()
    },
    //修改状态
    switchChange(status,id) {
      let params = {
        isValid: status,
        id: id
      }
      doUpdate(params).then(response => {
        let msg = status == 1 ? "已启用" : "已禁止"
        this.$message({
                  message: msg,
                  type: 'success'
                });
        //刷新列表
        this.fetchData()
      }).catch(err =>{
        this.$message({
                  message: '修改状态失败',
                  type: 'error'
                });
      })
    },


  }
}
</script>

<style>
  .avatar-uploader .el-upload {
      border: 1px dashed #d9d9d9;
      border-radius: 6px;
      cursor: pointer;
      position: relative;
      overflow: hidden;
    }
    .avatar-uploader .el-upload:hover {
      border-color: #409EFF;
    }
    .avatar-uploader-icon {
      font-size: 28px;
      color: #8c939d;
      width: 178px;
      height: 178px;
      line-height: 178px;
      text-align: center;
    }
    .avatar {
      width: 178px;
      height: 178px;
      display: block;
    }

</style>
