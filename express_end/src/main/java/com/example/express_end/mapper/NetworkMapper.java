package com.example.express_end.mapper;

import com.example.express_end.entity.Network;
import org.apache.ibatis.annotations.*;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.Map;

@Mapper
public interface NetworkMapper {
    //获得所有网点列表
    @Select({"select * from network limit #{begin},#{page_size}"})
    ArrayList<Network> getNetworkList(Map<String,Object> map);
    @Select({"select count(*) from network"})
    Integer getNetworkListCount(Map<String,Object> map);

    //获得附近网点列表
    @Select({"select * from network where location=#{location} limit #{begin},#{page_size}"})
    ArrayList<Network> getNearNetworkList(Map<String,Object> map);
    @Select({"select count(*) from network where location=#{location}"})
    Integer getNearNetworkListCount(Map<String,Object> map);

    @Select({"select * from network where nid=#{nid}"})
    Network getNetworkByNid(Map<String,Object> map);


    //网点的快件数量+1
    @Update({"update network set number=number+1 where nid=#{nid}"})
    Integer addNumber(Map<String,Object> map);

    //网点的快件数量-1
    @Update({"update network set number=number-1 where nid=#{nid}"})
    Integer subNumber(Map<String,Object> map);

    //新建网点
    @Options(useGeneratedKeys = true, keyProperty = "nid", keyColumn = "nid")
    @Insert({"insert into network (network_name,location,address) values (#{network_name},#{location},#{address})"})
    Integer insertNetwork(Map<String,Object> map);

    //删除网点
    @Delete({"delete from network where nid=#{nid}"})
    Integer deleteNetwork(Integer nid);

    @Select({"select nid from network limit #{begin},#{page_size}"})
    ArrayList<Integer> getNetworkNidList(Map<String, Object> map);
    @Select({"select count(*) from network"})
    Integer getNetworkNidListCount(Map<String, Object> map);
    //管理员添加网点管理员
    @Options(useGeneratedKeys = true, keyProperty = "aid",keyColumn = "aid")
    @Insert({"insert into admin (nid,username,password,email,phone,info," +
            "id_card,real_name,nickname,face)" +
            " value (#{nid},#{username},#{password},#{email},#{phone},#{info}," +
            "#{id_card},#{real_name},#{nickname},#{face})"})
    Integer addNetworkAdmin(Map<String ,Object> map);

    //管理员删除网点管理员
    @Delete({"delete from admin where aid =#{aid}"})
    Integer deleteNetworkAdmin(Map<String ,Object> map);

    //获取所有网点
    @Select({"select * from network limit #{begin},#{page_size}"})
    ArrayList<Network> getAllNetworkList(Map<String,Object> map);
    @Select({"select count(*) from network "})
    Integer getAllNetworkListCount(Map<String,Object> map);


}
