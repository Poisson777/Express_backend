package com.example.express_end.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.express_end.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface GlobalMapper  extends BaseMapper{
    @Select("select count(*) from user where email=#{email}")
    int isExistUserEmail(String email);

    @Select("select count(*) from expressman where email=#{email}")
    int isExistExpressEmail(String email);

    @Select("select count(*) from admin where email=#{email}")
    int isExistNetworkEmail(String email);
}
