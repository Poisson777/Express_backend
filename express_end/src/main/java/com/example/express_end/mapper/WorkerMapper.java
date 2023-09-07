package com.example.express_end.mapper;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;
import java.util.Map;

@Mapper
public interface WorkerMapper {
    @Select({"select * from network_worker where nid=#{nid} limit #{begin},#{page_size}"})
    ArrayList<Map<String,Object>> getWorkerList(Map<String,Object> map);
    @Select({"select count(*) from network_worker where nid=#{nid}"})
    Integer getWorkerListCount(Map<String,Object> map);

    @Select({"select * from network_worker limit #{begin},#{page_size}"})
    ArrayList<Map<String,Object>> getAllWorkerList(Map<String,Object> map);
    @Select({"select count(*) from network_worker"})
    Integer getAllWorkerListCount(Map<String,Object> map);


    @Select({"select * from network_worker where wid=#{wid}"})
    Map<String,Object> getWorkByWid(Map<String,Object> map);

    @Delete({"delete from network_worker where wid=#{wid}"})
    Integer deleteWorker(Map<String ,Object> map);

    @Update({"update network_worker set real_name=#{real_name},phone=#{phone},id_card=#{id_card},role=#{role} where wid=#{wid}"})
    Integer updateWorker(Map<String ,Object> map);

    @Update({"update network_worker set nid=#{nid} where wid=#{wid}"})
    Integer updateWorkerNid(Map<String ,Object> map);

    @Insert({"insert into network_worker (nid,real_name,phone,id_card,role) values (#{nid},#{real_name},#{phone},#{id_card},#{role})"})
    Integer insertWorker(Map<String ,Object> map);


}
