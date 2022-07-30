package com.wyn.gulimallproduct.exception;

import com.alibaba.fastjson.serializer.FieldSerializer;
import com.wyn.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wei-ya-nan
 * @version 1.0
 * @date 2022/6/10
 * 集中处理统一的异常类
 */
@Slf4j
/*@ResponseBody
@ControllerAdvice(basePackages = "com.wyn.gulimallproduct.controller")*/

@RestControllerAdvice(basePackages = "com.wyn.gulimallproduct.controller")
public class GulimallExceptionControllerAdvice {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R handlerVaildException(MethodArgumentNotValidException e, BindingResult result){
        log.error("数据校验出现问题{}，异常类型{}", e.getMessage(),e.getClass());
        Map<String,Object> map = new HashMap<>();
        result.getFieldErrors().forEach((item)->{
            String field = item.getField();
            String defaultMessage = item.getDefaultMessage();
            map.put(field, defaultMessage);
        });


        return R.error(400,"数据教研出现问题").put("data", map);
    }



}
