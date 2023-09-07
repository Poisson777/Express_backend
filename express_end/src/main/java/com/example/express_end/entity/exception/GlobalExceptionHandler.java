package com.example.express_end.entity.exception;

import com.example.express_end.Respond.RespondBean;
import com.example.express_end.Respond.RespondEnum;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * 全局异常处理类
 *
 * @author: LC
 * @date 2022/3/2 5:33 下午
 * @ClassName: GlobalExceptionHandler
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public RespondBean ExceptionHandler(Exception e) {
        if (e instanceof GlobalException) {
            GlobalException exception = (GlobalException) e;
            return RespondBean.error(exception.getRespBeanEnum());
        } else if (e instanceof BindException) {
            BindException bindException = (BindException) e;
            RespondBean respBean = RespondBean.error(RespondEnum.PARAMETER_ERROR);
            respBean.setMessage("参数校验异常：" + bindException.getBindingResult().getAllErrors().get(0).getDefaultMessage());
            return respBean;
        }
        System.out.println("异常信息" + e);
        return RespondBean.error(RespondEnum.ERROR);
    }
}
