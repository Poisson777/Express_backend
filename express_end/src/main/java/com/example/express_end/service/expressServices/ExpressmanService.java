package com.example.express_end.service.expressServices;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.express_end.Respond.RespondBean;
import com.example.express_end.Respond.RespondEnum;
import com.example.express_end.entity.Expressman;
import com.example.express_end.mapper.Express.ExpressMapper;
import com.example.express_end.mapper.ExpressmanMapper;
import com.example.express_end.vo.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class ExpressmanService {
    @Autowired
    ExpressmanMapper expressmanMapper;
    @Autowired
    ExpressMapper expressMapper;

    public RespondBean login(LoginForm loginForm){
        Expressman expressman = expressmanMapper.selectOne(new QueryWrapper<Expressman>()
        .eq("username",loginForm.getUsername())
        .eq("password",loginForm.getPassword()));
        if(expressman ==null){
            return (RespondBean.error(RespondEnum.PASSWORD_ERROR));
        }
        expressman.setPassword(null);
        return RespondBean.success(expressman);//自定义的返回类型，将数据包装成code，data，message的形式
    }
    public RespondBean register(HttpServletRequest request, RegisterForm registerForm) {
        if (expressmanMapper.selectOne(new QueryWrapper<Expressman>().eq("username", registerForm.getUsername())) != null) {
            return RespondBean.error(RespondEnum.REGISTER_ERROR);
        }
        ObjectMapper objectMapper=new ObjectMapper();
        String email=objectMapper.convertValue(request.getSession().getAttribute("email"), Email.class).getEmail();
        if(!registerForm.getCode().equals(request.getSession().getAttribute("code"))||
                !registerForm.getEmail().equals(email)){//判断验证码和邮箱是否对应
            return RespondBean.error(RespondEnum.CODE_ERROR);
        }
        Expressman expressman = new Expressman();
        expressman.setUsername(registerForm.getUsername());
        expressman.setNickname(registerForm.getNickname());
        expressman.setPassword(registerForm.getPassword());
        expressman.setFace("ye.jpg");
        expressman.setEmail(registerForm.getEmail());//采用基类BaseMapper的方法插入数据库需要保证类相同，所以需要进行转换
        expressmanMapper.insert(expressman);//全局捕捉异常，无需再判断，数据库操作失败自动返回500
        return RespondBean.success();
    }

    public RespondBean register2(RegisterForm registerForm) {
        if (expressmanMapper.selectOne(new QueryWrapper<Expressman>().eq("username", registerForm.getUsername())) != null) {
            return RespondBean.error(RespondEnum.REGISTER_ERROR);
        }
        Expressman expressman = new Expressman();
        expressman.setUsername(registerForm.getUsername());
        expressman.setNickname(registerForm.getNickname());
        expressman.setPassword(registerForm.getPassword());
        expressman.setFace("1.jpg");
        expressman.setEmail(registerForm.getEmail());//采用基类BaseMapper的方法插入数据库需要保证类相同，所以需要进行转换
        expressmanMapper.insert(expressman);//全局捕捉异常，无需再判断，数据库操作失败自动返回500
        return RespondBean.success();
    }

    public RespondBean updateUserInfo(EUpdateForm updateForm) {
        if(expressmanMapper.updateExpressmanInfo(updateForm)==1){
            return RespondBean.success();
        }
        else{
            return RespondBean.error(RespondEnum.USER_FIND_ERROR);
        }
    }

    public RespondBean updateLocation(Map<String, Object> map) {
        if(expressmanMapper.updateLocation(map)==1){
            return RespondBean.success();
        }
        else{
            return RespondBean.error(RespondEnum.USER_FIND_ERROR);
        }
    }

    public RespondBean getUserById(Map<String,Object> map) {
        Expressman expressman=expressmanMapper.selectExpressmanById(map);
        expressman.setPassword("");
        return RespondBean.success(expressman);
    }

    public RespondBean authExpressman(EAuthForm authForm){
        if(expressmanMapper.authExpressman(authForm)==1){
            return RespondBean.success();
        }
        else {
            return RespondBean.error(RespondEnum.USER_FIND_ERROR);
        }
    }

    public RespondBean getWaitSendExpress(Map<String,Object> map) {
        map.putIfAbsent("page_size", 5);
        map.put("state", "目的站");
        map.put("begin",((int)map.get("page_num")-1)*(int)map.get("page_size"));
        Map<String, Object> map2=new HashMap<>();
        map2.put("data",expressMapper.getExpressListByNid(map));
        map2.put("count",expressMapper.getExpressListByNidCount(map));
        return RespondBean.success(map2);
    }



    public RespondBean getExpressList(Map<String, Object> map) {
        map.putIfAbsent("state", "所有");
        map.putIfAbsent("page_size", 5);
        map.put("begin",((int)map.get("page_num")-1)*(int)map.get("page_size"));
        Map<String, Object> map2=new HashMap<>();
        map2.put("data",expressMapper.getExpressListByMid(map));
        map2.put("count",expressMapper.getExpressListByMidCount(map));
        return RespondBean.success(map2);
    }
    public RespondBean getExpressListByName(Map<String, Object> map){
        map.putIfAbsent("page_size", 5);
        map.put("begin",((int)map.get("page_num")-1)*(int)map.get("page_size"));
        Map<String, Object> map2=new HashMap<>();
        map2.put("data",expressMapper.getExpressListByName(map));
        map2.put("count",expressMapper.getExpressListByNameCount(map));
        return RespondBean.success(map2);
    }
    public RespondBean insertExpress(Map<String, Object> map) {
        map.put("code", UUID.randomUUID().toString());
        expressMapper.insertExpressByExpressman(map);
        return RespondBean.success(map);
    }

    public RespondBean getNearWaitSendExpress(Map<String, Object> map) {
        map.putIfAbsent("page_size", 5);
        map.put("state","目的站");
        map.put("begin",((int)map.get("page_num")-1)*(int)map.get("page_size"));
        Map<String, Object> map2=new HashMap<>();
        map2.put("data",expressMapper.getNearExpressList(map));
        map2.put("count",expressMapper.getNearExpressListCount(map));
        return RespondBean.success(map2);
    }

//修改密码
    public RespondBean updatePassword(HttpServletRequest request,Map<String,Object> map) {
        if(map.get("password").toString().length()<6){
            return RespondBean.error(RespondEnum.PARAMETER_ERROR);
        }
        if(!map.get("code").toString().equals(request.getSession().getAttribute("code2"))){
            return RespondBean.error(RespondEnum.CODE_ERROR);
        }
        if(expressmanMapper.updatePassword(map)>=1){
            return RespondBean.success();
        }
        else{
            return RespondBean.error(RespondEnum.USER_FIND_ERROR);
        }
    }

    //修改密码2
    public RespondBean updatePassword2(Map<String,Object> map) {
        if(map.get("password").toString().length()<6){
            return RespondBean.error(RespondEnum.PARAMETER_ERROR);
        }
        if(expressmanMapper.updatePassword(map)>=1){
            return RespondBean.success();
        }
        else{
            return RespondBean.error(RespondEnum.USER_FIND_ERROR);
        }
    }

    public RespondBean canvassExpress(Map<String, Object> map) {
        if (expressMapper.canvassExpress(map)==1){
            return RespondBean.success();
        }
        else {
            return RespondBean.error(RespondEnum.FIELD_NOT_FIND);
        }
    }
}
