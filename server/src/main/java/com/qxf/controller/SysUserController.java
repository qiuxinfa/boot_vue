package com.qxf.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qxf.entity.SysUser;
import com.qxf.service.SysUserService;
import com.qxf.util.EnumCode;
import com.qxf.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
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
    private static Logger logger = LoggerFactory.getLogger(SysUserController.class);
    private  final static String rootPath = "D:/attachment/";

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

    // 文件上传
    @RequestMapping("/upload")
    public Object uploadFile(MultipartFile[] multipartFiles){
        String fileName = "";
        File fileDir = new File(rootPath);
        if (!fileDir.exists() && !fileDir.isDirectory()) {
            fileDir.mkdirs();
        }
        try {
            if (multipartFiles != null && multipartFiles.length > 0) {
                for(int i = 0;i<multipartFiles.length;i++){
                    try {
                        //原文件名
                        fileName = multipartFiles[i].getOriginalFilename();
                        String suffixName = fileName.substring(fileName.lastIndexOf("."));
                        //用UUID命名
                        fileName = UUID.randomUUID().toString()+suffixName;
                        String storagePath = rootPath + fileName;
                        logger.info("上传的文件：" + multipartFiles[i].getName() + "," + multipartFiles[i].getContentType() + "," + multipartFiles[i].getOriginalFilename()
                                +"，保存的路径为：" + storagePath);
                        // 3种方法： 第1种
//                        Streams.copy(multipartFiles[i].getInputStream(), new FileOutputStream(storagePath), true);
                        // 第2种
//                        Path path = Paths.get(storagePath);
//                        Files.write(path,multipartFiles[i].getBytes());
                        // 第3种
                        multipartFiles[i].transferTo(new File(storagePath));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        //前端可以通过状态码，判断文件是否上传成功
        String imgURL = "http://localhost:8888/api/file/" + fileName;
        return new ResultUtil(EnumCode.OK.getValue(),"文件上传成功",imgURL);
    }

    /**
     *
     * @param fileName 文件名
     * @param response
     * @return
     */
    @RequestMapping("/download")
    public Object downloadFile(@RequestParam String fileName, HttpServletResponse response){
        OutputStream os = null;
        InputStream is= null;
        try {
            // 取得输出流
            os = response.getOutputStream();
            // 清空输出流
            response.reset();
            response.setContentType("application/x-download;charset=utf-8");
            //Content-Disposition中指定的类型是文件的扩展名，并且弹出的下载对话框中的文件类型图片是按照文件的扩展名显示的，点保存后，文件以filename的值命名，
            // 保存类型以Content中设置的为准。注意：在设置Content-Disposition头字段之前，一定要设置Content-Type头字段。
            //把文件名按UTF-8取出，并按ISO8859-1编码，保证弹出窗口中的文件名中文不乱码，中文不要太多，最多支持17个中文，因为header有150个字节限制。
            response.setHeader("Content-Disposition", "attachment;filename="+ new String(fileName.getBytes("utf-8"),"ISO8859-1"));
            //读取流
            File f = new File(rootPath+fileName);
            is = new FileInputStream(f);
            if (is == null) {
                logger.info("下载附件失败，请检查文件“" + fileName + "”是否存在");
                return new ResultUtil(EnumCode.INTERNAL_SERVER_ERROR.getValue(),"下载附件失败，请检查文件“" + fileName + "”是否存在");
            }
            //复制，后面修改，依赖，暂时关闭
//            IOUtils.copy(is, response.getOutputStream());
            response.getOutputStream().flush();
        } catch (IOException e) {
            return new ResultUtil(EnumCode.INTERNAL_SERVER_ERROR.getValue(),"下载附件失败,error:"+e.getMessage());
        }
        //文件的关闭放在finally中
        finally
        {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //其实，这个返回什么都不重要
        return new ResultUtil(EnumCode.OK.getValue(),"下载成功");
    }



}