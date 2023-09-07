package com.example.express_end.service;

import com.example.express_end.Respond.RespondBean;
import com.example.express_end.Respond.RespondEnum;
import com.example.express_end.mapper.Express.ExpressMapper;
import com.example.express_end.utils.RedisUtils;
import com.example.express_end.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.util.*;

@Service
public class RequestService {

    @Resource
    RedisUtils redisUtil;

    @Autowired
    ExpressMapper expressMapper;

    TimeUtils timeUtils =new TimeUtils();

    //用户点击上门取件
    public RespondBean adduserRequest(Map<String, Object> map){
        try{
            Map <String, Object> map1 = new HashMap<>();
            String uid = String.valueOf(map.get("uid"));
            String content = String.valueOf(map.get("content"));
            String location = String.valueOf(map.get("location"));
            String address = String.valueOf(map.get("address"));
            if(Objects.equals(uid, "null")){
                return RespondBean.error(RespondEnum.PARAMETER_NULL_ERROR);
            }if(Objects.equals(content, "null")){
                return RespondBean.error(RespondEnum.PARAMETER_NULL_ERROR);
            }if(Objects.equals(location, "null")){
                return RespondBean.error(RespondEnum.PARAMETER_NULL_ERROR);
            }if(Objects.equals(address, "null")){
                return RespondBean.error(RespondEnum.PARAMETER_NULL_ERROR);
            }
            map1.put("uid", uid);
            map1.put("content",content);
            map1.put("location",location);
            map1.put("address",address);
            Random random=new Random();
            String code=String.valueOf(random.nextInt(999999));
            if(code.length()!=6)
            {
                for (int i=0;i<6-code.length();i++) {
                    code = String.valueOf(i) + code;
                }
            }
            while(redisUtil.lGetListSize(code)!=0){
                random=new Random();
                code=String.valueOf(random.nextInt(999999));
                if(code.length()!=6)
                {
                    for (int i=0;i<6-code.length();i++) {
                        code = String.valueOf(i) + code;
                    }
                }
            }
            //绑定请求id
            redisUtil.lSet("request_id",code);

            map1.put("request_id",code);
            map1.put("status","isAccept");          //isAccept  accept cancel finish
            map1.put("eid",-1);
            long time =timeUtils.timeStamp();
            map1.put("time",timeUtils.ChangeTime(String.valueOf(time),"yyyy-MM-dd HH:mm"));
            long finish_time =timeUtils.getAddTimeStamp(1);
            String str_finish_time = timeUtils.ChangeTime(String.valueOf(finish_time),"yyyy-MM-dd HH");
            map1.put("finish_time", str_finish_time);
            redisUtil.lSet("user_request",map1);
            redisUtil.lSet("uid_"+uid,map1);
            return RespondBean.success(map1);
        }catch (Exception e){
            return RespondBean.error(RespondEnum.Redis_ERROR);
        }
    }

    //用户获取自己的上门取件请求
    public RespondBean getRequest(Map<String, Object >map){
        try{
            String uid = String.valueOf(map.get("uid"));
            if(Objects.equals(uid, "null")){
                return RespondBean.error(RespondEnum.PARAMETER_NULL_ERROR);
            }
            int pageN = (Integer) map.getOrDefault("page_num",1);
            int pageS = (Integer) map.getOrDefault("page_size",5);
            long len =redisUtil.lGetListSize("uid_"+uid);
            if(len ==0){
                Map<String, Object> map1=new HashMap<>();
                map1.put("length",0);
                map1.put("data",null);
                return RespondBean.success(map1);
            }else {
                int start = (pageN-1)*pageS;
                int end =pageN*pageS -1;
                if(start>len-1) {
                    Map<String, Object> map1=new HashMap<>();
                    map1.put("length",0);
                    map1.put("data",null);
                    return RespondBean.success(map1);
                }
                else {
                    List<Object> list =redisUtil.lGet("uid_"+uid,start,Math.min((len - 1), end) );
                    Map<String, Object> map1=new HashMap<>();
                    map1.put("length",redisUtil.lGetListSize("uid_"+uid));
                    map1.put("data",list);
                    return RespondBean.success(map1);
                }

            }
        }catch (Exception e){
            return RespondBean.error(RespondEnum.Redis_ERROR);
        }
    }

    //用户取消上门取件,
    public RespondBean canceluserRequest(Map<String, Object >map){
        try {
            String uid = String.valueOf(map.get("uid"));
            if(Objects.equals(uid, "null")){
                return RespondBean.error(RespondEnum.PARAMETER_NULL_ERROR);
            }
            String request_id = String.valueOf(map.get("request_id"));
            if(Objects.equals(request_id, "null")){
                return RespondBean.error(RespondEnum.PARAMETER_NULL_ERROR);
            }
            int flag=0;
            for (Object i: redisUtil.lGet("uid_"+uid,0,-1)
                 ) {

                    Map<String,Object> map1 = (Map<String, Object>) i;

                if(map1.get("request_id").equals(request_id)){
                    redisUtil.lRemove("user_request",1,i);
                    map1.replace("status","cancel");
                    if((Integer) map1.get("eid")!=-1){
                        int flag1=0;
                        for(Object j :redisUtil.lGet("eid_"+map1.get("eid"),0,-1)){
                            Map<String,Object> map2 = (Map<String, Object>) j;
                            if(map2.get("request_id").equals(request_id)){
                                if(map2.get("status").equals("finish")|| map2.get("status").equals("overtime") ||map2.get("status").equals("cancel")){
                                    return RespondBean.error(RespondEnum.Redis_ERROR);
                                }
                                map2.replace("status","cancel");
                                redisUtil.lUpdateIndex("eid_"+map1.get("eid"),flag1,map2);
                                break;
                            }
                            flag1++;
                        }
                    }
                    redisUtil.lUpdateIndex("uid_"+uid,flag,map1);
                    break;
                }
                flag++;
            }
            return RespondBean.success();
        }catch (Exception e){
            return RespondBean.error(RespondEnum.Redis_ERROR);
        }
    }

    //快递员接受上门取件订单
    public RespondBean acceptUserRequest(Map<String,Object>map){
        try{
            String uid =String.valueOf(map.get("uid"));
            String eid =String.valueOf(map.get("eid"));
            String request_id = String.valueOf(map.get("request_id"));
            if(Objects.equals(uid, "null")){
                return RespondBean.error(RespondEnum.PARAMETER_NULL_ERROR);
            }if(Objects.equals(eid, "null")){
                return RespondBean.error(RespondEnum.PARAMETER_NULL_ERROR);
            }if(Objects.equals(request_id, "null")){
                return RespondBean.error(RespondEnum.PARAMETER_NULL_ERROR);
            }
            //处理user_request
            List<Object> user_requests = redisUtil.lGet("user_request",0,-1);
            for (Object i :
                 user_requests) {
                Map<String,Object> map1 =(Map<String, Object>) i;
                if(map1.get("request_id").equals(request_id)){
                    redisUtil.lRemove("user_request",1,i);
                    break;
                }
            }
            //处理用户自身list
            int flag=0;
            List <Object> list = redisUtil.lGet("uid_"+uid,0,-1);
            for (Object i :list){
                Map<String,Object> map1 =(Map<String, Object>) i;
                if(map1.get("request_id").equals(request_id)){
                    if(map1.get("status").equals("finish")|| map1.get("status").equals("overtime") ||map1.get("status").equals("cancel")||map1.get("status").equals("accept")){
                        return RespondBean.error(RespondEnum.Redis_ERROR);
                    }
                    map1.replace("status","accept");
                    map1.replace("eid",eid);
                    redisUtil.lUpdateIndex("uid_"+uid,flag,map1);
                    //添加快递员自身list
                    redisUtil.lSet("eid_"+eid,map1);
                    break;
                }
                flag++;
            }
            return RespondBean.success();
        }catch (Exception e){
            return RespondBean.error(RespondEnum.Redis_ERROR);
        }
    }
    //快递员获取自身接收的订单
    public RespondBean getExpressAcceptRequest(Map<String, Object>map){
        try {
            String eid = String.valueOf(map.get("eid"));
            if(Objects.equals(eid, "null")){
                return RespondBean.error(RespondEnum.PARAMETER_NULL_ERROR);
            }
            int pageN = (Integer) map.getOrDefault("page_num",1);
            int pageS = (Integer) map.getOrDefault("page_size",5);
            long len = redisUtil.lGetListSize("eid_" + eid);
            if (len == 0) {
                Map<String, Object> map1 = new HashMap<>();
                map1.put("length", 0);
                map1.put("data", null);
                return RespondBean.success(map1);
            } else {
                int start = (pageN - 1) * pageS;
                int end = pageN * pageS - 1;
                if (start > len - 1) {
                    Map<String, Object> map1 = new HashMap<>();
                    map1.put("length", 0);
                    map1.put("data", null);
                    return RespondBean.success(map1);
                } else {
                    List<Object> list = redisUtil.lGet("eid_" + eid, start, Math.min((len - 1), end));
                    Map<String, Object> map1 = new HashMap<>();
                    map1.put("length", redisUtil.lGetListSize("eid_"+eid));
                    map1.put("data", list);
                    return RespondBean.success(map1);
                }
            }
        }catch (Exception e){
            return RespondBean.error(RespondEnum.Redis_ERROR);
        }
    }
    //快递员查看当前待接受用户请求
    public RespondBean getUserRequest(Map<String ,Object>map){
        try {
            int pageN = (Integer) map.getOrDefault("page_num",1);
            int pageS = (Integer) map.getOrDefault("page_size",5);
            String location = String.valueOf(map.get("location"));
            if(Objects.equals(location, "null")){
                return RespondBean.error(RespondEnum.PARAMETER_NULL_ERROR);
            }
            List<Object> list = redisUtil.lGet("user_request",0,-1);
            List<Map<String,Object>> list1= new ArrayList<>();
            for (Object i:
                 list) {
                Map<String,Object> map1 =(Map<String, Object>) i;
                if(map1.get("location").equals(location)){
                    list1.add(map1);
                }

            }
            int len = list1.size();
            int start = (pageN-1)*pageS;
            int end = pageN * pageS;
            Map<String,Object> map2 = new HashMap<>();
            if(start>len){
                map2.put("data",null);
                map2.put("length",0);
            }
            else {
                List Sublist =list1.subList(start, Math.min((len), end));
                map2.put("data",Sublist);
                map2.put("length",list1.size());
            }
            return RespondBean.success(map2);
        }catch (Exception e){
            return RespondBean.error(RespondEnum.Redis_ERROR);
        }
    }

    //完成用户请求
    public RespondBean finishUserRequest(Map<String,Object> map){
        try {
            String request_id = String.valueOf(map.get("request_id"));
            String eid = String.valueOf(map.get("eid"));
            String uid = String.valueOf(map.get("from_uid"));
            if(Objects.equals(uid, "null")){
                return RespondBean.error(RespondEnum.PARAMETER_NULL_ERROR);
            }if(Objects.equals(eid, "null")){
                return RespondBean.error(RespondEnum.PARAMETER_NULL_ERROR);
            }if(Objects.equals(request_id, "null")){
                return RespondBean.error(RespondEnum.PARAMETER_NULL_ERROR);
            }
//            System.out.println(map);
            map.put("mid",eid);
            if(expressMapper.insertExpressByExpressman(map)!=1){
                return RespondBean.error(RespondEnum.Insert_ERROR);
            }
            //修改用户请求状态
            int flag=0;
            List <Object> list = redisUtil.lGet("uid_"+uid,0,-1);
            for (Object i :list){
                Map<String,Object> map1 =(Map<String, Object>) i;

                if(map1.get("request_id").equals(request_id)){
                    if(map1.get("status").equals("finish")|| map1.get("status").equals("overtime") ||map1.get("status").equals("cancel")||map1.get("status").equals("isAccept")){
                        return RespondBean.error(RespondEnum.Redis_ERROR);
                    }
                    map1.replace("status","finish");
                    redisUtil.lUpdateIndex("uid_"+uid,flag,map1);
                    break;
                }
                flag++;
            }
            //修改快递员请求状态
            flag=0;
            list = redisUtil.lGet("eid_"+eid,0,-1);
            for (Object i :list){
                Map<String,Object> map1 =(Map<String, Object>) i;
                if(map1.get("request_id").equals(request_id)){
                    map1.replace("status","finish");
                    redisUtil.lUpdateIndex("eid_"+eid,flag,map1);
                    break;
                }
                flag++;
            }


            return RespondBean.success();
        }catch (Exception e){
            System.out.println(e);
            //设置手动回滚
            TransactionAspectSupport.currentTransactionStatus()
                    .setRollbackOnly();
            return RespondBean.error(RespondEnum.Redis_ERROR);
        }
    }

}
