package com.qxf.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qxf.entity.SysUser;
import com.qxf.service.SysUserService;
import com.qxf.util.EnumCode;
import com.qxf.util.ExcelUtil;
import com.qxf.util.ResultUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 用户(SysUser)表控制层
 *
 * @author makejava
 * @since 2020-08-03 20:41:06
 */
@RestController
@RequestMapping("user")
public class SysUserController {

    // 新增用户默认密码
    @Value("${defaultPassword}")
    private String defaultPassword;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 服务对象
     */
    @Resource
    private SysUserService sysUserService;

    @GetMapping("/list")
    public Object getListByPage(Integer startPage,Integer pageSize,String username){
        PageHelper.startPage(startPage,pageSize);
        List<SysUser> list = sysUserService.queryAll(username);
        PageInfo<SysUser> pageInfo = new PageInfo<>(list);
        return new ResultUtil(EnumCode.OK.getValue(),"请求成功",list,pageInfo.getTotal());
    }

    @PostMapping("/add")
    public ResultUtil addUser(@Valid @RequestBody SysUser user){
        String msg = "新增失败！";
        if (user != null){
            user.setId(UUID.randomUUID().toString().replace("-",""));
            user.setPassword(passwordEncoder.encode(defaultPassword));
            user.setCreateTime(new Date());
            Integer cnt = sysUserService.insert(user);
            if (cnt > 0){
                msg = "新增成功！";
            }
        }
        return new ResultUtil(EnumCode.OK.getValue(),msg);
    }

    @PostMapping("/update")
    public ResultUtil updateUser(@RequestBody SysUser user){
        String msg = "修改失败！";
        Integer cnt = sysUserService.update(user);
        if (cnt > 0){
            msg = "修改成功！";
        }
        return new ResultUtil(EnumCode.OK.getValue(),msg);
    }

    // @RequestBody 接收无法删除成功，原因未知
    @PostMapping("/delete")
    public ResultUtil deleteUser(String id){
        String msg = "0";
        Integer cnt = sysUserService.deleteById(id);
        if (cnt > 0){
            msg = "1";
        }
        return new ResultUtil(EnumCode.OK.getValue(),msg);
    }

    /**
     * 导出报表
     *
     * @return
     */
    @GetMapping("/export")
    @ResponseBody
    public void export(String username,HttpServletResponse response) throws Exception {
        //获取数据
        List<SysUser> list = sysUserService.queryAll(username);

        //excel标题
        String[] title = {"姓名", "邮箱", "创建时间","角色","是否可用"};

        //excel文件名
        String fileName = System.currentTimeMillis() + ".xls";

        //sheet名
        String sheetName = "用户信息";

        //没有数据就传入null吧，Excel工具类有对null判断
        String [][] content = null;

        if (list != null && list.size() > 0){
            content = new String[list.size()][title.length];
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            for (int i = 0; i < list.size(); i++) {
                content[i] = new String[title.length];
                SysUser obj = list.get(i);
                content[i][0] = obj.getUsername();
                content[i][1] = obj.getEmail();
                content[i][2] = obj.getCreateTime() == null ? "" : sdf.format(obj.getCreateTime());
                content[i][3] = obj.getRoleName();
                content[i][4] = obj.getIsValid() == 1 ? "是" : "否";
            }
        }

        //创建HSSFWorkbook
        HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, content);

        //响应到客户端
        try {
//            fileName = new String(fileName.getBytes(), "UTF-8");
//            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            OutputStream os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 批量导入用户
     *
     */
    @PostMapping("/import")
    @ResponseBody
    public ResultUtil ExcelImport(MultipartFile[] multipartFiles) throws Exception {
        if (multipartFiles == null || multipartFiles.length < 1){
            return new ResultUtil(EnumCode.INTERNAL_SERVER_ERROR.getValue(),"空数据，导入失败");
        }
        for (MultipartFile file : multipartFiles){
            List<String[]> list = ExcelUtil.readExcel(file);
            if (list.isEmpty()){
                return new ResultUtil(EnumCode.INTERNAL_SERVER_ERROR.getValue(),"空数据，导入失败");
            }

            for (int i=0;i<list.size();i++){
                String[] values = list.get(i);
                //这里只导入了3列数据：姓名、邮箱和是否可用（0、1），其他列可自行导入，先转换格式再写入数据库，比如：
                //导入角色的时候，根据角色名称查找角色id，如果角色id不存在，可以默认为学生之类的处理
                SysUser user = new SysUser();
                user.setUsername(values[0]);
                user.setEmail(values[1]);
                user.setIsValid("是".equals(values[2]) ? 1 : 0);
                user.setCreateTime(new Date());
                user.setId(UUID.randomUUID().toString().replace("-",""));
                user.setPassword(passwordEncoder.encode(defaultPassword));
                // 关联用户-角色表，角色默认为管理员
                user.setRoleIds("1");
                sysUserService.insert(user);


            }
        }
        //前端可以通过状态码，判断文件是否上传成功
        return new ResultUtil(EnumCode.OK.getValue(),"数据导入成功");
    }

}