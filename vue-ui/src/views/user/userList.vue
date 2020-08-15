<template>
  <div class="app-container">

    <el-form :inline="true" :model="filters" class="demo-form-inline">
      <el-form-item label="用户名">
        <el-input v-model="filters.keyword" placeholder="输入用户名查询" @keyup.enter.native="queryData"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="queryData">查询</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-plus" @click="handelAdd">新增</el-button>
      </el-form-item>
<!--      <el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="handleImport">导入</el-button>
      </el-form-item> -->
      <el-form-item>
        <el-button type="primary" icon="el-icon-plus" @click="handleExport">导出</el-button>
      </el-form-item>
      <el-form-item>
         <el-upload class="upload-demo"
      				 :action="uploadUrl2"
      				 :before-upload="handleBeforeUpload2"
      				 :on-error="handleUploadError"
      				 :before-remove="beforeRemove"
      				 multiple
      				 :limit="1"
      				 :on-exceed="handleExceed"
      				 accept=".csv, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel"
      				 :file-list="fileList">
            <el-button size="small" type="primary">批量导入用户</el-button>
         </el-upload>
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
      <el-table-column label="用户名" align="center">
        <template slot-scope="scope">
          {{ scope.row.username }}
        </template>
      </el-table-column>
      <el-table-column label="邮箱"  align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.email }}</span>
        </template>
      </el-table-column>
      <el-table-column label="角色" align="center">
        <template slot-scope="scope">
          {{ scope.row.roleName }}
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.createTime | dateFormat}}</span>
        </template>
      </el-table-column>
      <el-table-column label="允许登陆" align="center">
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
                <el-form-item label="用户名"   prop="username">
                  <el-input type="text" placeholder="用户名" auto-complete="off" v-model="ruleForm.username"></el-input>
                </el-form-item>
                <el-form-item label="邮箱" prop="email">
                  <el-input type="text" placeholder="邮箱" auto-complete="off" v-model="ruleForm.email"></el-input>
                </el-form-item>
               <el-form-item label="头像"   prop="avater">
               <el-upload
                   class="avatar-uploader"
                   action="xxx"
                   :show-file-list="false"
                   :on-success="handleAvatarSuccess"
                   :before-upload="beforeAvatarUpload">
                   <img v-if="ruleForm.avater" :src="ruleForm.avater" class="avatar">
                   <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                 </el-upload>
               </el-form-item>
                </el-form-item>
                <el-form-item label="性别" prop="sex">
                 <el-select v-model="ruleForm.sex" filterable>
                    <el-option
                      v-for="item in sexArr"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value">
                    </el-option>
                 </el-select>
                </el-form-item>
                <el-form-item label="角色" prop="role">
                 <el-select multiple v-model="ruleForm.roleIds" filterable placeholder="请选择">
                    <el-option
                      v-for="item in roles"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id">
                    </el-option>
                 </el-select>
                </el-form-item>
                <el-form-item label="是否启用">
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
import { getUserList,addUser,updateUser,deleteUser,uploadImg,exportUser,importUser } from '@/api/user'
import { getRoleList } from '@/api/role'
import { getUserId } from '@/utils/auth'
import { formatDate } from '@/utils/date'
import axios from 'axios'

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
     sexArr:[
       {value:1, label:"男"},
       {value:2, label:"女"}
      ],
     url: '',
      filters: {
      	keyword: ''
      },
      imageUrl: '',
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
      	username: '',
      	password: '123456',  //默认密码
      	sex: 1,
      	email: '',
      	isValid: 1,
        avater: '',
      	roleIds: []
      },
      // 表单验证
      rules: {
        username: [
          { required: true, message: '请输入用户名'},
          { min: 2, max: 10, message: '长度在 5 到 12 个字符'}
        ],
        email: [
          { required: true, message: '请输入邮箱地址' },
          { type: 'email', message: '请输入正确的邮箱地址' }
        ],
        roleIds: [
          { required: true, message: '请选择用户角色', trigger: 'change' }
        ],
        sex: [
          { required: true, message: '请选择性别', trigger: 'change' }
        ]
      },
     //新增or编辑弹框标题(根据点击的新增or编辑按钮显示不同的标题)
     titleMap: {
         add:'新增',
         view: "查看信息",
         edit: "修改信息"
     },
     roles: [], //角色列表
      // 导入参数
     isAutoUpload: true,
     uploadUrl: 'http://192.168.43.152:8089/file/upload',
     uploadUrl2: 'http://localhost:8888/user/import',
     fileList: [],

    }
  },
  created() {
    this.fetchData()
    this.getRoles()
  },
  methods: {
    fetchData() {
      this.listLoading = true
      let params = {
        startPage: this.startPage,
        pageSize: this.pageSize,
        username: this.filters.keyword
      }
      getUserList(params).then(response => {
        this.list = response.data
        this.total = response.total
        this.listLoading = false
        // debugger
      }).catch(err =>{
        this.listLoading = false
      })
    },
    //获取角色列表
    getRoles(){
      getRoleList().then(response => {
        this.roles = response.data
      }).catch(err =>{
         console.log("获取角色列表失败")
      })
    },
    //查询
    queryData(){
      this.fetchData()
    },

    handleAvatarSuccess(res, file) {
     // this.imageUrl = URL.createObjectURL(file.raw);
    },
    beforeAvatarUpload(file) {
     let formData = new FormData();
     formData.append("multipartFiles", file);
     uploadImg(formData).then(response => {
       // debugger
       if(response.code == 200){
         console.log("返回的头像名："+response.data)
         this.ruleForm.avater = response.data

       }
     }).catch(err =>{
       this.$message({
                 message: '上传头像失败',
                 type: 'error'
               });
     })
    },

    // 表单提交
    submitForm(formName) {
    	this.$refs[formName].validate((valid) => {
    		if (valid) {
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
     //新增
    handelAdd() {
      this.currentType = 'add'
      //显示弹框
      this.dialogFormVisible = true;
     },
     //编辑
    handleEdit(obj) {
      this.currentType = 'edit'
      this.ruleForm = obj
      if(obj.roleIds){
        // 角色不为空，角色回显
        this.ruleForm.roleIds = obj.roleIds.split(',')
      }else{
        this.ruleForm.roleIds = []
      }

      //显示弹框
      this.dialogFormVisible = true;
     },
     // 执行添加
    doAdd(){
      // debugger
       this.ruleForm.roleIds = this.ruleForm.roleIds.join(',')
       addUser(this.ruleForm).then(response => {
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
      debugger
      this.ruleForm.roleIds = this.ruleForm.roleIds.join(',')
      updateUser(this.ruleForm).then(response => {
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
      deleteUser(params).then(response => {
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
    //修改用户状态
    switchChange(status,id) {
      let params = {
        isValid: status,
        id: id
      }
      updateUser(params).then(response => {
        let msg = status == 1 ? "已允许用户登录" : "已禁止用户登录"
        this.$message({
                  message: msg,
                  type: 'success'
                });
        //刷新列表
        this.fetchData()
      }).catch(err =>{
        this.$message({
                  message: '修改用户状态失败',
                  type: 'error'
                });
      })
    },

    /** 导出按钮操作 */
    handleExport() {
       let params = {
         username: this.filters.keyword
       }
       exportUser(params).then(res => {
         debugger
          const link = document.createElement('a')
          let blob = new Blob([res],{type: 'application/vnd.ms-excel'});
          link.style.display = 'none'
          link.href = URL.createObjectURL(blob);
          console.log("href:"+link.href)
          let num = ''
          for(let i=0;i < 10;i++){
           num += Math.ceil(Math.random() * 10)
          }
          link.setAttribute('download', num + '.xls')
          document.body.appendChild(link)
          link.click()
          document.body.removeChild(link)
       }).catch(err =>{
         debugger
         console.log("导出失败")
       })
    },
     //测试下载文件(注意web的上下文)
     handleDownLoad() {
       window.location.href = `/file/download?fileName=` + this.form.fileName
     },
     handleExceed(files, fileList) {
        this.$message.warning(`当前限制选择 5 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
      },
     beforeRemove(file, fileList) {
       return this.$confirm(`确定移除 ${ file.name }？`);
     },
     handleUploadError(error, file) {
        console.log("文件上传出错："+error)
     },
     //测试上传文件(注意web的上下文)
     handleBeforeUpload(file){
      this.uploadUrl = 'http://192.168.43.152:8089/file/upload'
      console.log("开始上传，上传的文件为："+file)
      let formData = new FormData();
      formData.append("multipartFiles", file);
      axios({
          method: 'post',
          url: 'http://192.168.43.152:8089/file/upload',
          data: formData,
          headers: {'Content-Type': 'multipart/form-data' }
        }).then((res) => {
          console.log("文件上传返回："+res)
        }).catch(error => {
          console.log("文件上传异常:"+error)
        })

          // this.uploadUrl ='http://192.168.43.152:8089/file/upload'
     },
     //导入
     handleBeforeUpload2(file){
      debugger
      this.fileTemp = file
      let fileName = file.name
      let fileType = fileName.substring(fileName.lastIndexOf('.') + 1);

      // 判断上传文件格式
      if (this.fileTemp) {
        if ((fileType != 'xlsx') && (fileType != 'xls')) {
          this.$message({
            type: 'warning',
            message: '附件格式错误，请删除后重新上传！'
          })
          return;
        }
      } else {
        this.$message({
          type: 'warning',
          message: '请上传附件！'
        })
        return;
      }

      this.uploadUrl = 'http://localhost:8888/user/import'
      console.log("开始上传，上传的文件为："+file)
      let formData = new FormData();
      formData.append("multipartFiles", file);
      
      importUser(formData).then(res => {
         console.log("导入成功，返回："+res)
      }).catch(e => {
        console.log("导入失败")
      })
      // axios({
      //     method: 'post',
      //     url: 'http://localhost:8888/user/import',
      //     data: formData,
      //     headers: {'Content-Type': 'multipart/form-data' }
      //   }).then((res) => {
      //     console.log("文件上传返回："+res)
      //   }).catch(error => {
      //     console.log("文件上传异常:"+error)
      //   })

          // this.uploadUrl ='http://192.168.43.152:8089/file/upload'
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
