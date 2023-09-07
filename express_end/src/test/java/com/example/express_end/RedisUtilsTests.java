package com.example.express_end;


import com.example.express_end.Respond.RespondBean;
import com.example.express_end.mapper.UserMapper;
import com.example.express_end.utils.RedisUtils;
import com.example.express_end.utils.TimeUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;

@SpringBootTest
public class RedisUtilsTests {
    @Resource
    RedisUtils redisUtil;
    @Autowired
    UserMapper userMapper;

    TimeUtils timeUtils =new TimeUtils();
    @Test
    void print(){
        System.out.println("杀杀杀");
        System.out.println(System.getProperty("file.encoding"));
    }
    @Test
    void T() {
        redisUtil.set("1","杀杀杀");
        System.out.println(redisUtil.get("1"));
    }
    @Test
    void Expire(){
        redisUtil.set("text1","111",120);
        redisUtil.set("num1",10,60*60);
        redisUtil.decr("num1",2);
        redisUtil.incr("num1",10);
    }
    @Test
    void updateNum1(){
        //�������ddl
        redisUtil.incr("num1",1);
    }
    @Test
    void Hashset(){
//        redisUtil.hset("hset1", "text","11",60*60);
        redisUtil.hset("hset1", "text2","11",60*60);
        System.out.println(redisUtil.hget("hset1","text2"));
    }
    @Test
    void Set(){
        redisUtil.sSetAndTime("Set1",60*60,1,2,3,"s");
        System.out.println(redisUtil.sGetSetSize("Set1"));
        System.out.println(redisUtil.sGet("Set1"));
        System.out.println(redisUtil.sHasKey("Set1",1));
    }
    @Test
    void Hmset(){
        HashMap<String,Object> map1=new HashMap<>();
        HashMap<String,Object> map2=new HashMap<>();
        map1.put("num3",1);
        map1.put("num2",2);
        map2.put("map1",map1);

        redisUtil.hmset("hmset",map2,64*64);
        System.out.println(redisUtil.hmget("hmset"));
    }


    @Test
    void testTimeschedule() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH");

        long current_time = timeUtils.timeStamp();
        //更新用户request
        ArrayList<Integer> uids = userMapper.getUserIdList();
        for (int uid :
                uids) {
            int flag = 0;
            List<Object> list = redisUtil.lGet("uid_" + String.valueOf(uid), 0, -1);
            for (Object i :
                    list) {

                Map<String, Object> map1 = (Map<String, Object>) i;
                if (!map1.get("status").equals("cancel") || !map1.get("status").equals("finish")) {
                    String finish_time = String.valueOf(map1.get("finish_time"));
                    Date finishtime = sdf.parse(finish_time);

                    String str_curtime = timeUtils.ChangeTime(String.valueOf(current_time), "yyyy-MM-dd HH");
                    Date curtime = sdf.parse(str_curtime);
                    if (finishtime.before(curtime)) {
                        map1.replace("status", "overtime");
                    }
                    redisUtil.lUpdateIndex("uid_" + uid, flag, map1);
                }
                flag++;
            }

        }
    }
        @Test
        void Lset () throws UnsupportedEncodingException {
            Map<String, Object> map1 = new HashMap<>();
            String uid = "1";
            map1.put("uid", uid);
            map1.put("content", "内容");
            map1.put("location", "福建省福州市闽侯县");
            map1.put("address", "傅吉安是实打实打算的");
            Random random = new Random();
            String code = String.valueOf(random.nextInt(999999));
            if (code.length() != 6) {
                for (int i = 0; i < 6 - code.length(); i++) {
                    code = String.valueOf(i) + code;
                }
            }
            //������id
            redisUtil.lSet("request_id", code);

            map1.put("request_id", code);
            map1.put("status", "isAccept");
            map1.put("eid", -1);
            timeUtils = new TimeUtils();
            long time =timeUtils.timeStamp();
            map1.put("time",timeUtils.ChangeTime(String.valueOf(time),"yyyy-MM-dd HH:mm"));
            long finish_time = timeUtils.getAddTimeStamp(-7);
            String str_finish_time = timeUtils.ChangeTime(String.valueOf(finish_time), "yyyy-MM-dd HH:mm");
            map1.put("finish_time", str_finish_time);
            redisUtil.lSet("user_request", map1);
            redisUtil.lSet("uid_" + uid, map1);


        }

        @Test
        void getUserRequest () {
            String uid = "1";
            int pageN = 1;
            int pageS = 5;
            long len = redisUtil.lGetListSize("uid_" + uid);
            Map<String, Object> map1 = new HashMap<>();
            if (len == 0) {

                map1.put("length", 0);
                map1.put("data", null);

            } else {
                int start = (pageN - 1) * pageS;
                int end = pageN * pageS - 1;
                if (start > len - 1) {

                    map1.put("length", 0);
                    map1.put("data", null);

                } else {
                    List<Object> list = redisUtil.lGet("uid_" + uid, start, (len - 1) < end ? len - 1 : end);

                    map1.put("length", list.size());
                    map1.put("data", list);

                }
                System.out.println(map1);

            }
        }
        @Test
        void canceluserRequest () {
            String uid = "1";
            String request_id = "323608";
            Object find = null;
            int flag = 0;
            for (Object i : redisUtil.lGet("uid_" + uid, 0, -1)
            ) {

                Map<String, Object> map1 = (Map<String, Object>) i;
                System.out.println(i);
                if (map1.get("request_id").equals(request_id)) {
                    redisUtil.lRemove("user_request", 1, i);

                    find = map1;
                    map1.replace("status", "cancel");
                    if ((Integer) map1.get("eid") != -1) {
                        int flag1 = 0;
                        for (Object j : redisUtil.lGet("eid_" + map1.get("eid"), 0, -1)) {
                            Map<String, Object> map2 = (Map<String, Object>) j;
                            if (map2.get("request_id").equals(request_id)) {
                                map2.replace("status", "cancel");
                                redisUtil.lUpdateIndex("eid_" + map1.get("eid"), flag1, map2);
                                break;
                            }
                            flag1++;
                        }
                    }
                    redisUtil.lUpdateIndex("uid_" + uid, flag, map1);
                    break;
                }
                flag++;

            }
        }
        @Test
        void acceptUserRequest () {
            String uid = "1";
            String eid = "1";
            String request_id = "701733";
            //����user_request
            List<Object> user_requests = redisUtil.lGet("user_request", 0, -1);
            for (Object i :
                    user_requests) {
                Map<String, Object> map1 = (Map<String, Object>) i;
                if (map1.get("request_id").equals(request_id)) {
                    redisUtil.lRemove("user_request", 1, i);
                    break;
                }
            }
            //�����û�����list
            int flag = 0;
            List<Object> list = redisUtil.lGet("uid_" + uid, 0, -1);
            for (Object i : list) {
                Map<String, Object> map1 = (Map<String, Object>) i;
                if (map1.get("request_id").equals(request_id)) {
                    map1.replace("status", "accept");
                    map1.replace("eid", eid);
                    redisUtil.lUpdateIndex("uid_" + uid, flag, map1);
                    //��ӿ��Ա����list
                    redisUtil.lSet("eid_" + eid, map1);
                    break;
                }
                flag++;
            }
        }
        @Test
        void get_user_request () {
            int pageN = (Integer) 1;
            int pageS = (Integer) 5;
            String location = String.valueOf("福建省福州市闽侯县");
            List<Object> list = redisUtil.lGet("user_request", 0, -1);
            List<Map<String, Object>> list1 = new ArrayList<>();
            for (Object i :
                    list) {
                Map<String, Object> map1 = (Map<String, Object>) i;
                if (map1.get("location").equals(location)) {
                    list1.add(map1);
                }

            }
            int len = list1.size();
            int start = (pageN - 1) * pageS;
            int end = pageN * pageS - 1;
            Map<String, Object> map2 = new HashMap<>();
            if (start > len - 1) {
                map2.put("data", null);
                map2.put("length", 0);
            } else {
                System.out.println(start);
                System.out.println(len - 1);
                List Sublist = list1.subList(start, Math.min((len - 1), end));
                map2.put("data", Sublist);
                map2.put("length", Sublist.size());
            }
            System.out.println(map2);
        }

        @Test
        void finishRequest () {
            String request_id = "701733";
            String eid = "1";
            String uid = "1";
            //修改用户请求表状态
            int flag = 0;
            List<Object> list = redisUtil.lGet("uid_" + uid, 0, -1);
            for (Object i : list) {
                Map<String, Object> map1 = (Map<String, Object>) i;
                if (map1.get("request_id").equals(request_id)) {
                    map1.replace("status", "finish");
                    redisUtil.lUpdateIndex("uid_" + uid, flag, map1);
                    break;
                }
                flag++;
            }
            //修改快递员请求表状态
            flag = 0;
            list = redisUtil.lGet("eid_" + eid, 0, -1);
            for (Object i : list) {
                Map<String, Object> map1 = (Map<String, Object>) i;
                if (map1.get("request_id").equals(request_id)) {
                    map1.replace("status", "finish");
                    redisUtil.lUpdateIndex("eid_" + eid, flag, map1);
                    break;
                }
                flag++;
            }
        }
    }
