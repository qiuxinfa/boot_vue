package com.qxf.exception;

import com.qxf.util.EnumCode;
import com.qxf.util.ResultUtil;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @ClassName GlobalExceptionHandler
 * @Description 全局异常处理
 * @Author qiuxinfa
 * @Date 2020/8/7 22:58
 **/
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 校验参数异常处理
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultUtil handleValidationException(MethodArgumentNotValidException e){
        // 同样是获取BindingResult对象，然后获取其中的错误信息
        // 如果前面开启了fail_fast，事实上这里只会有一个信息
        //如果没有，则可能又多个
        String errorInformation = e.getBindingResult().getAllErrors()
                .stream().toString();
        return new ResultUtil(EnumCode.INTERNAL_SERVER_ERROR.getValue(),errorInformation);
    }
}
