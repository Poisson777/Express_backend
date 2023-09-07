package com.example.express_end.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;
import java.util.Map;

@Mapper
public interface ChinaMapper {
    @Select({"select * from china where pid=#{pid} and id<>0"})
    ArrayList<Map<String,Object>> getListByPid(Integer pid);
}
