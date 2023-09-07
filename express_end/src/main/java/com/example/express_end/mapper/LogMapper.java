package com.example.express_end.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.express_end.entity.Log;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Map;

@Repository
@Mapper
public interface LogMapper extends BaseMapper<Log> {

    @Insert({"insert into log (aid,operator_name,operate_type,operate_info) values (#{aid} ,#{operator_name},#{operate_type},#{operate_info})"})
    public void insertLog(Map map);
    @Select({"select username from admin where aid=#{aid}"})
    public String getUsernameById(Map<String,Object> map);

    @Select({"select * from log order by lid desc limit #{begin},#{page_size} "})
    ArrayList<Map> getLogList(Map<String,Object> map);
    @Select({"select count(*) from log"})
    Integer getLogListCount(Map<String,Object> map);
}
