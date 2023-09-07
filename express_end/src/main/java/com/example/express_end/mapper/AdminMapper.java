package com.example.express_end.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;
import java.util.Map;

@Mapper
public interface AdminMapper {
    @Select({"select * from admin where is_super<>0 limit #{begin},#{page_size}"})
    ArrayList<Map<String,Object>> getNetworkAdminList(Map<String,Object> map);
    @Select({"select count(*) from admin where aid<>0"})
    Integer getNetworkAdminListCount(Map<String,Object> map);

    @Select({"select * from admin where is_super=0 limit #{begin},#{page_size}"})
    ArrayList<Map<String,Object>> getSuperAdminList(Map<String,Object> map);
    @Select({"select count(*) from admin where aid=0"})
    Integer getSuperAdminListCount(Map<String,Object> map);

    @Select({"select * from admin  limit #{begin},#{page_size}"})
    ArrayList<Map<String,Object>> getAllAdminList(Map<String,Object> map);
    @Select({"select count(*) from admin "})
    Integer getAllAdminListCount(Map<String,Object> map);
    @Select({"select * from admin where real_name like '%${real_name}%' limit #{begin},#{page_size}"})
    ArrayList<Map<String,Object>> getAdminListByName(Map<String,Object> map);
    @Select({"select count(*) from admin where real_name like '%${real_name}%'"})
    Integer getAdminListCountByName(Map<String,Object> map);
}
