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
public class Log {
    @TableId(type= IdType.AUTO)
    private Integer lid;
    private Integer uid;
    private String operator_name;
    private String operate_type;
    private String operate_info;
    private String create_time;
    public Map<String,Object> toMap(){
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("lid",lid);
        map.put("uid",uid);
        map.put("operator_name",operator_name);
        map.put("operate_type",operate_type);
        map.put("operate_info",operate_info);
        map.put("create_time",create_time);
        return map;
    }
}
