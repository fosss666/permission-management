package com.fosss.system.exception;

import com.fosss.system.util.R;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public R doException(Exception e){
        e.printStackTrace();
        return R.error().data("data","执行了全局异常处理:"+e.getMessage());
    }

    @ExceptionHandler(ArithmeticException.class)
    public R doException(ArithmeticException e){
        e.printStackTrace();
        return R.error().data("data","执行了特定异常处理:"+e.getMessage());
    }

    @ExceptionHandler(MyException.class)
    public R doException(MyException e){
        e.printStackTrace();
        return R.error().data("data","执行了自定义异常处理:"+e.getMessage());
    }
}














