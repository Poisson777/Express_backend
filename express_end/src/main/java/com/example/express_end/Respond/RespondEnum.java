package com.example.express_end.Respond;

public enum RespondEnum {//枚举类，定义不同异常的异常码和信息
    SUCCESS(200,"成功"),
    ERROR(500, "服务端异常"),
    LOGIN_ERROR(500210, "还未登录"),
    REGISTER_ERROR(500211, "用户名已被使用"),
    EMAIL_ERROR(500212, "邮箱已被使用"),
    PARAMETER_ERROR(500212, "参数校验异常"),
    CODE_ERROR(500214,"验证码错误" ),
    USER_FIND_ERROR(500216,"未找到该用户" ),
    PASSWORD_ERROR(500215, "用户名或密码"),
    FIELD_NOT_FIND(500217,"没找到记录" ),//...
    Redis_ERROR(500218, "redis错误"),
    Insert_ERROR(500219,"插入失败"),

    AID_ERROR(500220,"请将操作员的aid作为参数opid传过来"),
    PARAMETER_NULL_ERROR(500221, "请求参数为空或为0"),
    ADD_WARNING(500223, "已入库"),
    ROLE_ERROR(500222, "请正确填写role参数:user,expressman,admin");
    private final Integer code;
    private final String message;
    RespondEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }


}
