package com.example.express_end.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Request {
    @TableId(type = IdType.AUTO)
    private Integer rid;
    private Integer uid;
    private Integer mid;
    private String location;
    private String address;
    private String create_time;
}
