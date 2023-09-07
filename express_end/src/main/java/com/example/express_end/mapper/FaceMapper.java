package com.example.express_end.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;
import java.util.Map;

@Mapper
public interface FaceMapper {
    @Select({"select * from face limit #{begin},#{page_size}"})
    ArrayList<Map<String,Object>> geFaceList(Map<String,Object> map);
    @Select({"select count(*) from face"})
    Integer geFaceListCount(Map<String,Object> map);
}
