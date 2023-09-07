package com.example.express_end.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

//实体类用于与数据库对接，然后返回给前端的数据
@Data // getter setter @toString
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @TableId(type= IdType.AUTO)
    private Integer uid;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String info;
    private String nickname;
    private String id_card;
    private String real_name;
    private String location;
    private String create_time;
    private String face;
    public Map<String,Object> toMap(){
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("uid",uid);
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
