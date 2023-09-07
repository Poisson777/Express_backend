package com.example.express_end.Respond;


public class RespondBean {
    private Integer code;
    private String message;
    private Object data;

    public RespondBean(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public RespondBean(RespondEnum respondEnum, Object data) {
        this.code = respondEnum.getCode();
        this.message = respondEnum.getMessage();
        this.data = data;
    }

    public RespondBean(RespondEnum respondEnum) {
        this.code = respondEnum.getCode();
        this.message = respondEnum.getMessage();
    }
    //返回成功
    public static RespondBean success(){
        return new RespondBean(RespondEnum.SUCCESS);
    }
    public static RespondBean success(Object data){
        return new RespondBean(RespondEnum.SUCCESS,data);
    }
    //返回失败
    public static RespondBean error(){
        return new RespondBean(RespondEnum.ERROR);
    }
    public static RespondBean error(RespondEnum respondEnum){
        return new RespondBean(respondEnum);
    }
    public static RespondBean error(RespondEnum respondEnum,Object data){
        return new RespondBean(respondEnum,data);
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
