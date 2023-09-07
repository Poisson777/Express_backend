package com.example.express_end.service;

import com.example.express_end.Respond.RespondBean;
import com.example.express_end.Respond.RespondEnum;
import com.example.express_end.mapper.Express.ExpressMapper;
import com.example.express_end.mapper.NetworkMapper;
import com.example.express_end.mapper.WorkerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class NetworkService {
    @Autowired
    NetworkMapper networkMapper;
    @Autowired
    ExpressMapper expressMapper;
    @Autowired
    WorkerMapper workerMapper;
    public RespondBean getNetWorkByNid(Map<String, Object> map) {
        return RespondBean.success(networkMapper.getNetworkByNid(map));
    }

    public RespondBean getExpressListByNid(Map<String, Object> map) {
        map.putIfAbsent("page_size", 5);
        map.put("begin",((int)map.get("page_num")-1)*(int)map.get("page_size"));
        Map<String, Object> map2=new HashMap<>();
        map2.put("data",expressMapper.getExpressListByNid(map));
        map2.put("count",expressMapper.getExpressListByNidCount(map));
        return RespondBean.success(map2);
    }

    public RespondBean addExpress(Map<String, Object> map) {
        Map<String, Object> map1=expressMapper.getExpressByEid2(map);
        if(map1==null){
            return RespondBean.error(RespondEnum.FIELD_NOT_FIND);
        }
        if (map.get("nid").toString().equals("0")){
            return RespondBean.error(RespondEnum.PARAMETER_NULL_ERROR);
        }
        if(map1.get("nid").toString().equals(map.get("nid").toString())){
            return RespondBean.error(RespondEnum.ADD_WARNING);
        }
        if(expressMapper.addExpress(map)==1&&networkMapper.addNumber(map)==1) {
            return RespondBean.success();
        }
        else {
            return RespondBean.error(RespondEnum.FIELD_NOT_FIND);
        }
    }

    public RespondBean deliverExpress(Map<String, Object> map) {
        if(networkMapper.subNumber(map)==1) {
            map.put("nid",0);
            if (expressMapper.updateNid(map)==1){
                return RespondBean.success();
            }
            else {
                return RespondBean.error(RespondEnum.FIELD_NOT_FIND);
            }
        }
        else {
            return RespondBean.error(RespondEnum.FIELD_NOT_FIND);
        }
    }

    public RespondBean getNearNetworkList(Map<String, Object> map) {
        map.putIfAbsent("page_size", 5);
        map.put("begin",((int)map.get("page_num")-1)*(int)map.get("page_size"));
        Map<String, Object> map2=new HashMap<>();
        map2.put("data",networkMapper.getNearNetworkList(map));
        map2.put("count",networkMapper.getNearNetworkListCount(map));
        return RespondBean.success(map2);
    }
    public RespondBean getAllNetworkList(Map<String, Object> map) {
        map.putIfAbsent("page_size", 5);
        map.put("begin",((int)map.get("page_num")-1)*(int)map.get("page_size"));
        Map<String, Object> map2=new HashMap<>();
        map2.put("data",networkMapper.getAllNetworkList(map));
        map2.put("count",networkMapper.getAllNetworkListCount(map));
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

    public RespondBean insertNetwork(Map<String, Object> map) {
        return RespondBean.success(networkMapper.insertNetwork(map));
    }

    public RespondBean deleteNetwork(Map<String, Object> map) {
        Integer nid = Integer.parseInt(String.valueOf(map.get("nid")));
        if (networkMapper.deleteNetwork(nid)==1) {
            return RespondBean.success();
        }
        else {
            return RespondBean.error(RespondEnum.FIELD_NOT_FIND);
        }
    }

    public RespondBean getWorkerList(Map<String, Object> map) {
        map.putIfAbsent("page_num", 1);
        map.putIfAbsent("page_size", 5);
        map.put("begin",((int)map.get("page_num")-1)*(int)map.get("page_size"));
        Map<String, Object> map2=new HashMap<>();
        map2.put("data",workerMapper.getWorkerList(map));
        map2.put("count",workerMapper.getWorkerListCount(map));
        return RespondBean.success(map2);
    }


    public RespondBean getWorkerByWid(Map<String, Object> map) {
        Map<String,Object> map1=workerMapper.getWorkByWid(map);
        if (map1==null){
            return RespondBean.error(RespondEnum.FIELD_NOT_FIND);
        }
        else {
            return RespondBean.success(map1);
        }

    }

    public RespondBean updateWorkerInfo(Map<String, Object> map) {
        if(workerMapper.updateWorker(map)!=0)
        {
            return RespondBean.success();
        }
        else {
            return RespondBean.error(RespondEnum.FIELD_NOT_FIND);
        }
    }

    public RespondBean insertWorker(Map<String, Object> map) {
        workerMapper.insertWorker(map);
        return RespondBean.success();
    }

    public RespondBean deleteWorker(Map<String, Object> map) {
        if(workerMapper.deleteWorker(map)!=0)
        {
            return RespondBean.success();
        }
        else {
            return RespondBean.error(RespondEnum.FIELD_NOT_FIND);
        }
    }

    public RespondBean updateWorkerNid(Map<String, Object> map) {
        if(workerMapper.updateWorkerNid(map)!=0)
        {
            return RespondBean.success();
        }
        else {
            return RespondBean.error(RespondEnum.FIELD_NOT_FIND);
        }
    }

    public RespondBean getAllWorkerList(Map<String, Object> map) {
        map.putIfAbsent("page_num", 1);
        map.putIfAbsent("page_size", 5);
        map.put("begin",((int)map.get("page_num")-1)*(int)map.get("page_size"));
        Map<String, Object> map2=new HashMap<>();
        map2.put("data",workerMapper.getAllWorkerList(map));
        map2.put("count",workerMapper.getAllWorkerListCount(map));
        return RespondBean.success(map2);
    }
}
