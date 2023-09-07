package com.example.express_end.mapper.Express;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.express_end.entity.Express;
import org.apache.ibatis.annotations.*;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.Map;

@Mapper
public interface ExpressMapper extends BaseMapper<Express> {
    //更新状态,修改快件状态
    @Update({"update express set state=#{state} where eid=#{eid}"})
    Integer setState(Map<String,Object> map);


    //用户获取自己发送的快递，根据state筛选，begin，page_size分页，state=all时用sql拼接获取所有+
    ArrayList<Map<String, Object>> getSendExpressListByUid(Map<String,Object> map);
    Integer getSendExpressListByUidCount(Map<String,Object> map);


    //用户获取自己收到的快递，根据state筛选，begin，page_size分页，state=all时用sql拼接获取所有+
    ArrayList<Map<String, Object>> getReceiptExpressListByUid(Map<String,Object> map);
    Integer getReceiptExpressListByUidCount(Map<String,Object> map);


    //网点获取自己站中的快递，state筛选,begin，page_size分页；state=目的站，即带揽件列表+
    ArrayList<Map<String, Object>> getExpressListByNid(Map<String,Object> map);
    Integer getExpressListByNidCount(Map<String,Object> map);

    //获取附近的揽件列表
    ArrayList<Map<String, Object>> getNearExpressList(Map<String,Object> map);
    Integer getNearExpressListCount(Map<String,Object> map);


    //快递员获取自己完成的快递，配送中的快递放在字段中+
    ArrayList<Map<String, Object>> getExpressListByMid(Map<String,Object> map);
    Integer getExpressListByMidCount(Map<String,Object> map);


    //获取所有的快递，用state筛选+
    ArrayList<Map<String, Object>> getAllExpressList(Map<String,Object> map);
    Integer getAllExpressListCount(Map<String,Object> map);


    //根据eid获取快递
    ArrayList<Map<String, Object>> getExpressByEid(Map<String,Object> map);

    Map<String,Object> getExpressByEid2(Map<String,Object> map);
    //获取自己揽件列表

    //修改快件当前地址，输入当前路径+
    @Update({"update express set current_location=#{current_location},router=concat(concat(router,'->'),#{current_location}) where eid=#{eid}"})
    Integer updateCurLocation(Map<String,Object> map);
    //修改快件的路径,输入当前路径，可直接拼接，与上面的修改快件地址同时使用+
    @Update({"update express set router=concat(concat(router,'->'),#{current_location}) where eid=#{eid}"})
    Integer updateRouter(Map<String,Object> map);


    //用户确认收货，仅需提供eid+
    @Update({"update express set state='已送达',receive_time=#{receive_time} where eid=#{eid}"})
    Integer confirmReceipt(Map<String,Object> map);

    //修改最终站的目的地
    @Update({"update express set final_network=#{final_network} where eid=#{eid}"})
    Integer updateFinalNetwork(Map<String,Object> map);

    //修改nid
    @Update({"update express set nid=#{nid} where eid=#{eid}"})
    Integer updateNid(Map<String,Object> map);

    //入库
    @Update({"update express set nid=#{nid},state='中转站' where eid=#{eid}"})
    Integer addExpress(Map<String,Object> map);

    //周转快递，快递到达中间某个网点时候,修改一系列信息,此时传入的是nid和eid+
    @Update({"update express INNER JOIN network ON express.eid=#{eid} and network.nid=#{nid} " +
            "set express.nid=#{nid},express.current_location=network.location,express.router=concat(concat(router,'->'),network.location)"})
    Integer turnExpress(Map<String,Object> map);


    //揽件
    @Update({"update express set mid=#{mid},state='配送中' where eid=#{eid}"})
    Integer canvassExpress(Map<String,Object> map);

    //删除指定快递+
    @Delete({"delete from express where eid=#{eid}"})
    Integer deleteExpress(Map<String,Object> map);

    //快递员新增快递+
    @Options(useGeneratedKeys = true, keyProperty = "eid", keyColumn = "eid")
    @Insert({"insert into express " +
            "(mid,name,from_uid,to_uid,from_location,to_location,from_address,to_address,current_location,weight,type,state,router,code,expect_receive_time) values" +
            "(#{mid},#{name},#{from_uid},#{to_uid},#{from_location},#{to_location},#{from_address},#{to_address}," +
            "#{from_location},#{weight},#{type},'待入库',#{from_location},#{code},#{expect_receive_time})"})
    Integer insertExpressByExpressman(Map<String,Object> map);


    //由网点新增快递会有所不同+
    @Options(useGeneratedKeys = true, keyProperty = "eid", keyColumn = "eid")
    @Insert({"insert into express " +
            "(name,nid,from_uid,to_uid,from_location,to_location,from_address,to_address,current_location,weight,type,state,router,code,expect_receive_time) values" +
            "(#{name},#{nid},#{from_uid},#{to_uid},#{from_location},#{to_location},#{from_address},#{to_address}," +
            "#{from_location},#{weight},#{type},'周转中',#{from_location},#{code},#{expect_receive_time})"})
    Integer insertExpressByNetwork(Map<String,Object> map);


    ArrayList<Map<String, Object>> getExpressListByName(Map<String ,Object> map);

    @Select({"select count(*) from express where name=#{name}"})
    Integer getExpressListByNameCount(Map <String,Object> map);

    @Update({"update express set nid=0 where state='周转中' or state='待入库' or state='配送中'"})
    Integer reshapeExpress();

    @Update({"update express set nid=1 where state='中转站' and nid=0"})
    Integer reshapeExpress2();

    @Update({"update express set mid=0 where state='中转站' or state='周转中'"})
    Integer reshapeExpress3();
}
