package com.example.express_end.utils;

import com.example.express_end.Respond.RespondEnum;
import com.example.express_end.entity.exception.GlobalException;
import com.example.express_end.mapper.LogMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Aspect//切面定义
@Component
@Order(1)//配置类的加载顺序
public class WebLogAspect {
    @Autowired
    LogMapper logMapper;
    @Pointcut("@annotation(com.example.express_end.utils.WebLog)")//定义切点，controller包下的所有方法
    public void pointcut() {
    }

    @AfterReturning(value = " pointcut()&& @annotation(webLog)", returning="res")
    public void doAfterReturning(JoinPoint joinPoint,WebLog webLog,Object res) throws Throwable {
//        System.out.println("进入后置增强了！");
//        String name = joinPoint.getSignature().getName();
//        System.out.println(name);
        Object[] args = joinPoint.getArgs();
//        System.out.println(args[0]);
        Map<String,Object> map=new HashMap<String, Object>();
        if(args[0] instanceof Map<?,?>){
            map= (Map<String, Object>) args[0];
        }
        else {
            Field[] fields = args[0].getClass().getDeclaredFields();
            for(Field field:fields){
                field.setAccessible(true);
                map.put(field.getName(), field.get(args[0]));//获取请求参数
            }
        }
        Map<String,Object> map2=new HashMap<String, Object>();//插入用参数
        if(map.get("opid")==null){//判断是否传进来管理员id
            throw new GlobalException(RespondEnum.AID_ERROR);
        }
        else {
            map2.put("aid", map.get("opid"));
            map2.put("operate_type", webLog.type());
            map2.put("operate_info", webLog.info());
        }
        System.out.println(map2);
        map2.put("operator_name",logMapper.getUsernameById(map2));
        logMapper.insertLog(map2);
//            if(webLog.type().equals("登录") ){
//                map2.put("operate_info","登录了系统");
//            }
//            else {
//                if(webLog.type().equals("新增")){
//
//                }
//                String s=webLog.type();
//                s=s+"了ID为";
//                if (map.get("uid")!=null){
//                    s=s+map.get("uid")+"的用户";
//                }
//                else if (map.get("mid")!=null){
//                    s=s+map.get("mid")+"的快递员";
//                }
//                else if (map.get("eid")!=null){
//                    s=s+map.get("eid")+"的快递";
//                }
//                else if (map.get("nid")!=null){
//                    s=s+map.get("nid")+"的网点";
//                }
//                else if (map.get("cid")!=null){
//                    s=s+map.get("cid")+"的公司信息";
//                }
//            }
//        }
//        System.out.println("我是map:"+map);
//        for (Object arg : args) {
//            System.out.println("参数：" + arg);
//
//        }
//        System.out.println("方法返回值为：" + res);
    }
}
