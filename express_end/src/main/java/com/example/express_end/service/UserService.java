package com.example.express_end.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.express_end.Respond.RespondBean;
import com.example.express_end.Respond.RespondEnum;
import com.example.express_end.entity.Admin;
import com.example.express_end.entity.User;
import com.example.express_end.mapper.Express.ExpressMapper;
import com.example.express_end.mapper.UserMapper;
import com.example.express_end.vo.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    ExpressMapper expressMapper;

    public RespondBean login(HttpServletRequest request,LoginForm loginForm){//使用继承至BaseMapper的selectOne方法，用QueryWrapper条件拼接
        User user=userMapper.selectOne(new QueryWrapper<User>()
                .eq("username",loginForm.getUsername())
                .eq("password",loginForm.getPassword()));
//        Map user=userMapper.login(loginForm);
        if(user==null){
            return RespondBean.error(RespondEnum.PASSWORD_ERROR);//传入自定义的枚举类型，用于存储各种错误
        }
        user.setPassword("");
//        user.put("password",null);
        request.getSession().setAttribute("tokenid", UUID.randomUUID());
        request.getSession().setMaxInactiveInterval(600);
        return RespondBean.success(user);//自定义的返回类型，将数据包装成code，data，message的形式
    }

    public RespondBean register(HttpServletRequest request,RegisterForm registerForm) {
        if(userMapper.selectOne(new QueryWrapper<User>().eq("username",registerForm.getUsername()))!=null)
        {
            return RespondBean.error(RespondEnum.REGISTER_ERROR);
        }
        ObjectMapper objectMapper=new ObjectMapper();
        String email=objectMapper.convertValue(request.getSession().getAttribute("email"),Email.class).getEmail();
        if(!registerForm.getCode().equals(request.getSession().getAttribute("code"))||
                !registerForm.getEmail().equals(email)){//判断验证码和邮箱是否对应
            return RespondBean.error(RespondEnum.CODE_ERROR);
        }
        User user=new User();
        user.setUsername(registerForm.getUsername());
        user.setNickname(registerForm.getNickname());
        user.setPassword(registerForm.getPassword());
        user.setFace("1.jpg");
        user.setEmail(registerForm.getEmail());//采用基类BaseMapper的方法插入数据库需要保证类相同，所以需要进行转换
        userMapper.insert(user);//全局捕捉异常，无需再判断，数据库操作失败自动返回500
        return RespondBean.success();
    }
    public RespondBean register2( RegisterForm registerForm) {
        if(userMapper.selectOne(new QueryWrapper<User>().eq("username",registerForm.getUsername()))!=null)
        {
            return RespondBean.error(RespondEnum.REGISTER_ERROR);
        }
        User user=new User();
        user.setUsername(registerForm.getUsername());
        user.setNickname(registerForm.getNickname());
        user.setPassword(registerForm.getPassword());
        user.setFace("1.jpg");
        user.setEmail(registerForm.getEmail());//采用基类BaseMapper的方法插入数据库需要保证类相同，所以需要进行转换
        userMapper.insert(user);//全局捕捉异常，无需再判断，数据库操作失败自动返回500
        return RespondBean.success();
    }


    //管理员登录  /可以是超级管理员也可以是网点管理员
    public RespondBean AdminLogin(HttpServletRequest request,Map<String ,Object> map){//使用继承至BaseMapper的selectOne方法，用QueryWrapper条件拼接
        Map<String,Object> map1 = userMapper.findAdmin(map);
//        Map user=userMapper.login(loginForm);
        if(map1==null){
            return RespondBean.error(RespondEnum.PASSWORD_ERROR);//传入自定义的枚举类型，用于存储各种错误
        }
        map1.replace("password","");
//        user.put("password",null);
        request.getSession().setAttribute("tokenid", UUID.randomUUID());
        request.getSession().setMaxInactiveInterval(600);
        return RespondBean.success(map1);//自定义的返回类型，将数据包装成code，data，message的形式
    }
    //网点管理员注册
    public RespondBean AdminRegister(HttpServletRequest request,Map<String ,Object> map){
        if(userMapper.findUsername(map)==0){
            ObjectMapper objectMapper=new ObjectMapper();
            String email=objectMapper.convertValue(request.getSession().getAttribute("email"),Email.class).getEmail();
            if(!map.get("code").equals(request.getSession().getAttribute("code"))||
                    !map.get("email").equals(email)){//判断验证码和邮箱是否对应

                return RespondBean.error(RespondEnum.CODE_ERROR);
            }
            map.put("face","1.jpg");
            userMapper.insertNetworkAdminByAdmin(map);
            return RespondBean.success();
        }
        else {
            return RespondBean.error(RespondEnum.REGISTER_ERROR);
        }
    }

    public RespondBean AdminRegister2(Map<String, Object> map) {
        if(userMapper.findUsername(map)==0){
            map.put("face","1.jpg");
            userMapper.insertNetworkAdminByAdmin(map);
            return RespondBean.success();
        }
        else {
            return RespondBean.error(RespondEnum.REGISTER_ERROR);
        }
    }


    public RespondBean updateUserInfo(UpdateForm updateForm) {
        if(userMapper.updateUserInfo(updateForm)==1){
            return RespondBean.success();
        }
        else{
            return RespondBean.error(RespondEnum.USER_FIND_ERROR);
        }
    }

    public RespondBean authUser(AuthForm authForm) {
        if(userMapper.authUser(authForm)==1){
            return RespondBean.success();
        }
        else{
            return RespondBean.error(RespondEnum.USER_FIND_ERROR);
        }
    }

    public RespondBean updateLocation(Map<String, Object> map) {
        if(userMapper.updateLocation(map)==1){
            return RespondBean.success();
        }
        else{
            return RespondBean.error(RespondEnum.USER_FIND_ERROR);
        }
    }

    public RespondBean getUserById(Map<String,Object> map) {
        User user=userMapper.selectById((Integer)map.get("uid"));
        user.setPassword("");
        return RespondBean.success(user);
    }

    public RespondBean confirmReceipt(Map<String, Object> map){
        map.put("state","已送达");
        Date date=new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
        map.put("receive_time",dateFormat.format(date));
        return RespondBean.success(expressMapper.confirmReceipt(map));
    }

    public RespondBean getReceiptExpressListByUid(Map<String, Object> map){
        map.putIfAbsent("page_size", 5);
        map.put("begin",((int)map.get("page_num")-1)*(int)map.get("page_size"));
        Map<String, Object> map2=new HashMap<>();
        map2.put("data",expressMapper.getReceiptExpressListByUid(map));
        map2.put("count",expressMapper.getReceiptExpressListByUidCount(map));
        return RespondBean.success(map2);
    }

    public RespondBean getSendExpressListByUid(Map<String, Object> map){
        map.putIfAbsent("page_size", 5);
        map.put("begin",((int)map.get("page_num")-1)*(int)map.get("page_size"));
        Map<String, Object> map2=new HashMap<>();
        map2.put("data",expressMapper.getSendExpressListByUid(map));
        map2.put("count",expressMapper.getSendExpressListByUidCount(map));
        return RespondBean.success(map2);
    }


    public RespondBean updatePassword(HttpServletRequest request,Map<String, Object> map) {
        if(map.get("password").toString().length()<6){
            return RespondBean.error(RespondEnum.PARAMETER_ERROR);
        }
        if(!map.get("code").toString().equals(request.getSession().getAttribute("code2"))){
            return RespondBean.error(RespondEnum.CODE_ERROR);
        }
        if(userMapper.updatePassword(map)>=1){
            return RespondBean.success();
        }
        else{
            return RespondBean.error(RespondEnum.USER_FIND_ERROR);
        }
    }

    public RespondBean networkAdminUpdatePassword(HttpServletRequest request,Map<String, Object> map) {
        if(map.get("password").toString().length()<6){
            return RespondBean.error(RespondEnum.PARAMETER_ERROR);
        }
        if(!map.get("code").toString().equals(request.getSession().getAttribute("code2"))){
            return RespondBean.error(RespondEnum.CODE_ERROR);
        }
        if(userMapper.networkAdminUpdatePassword(map)>=1){
            return RespondBean.success();
        }
        else{
            return RespondBean.error(RespondEnum.FIELD_NOT_FIND);
        }
    }

    public RespondBean updatePassword2(Map<String, Object> map) {
        if(map.get("password").toString().length()<6){
            return RespondBean.error(RespondEnum.PARAMETER_ERROR);
        }
        if(userMapper.updatePassword(map)>=1){
            return RespondBean.success();
        }
        else{
            return RespondBean.error(RespondEnum.USER_FIND_ERROR);
        }
    }
    public RespondBean networkAdminUpdatePassword2(Map<String, Object> map) {
        if(map.get("password").toString().length()<6){
            return RespondBean.error(RespondEnum.PARAMETER_ERROR);
        }
        if(userMapper.networkAdminUpdatePassword(map)>=1){
            return RespondBean.success();
        }
        else{
            return RespondBean.error(RespondEnum.FIELD_NOT_FIND);
        }
    }

    public RespondBean getUidByPhone(Map<String, Object> map) {
        if (userMapper.getUidByPhone(map)!=null)
        {
            return RespondBean.success(userMapper.getUidByPhone(map));
        }
        else {
            return RespondBean.error(RespondEnum.FIELD_NOT_FIND);
        }
    }
    public RespondBean getUidByPhoneLike(Map<String, Object> map) {
        map.putIfAbsent("page_size", 5);
        map.put("begin",((int)map.get("page_num")-1)*(int)map.get("page_size"));
        Map<String, Object> map2=new HashMap<>();
        map2.put("data",userMapper.getUidByPhoneLike(map));
        map2.put("count",userMapper.getUidByPhoneLikeCount(map));
        return RespondBean.success(map2);
    }

}
