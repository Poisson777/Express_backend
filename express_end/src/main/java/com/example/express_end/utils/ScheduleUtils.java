package com.example.express_end.utils;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.example.express_end.mapper.ExpressmanMapper;
import com.example.express_end.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Configuration
@EnableScheduling
public class ScheduleUtils {
    @Autowired
    UserMapper userMapper;
    @Autowired
    ExpressmanMapper expressmanMapper;
    @Resource
    RedisUtils redisUtil;

    TimeUtils timeUtils = new TimeUtils();
    //3分钟,采用心跳机制 让redis不会断开
    @Scheduled(cron = "${cron.report.shoucash}")
    public void takeShoucash() {
        try {
//                System.out.println("heartbeat");
                redisUtil.getRedisTemplate().opsForValue().get("heartbeat");
            }
         catch (Exception e) {
            System.out.println(e);
        }
    }

    @Scheduled(cron = "${cron.report.reflushRedis}")
    public void changeUserStatus(){
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH");
            long current_time = timeUtils.timeStamp();
            //更新用户request
            ArrayList<Integer> uids = userMapper.getUserIdList();
            for (int uid:
                 uids) {
                int flag=0;
                boolean isUpdate =false;
                List<Object> list = redisUtil.lGet("uid_"+String.valueOf(uid),0,-1);
                for (Object i:
                     list) {

                    Map<String,Object> map1 = (Map<String, Object>) i;
                    if(!map1.get("status").equals("cancel") || !map1.get("status").equals("finish")){
                        String finish_time = String.valueOf(map1.get("finish_time"));
                        Date finishtime = sdf.parse(finish_time);

                        String str_curtime = timeUtils.ChangeTime(String.valueOf(current_time),"yyyy-MM-dd HH");
                        Date curtime = sdf.parse(str_curtime);
                        if(finishtime.before(curtime)){
                            if(!map1.get("status").equals("overtime")){
                                map1.replace("status","overtime");
                                isUpdate =true;
                            }
                        }
                        if(isUpdate){
                            redisUtil.lUpdateIndex("uid_"+uid,flag,map1);
                            isUpdate = false;
                        }

                    }
                    flag++;
                }

            }

        }catch (Exception e){
            System.out.println(e);
        }
    }
    @Scheduled(cron = "${cron.report.reflushRedis}")
    public void changeUserRequestStatus(){
        try{
            //更新user_request
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH");
            long current_time = timeUtils.timeStamp();
            int flag=0;
            List<Object> list = redisUtil.lGet("user_request",0,-1);
            for (Object i:
                    list) {
                    Map<String,Object> map1 = (Map<String, Object>) i;
                    String finish_time = String.valueOf(map1.get("finish_time"));
                    Date finishtime = sdf.parse(finish_time);
                    String str_curtime = timeUtils.ChangeTime(String.valueOf(current_time),"yyyy-MM-dd HH");
                    Date curtime = sdf.parse(str_curtime);
                    if(finishtime.before(curtime)) {
                        redisUtil.lRemove("user_request", 1, i);
                    }
                    flag++;
                }

        }catch (Exception e){
            System.out.println(e);
        }
    }

    @Scheduled(cron = "${cron.report.reflushRedis}")
    public void changeExpressStatus(){
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH");
            long current_time = timeUtils.timeStamp();
            //更新快递员request
            ArrayList<Integer> eids = expressmanMapper.getEidList();
            for (int eid:
                    eids) {
                int flag=0;
                boolean isUpdate =false;
                List<Object> list = redisUtil.lGet("eid_"+String.valueOf(eid),0,-1);
                for (Object i:
                        list) {

                    Map<String,Object> map1 = (Map<String, Object>) i;
                    if(!map1.get("status").equals("cancel") || !map1.get("status").equals("finish")){
                        String finish_time = String.valueOf(map1.get("finish_time"));
                        Date finishtime = sdf.parse(finish_time);

                        String str_curtime = timeUtils.ChangeTime(String.valueOf(current_time),"yyyy-MM-dd HH");
                        Date curtime = sdf.parse(str_curtime);
                        if(finishtime.before(curtime)){
                            if(!map1.get("status").equals("overtime")){
                                map1.replace("status","overtime");
                                isUpdate =true;
                            }
                        }
                        if(isUpdate){
                            redisUtil.lUpdateIndex("eid_"+eid,flag,map1);
                            isUpdate = false;
                        }

                    }
                    flag++;
                }

            }

        }catch (Exception e){
            System.out.println(e);
        }
    }

    @Scheduled(cron = "${cron.report.checkExpireRedis}")
    public void checkExpireRedis(){
        try {
            ArrayList<String> codes = new ArrayList<>();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH");
            long current_sub7day_time = timeUtils.getAddTimeStamp(-7);
            //保留7天的用户request
            ArrayList<Integer> uids = userMapper.getUserIdList();
            for (int uid:
                    uids) {
                List<Object> list = redisUtil.lGet("uid_" + String.valueOf(uid), 0, -1);
                for (Object i :
                        list) {
                    Map<String, Object> map1 = (Map<String, Object>) i;
                    if (map1.get("status").equals("cancel") || map1.get("status").equals("finish")) {
                        String time = String.valueOf(map1.get("time"));
                        Date time1 = sdf.parse(time);
                        String str_cursub7time = timeUtils.ChangeTime(String.valueOf(current_sub7day_time), "yyyy-MM-dd HH");
                        Date cursub7time = sdf.parse(str_cursub7time);
                        if (time1.after(cursub7time)) {
                            redisUtil.lRemove("uid_"+uid,1,i);
                            codes.add(String.valueOf(map1.get("request_id")));
                        }
                    }
                }
            }
            ArrayList<Integer> mids = expressmanMapper.getEidList();
            for (int mid:
                    mids) {
                List<Object> list = redisUtil.lGet("eid_" + String.valueOf(mid), 0, -1);
                for (Object i :
                        list) {
                    Map<String, Object> map1 = (Map<String, Object>) i;
                    if (map1.get("status").equals("cancel") || map1.get("status").equals("finish")) {
                        String time = String.valueOf(map1.get("time"));
                        Date time1 = sdf.parse(time);
                        String str_cursub7time = timeUtils.ChangeTime(String.valueOf(current_sub7day_time), "yyyy-MM-dd HH");
                        Date cursub7time = sdf.parse(str_cursub7time);
                        if (time1.after(cursub7time)) {
                            redisUtil.lRemove("eid_"+mid,1,i);
                        }
                    }
                }
            }
            //清除未使用的绑定code
            List<Object> redis_request_ids = redisUtil.lGet("request_id",0,-1);
            for (Object id:
                 redis_request_ids) {
                if(codes.contains(String.valueOf(id))){
                    redisUtil.lRemove("request_id",1,id);
                }
            }


        }catch (Exception e){
            System.out.println(e);
        }
    }
}
