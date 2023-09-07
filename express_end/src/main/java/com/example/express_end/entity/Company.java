package com.example.express_end.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Company {
    @TableId(type= IdType.AUTO)
    private Integer cid;
    private String company_info;
    private String app_info;
    private String contact;
}
