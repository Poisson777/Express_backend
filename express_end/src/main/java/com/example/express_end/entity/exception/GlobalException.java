package com.example.express_end.entity.exception;

import com.example.express_end.Respond.RespondBean;
import com.example.express_end.Respond.RespondEnum;

/**
 * 全局异常
 *
 * @author: LC
 * @date 2022/3/2 5:32 下午
 * @ClassName: GlobalException
 */
public class GlobalException extends RuntimeException {

    private RespondEnum RespondEnum;

    public RespondEnum getRespBeanEnum() {
        return RespondEnum;
    }

    public void setRespBeanEnum(RespondEnum RespondEnum) {
        this.RespondEnum = RespondEnum;
    }

    public GlobalException(RespondEnum RespondEnum) {
        this.RespondEnum = RespondEnum;
    }
}
