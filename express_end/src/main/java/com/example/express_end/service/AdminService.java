package com.example.express_end.service;

import com.example.express_end.Respond.RespondBean;
import com.example.express_end.Respond.RespondEnum;
import com.example.express_end.entity.User;
import com.example.express_end.mapper.*;
import com.example.express_end.mapper.Express.ExpressMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class AdminService {
    @Autowired
    ExpressmanMapper expressmanMapper;
    @Autowired
    ExpressMapper expressMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    NetworkMapper networkMapper;
    @Autowired
    LogMapper logMapper;
    @Autowired
    AdminMapper adminMapper;


    public RespondBean getUserList(Map<String, Object> map) {
        map.putIfAbsent("page_size", 5);
        map.putIfAbsent("page_num", 1);
        map.put("begin",((int)map.get("page_num")-1)*(int)map.get("page_size"));
        Map<String, Object> map2=new HashMap<>();
        map2.put("data",userMapper.getUserList(map));
        map2.put("count",userMapper.getUserListCount(map));
        return RespondBean.success(map2);
    }

    public RespondBean getExpressmanList(Map<String, Object> map) {
        map.putIfAbsent("page_size", 5);
        map.put("begin",((int)map.get("page_num")-1)*(int)map.get("page_size"));
        Map<String, Object> map2=new HashMap<>();
        map2.put("data",expressmanMapper.getUserList(map));
        map2.put("count",expressmanMapper.getUserListCount(map));
        return RespondBean.success(map2);
    }

    public RespondBean getExpressList(Map<String, Object> map) {
        map.putIfAbsent("state", "所有");
        map.putIfAbsent("page_size", 5);
        map.put("begin",((int)map.get("page_num")-1)*(int)map.get("page_size"));
        Map<String, Object> map2=new HashMap<>();
        String[] ss=map.get("state").toString().split(";");
        map.put("state", ss);
        map2.put("data",expressMapper.getAllExpressList(map));
        map2.put("count",expressMapper.getAllExpressListCount(map));
        return RespondBean.success(map2);
    }

    public RespondBean getNetworkList(Map<String, Object> map) {
        map.putIfAbsent("page_size", 5);
        map.put("begin",((int)map.get("page_num")-1)*(int)map.get("page_size"));
        Map<String, Object> map2=new HashMap<>();
        map2.put("data",networkMapper.getNetworkList(map));
        map2.put("count",networkMapper.getNetworkListCount(map));
        return RespondBean.success(map2);
    }

    public RespondBean getLocationNetworkList(Map<String, Object> map) {
        map.putIfAbsent("page_size", 5);
        map.put("begin",((int)map.get("page_num")-1)*(int)map.get("page_size"));
        Map<String, Object> map2=new HashMap<>();
        map2.put("data",networkMapper.getNearNetworkList(map));
        map2.put("count",networkMapper.getNearNetworkListCount(map));
        return RespondBean.success(map2);
    }

    public RespondBean getLogList(Map<String, Object> map) {
        map.putIfAbsent("page_size", 5);
        map.put("begin",((int)map.get("page_num")-1)*(int)map.get("page_size"));
        Map<String, Object> map2=new HashMap<>();
        map2.put("data",logMapper.getLogList(map));
        map2.put("count",logMapper.getLogListCount(map));
        return RespondBean.success(map2);
    }

    public RespondBean insertUserByAdmin(Map<String, Object> map) {
        map.put("face","1.jpg");
        userMapper.insertUserByAdmin(map);
        return RespondBean.success(map);
    }

    public RespondBean updateUserInfoByAdmin(Map<String, Object> map) {
        if (userMapper.updateUserInfoByAdmin(map)==1){
            return RespondBean.success();
        }
        else {
            return RespondBean.error(RespondEnum.FIELD_NOT_FIND);
        }
    }

    public RespondBean deleteUserByAdmin(Map<String, Object> map) {
        if (userMapper.deleteUserByAdmin(map)==1){
            return RespondBean.success();
        }
        else {
            return RespondBean.error(RespondEnum.FIELD_NOT_FIND);
        }
    }

    public RespondBean insertExpressmanByAdmin(Map<String, Object> map) {
        map.put("face","1.jpg");
        expressmanMapper.insertExpressman(map);
        return RespondBean.success(map);
    }
    public RespondBean updateExpressmanInfoByAdmin(Map<String, Object> map) {
        if (expressmanMapper.updateExpressmanInfoByAdmin(map)==1){
            return RespondBean.success();
        }
        else {
            return RespondBean.error(RespondEnum.FIELD_NOT_FIND);
        }
    }

    public RespondBean deleteExpressmanByAdmin(Map<String, Object> map) {
        if (expressmanMapper.deleteExpressmanByAdmin(map)==1){
            return RespondBean.success();
        }
        else {
            return RespondBean.error(RespondEnum.FIELD_NOT_FIND);
        }
    }

    public RespondBean insertExpressByAdmin(Map<String, Object> map) {
        map.put("code", UUID.randomUUID().toString());
        expressMapper.insertExpressByNetwork(map);
        return RespondBean.success(map);
    }
    public RespondBean addNetworkByAdmin(Map<String ,Object> map){
        if(networkMapper.insertNetwork(map)==1){
            return RespondBean.success();
        }
        return RespondBean.error(RespondEnum.Insert_ERROR);
    }

    public RespondBean deleteNetworkByAdmin (Map < String, Object > map){
        Integer nid = Integer.parseInt(String.valueOf(map.get("nid")));
        if (networkMapper.deleteNetwork(nid) == 1) {
            return RespondBean.success();
        }
        return RespondBean.error(RespondEnum.FIELD_NOT_FIND);
    }

    public RespondBean addNetworkAdminByAdmin(Map<String ,Object> map){
        map.put("face","1.jpg");
        if(networkMapper.addNetworkAdmin(map)==1){
            return RespondBean.success();
        }
        return RespondBean.error(RespondEnum.Insert_ERROR);
    }

    public RespondBean deleteNetworkAdminByAdmin(Map<String, Object>map){
        if(networkMapper.deleteNetworkAdmin(map)==1){
            return RespondBean.success();
        }
        return RespondBean.error(RespondEnum.FIELD_NOT_FIND);
    }

    public RespondBean deleteExpress(Map<String, Object> map) {
        if(expressMapper.deleteExpress(map)==1){
            return RespondBean.success();
        }
        return RespondBean.error(RespondEnum.FIELD_NOT_FIND);
    }

    public RespondBean getNetworkAdminList(Map<String, Object> map) {
        map.putIfAbsent("page_size", 5);
        map.put("begin",((int)map.get("page_num")-1)*(int)map.get("page_size"));
        Map<String, Object> map2=new HashMap<>();
        map2.put("data",adminMapper.getNetworkAdminList(map));
        map2.put("count",adminMapper.getNetworkAdminListCount(map));
        return RespondBean.success(map2);
    }
    public RespondBean getSuperAdminList(Map<String, Object> map) {
        map.putIfAbsent("page_size", 5);
        map.put("begin",((int)map.get("page_num")-1)*(int)map.get("page_size"));
        Map<String, Object> map2=new HashMap<>();
        map2.put("data",adminMapper.getSuperAdminList(map));
        map2.put("count",adminMapper.getSuperAdminListCount(map));
        return RespondBean.success(map2);
    }
    public RespondBean getAllAdminList(Map<String, Object> map) {
        map.putIfAbsent("page_size", 5);
        map.put("begin",((int)map.get("page_num")-1)*(int)map.get("page_size"));
        Map<String, Object> map2=new HashMap<>();
        map2.put("data",adminMapper.getAllAdminList(map));
        map2.put("count",adminMapper.getAllAdminListCount(map));
        return RespondBean.success(map2);
    }

    public RespondBean getUserListByName(Map<String, Object> map) {
        map.putIfAbsent("page_size", 5);
        map.putIfAbsent("page_num", 1);
        map.put("begin",((int)map.get("page_num")-1)*(int)map.get("page_size"));
        Map<String, Object> map2=new HashMap<>();
        map2.put("data",userMapper.getUserListByName(map));
        map2.put("count",userMapper.getUserListByNameCount(map));
        return RespondBean.success(map2);
    }

    public RespondBean getExpressmanListByName(Map<String, Object> map) {
        map.putIfAbsent("page_size", 5);
        map.putIfAbsent("page_num", 1);
        map.put("begin",((int)map.get("page_num")-1)*(int)map.get("page_size"));
        Map<String, Object> map2=new HashMap<>();
        map2.put("data",expressmanMapper.getUserListByName(map));
        map2.put("count",expressmanMapper.getUserListByNameCount(map));
        return RespondBean.success(map2);
    }

    public RespondBean getAllAdminListByName(Map<String, Object> map) {
        map.putIfAbsent("page_size", 5);
        map.putIfAbsent("page_num", 1);
        map.put("begin",((int)map.get("page_num")-1)*(int)map.get("page_size"));
        Map<String, Object> map2=new HashMap<>();
        map2.put("data",adminMapper.getAdminListByName(map));
        map2.put("count",adminMapper.getAdminListCountByName(map));
        return RespondBean.success(map2);
    }

    public RespondBean getNetworkNidList(Map<String,Object> map){
        map.putIfAbsent("page_size", 5);
        map.putIfAbsent("page_num", 1);
        map.put("begin",((int)map.get("page_num")-1)*(int)map.get("page_size"));
        Map<String, Object> map2=new HashMap<>();
        map2.put("data",networkMapper.getNetworkNidList(map));
        map2.put("count",networkMapper.getNetworkNidListCount(map));
        return RespondBean.success(map2);

    }

    public RespondBean reshapeExpress(Map<String, Object> map) {
        return RespondBean.success(expressMapper.reshapeExpress3()+expressMapper.reshapeExpress2()+
                expressMapper.reshapeExpress());
    }
}
