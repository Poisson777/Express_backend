package com.example.express_end.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Express {
    @TableId(type= IdType.AUTO)
    private Integer eid;
    private String name;
    private int from_uid;
    private int to_uid;
    private int nid;
    private int mid;
    private String from_address;
    private String to_address;
    private String from_location;
    private String to_location;
    private String send_time;
    private String receive_time;
    private String expect_receive_time;
    private String current_location;
    private String final_network;
    private int weight;
    private String type;
    private String state;
    private String router;
    private String code;
    public Map<String,Object> toMap(){
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("mid",mid);
        map.put("eid",eid);
        map.put("name",name);
        map.put("from_uid",from_uid);
        map.put("to_uid",to_uid);
        map.put("nid",nid);
        map.put("from_address",from_address);
        map.put("to_address",to_address);
        map.put("from_location",from_location);
        map.put("to_location",to_location);
        map.put("send_time",send_time);
        map.put("receive_time",receive_time);
        map.put("expect_receive_time",expect_receive_time);
        map.put("current_location",current_location);
        map.put("final_network",final_network);
        map.put("weight",weight);
        map.put("type",type);
        map.put("state",state);
        map.put("router",router);
        map.put("code",code);
        return map;
    }
}
