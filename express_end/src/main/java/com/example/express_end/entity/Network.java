package com.example.express_end.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Network {
    @TableId(type= IdType.AUTO)
    private Integer nid;
    private String network_name;
    private String location;
    private String address;
    private int number;
}
