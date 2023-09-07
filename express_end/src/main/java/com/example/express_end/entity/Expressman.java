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
public class Expressman {
    @TableId(type= IdType.AUTO)
    private Integer mid;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String info;
    private String nickname;
    private String id_card;
    private String real_name;
    private String create_time;
    private String location;
    private String face;
    public Map<String,Object> toMap(){
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("mid",mid);
        map.put("username",username);
        map.put("password",password);
        map.put("email",email);
        map.put("phone",phone);
        map.put("info",info);
        map.put("nickname",nickname);
        map.put("id_card",id_card);
        map.put("real_name",real_name);
        map.put("location",location);
        map.put("create_time",create_time);
        map.put("face",face);
        return map;
    }
}
