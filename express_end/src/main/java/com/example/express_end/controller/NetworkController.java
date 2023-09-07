package com.example.express_end.controller;

import com.example.express_end.Respond.RespondBean;
import com.example.express_end.entity.Admin;
import com.example.express_end.mapper.NetworkMapper;
import com.example.express_end.service.AdminService;
import com.example.express_end.service.ExpressService;
import com.example.express_end.service.NetworkService;
import com.example.express_end.utils.WebLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/network")
public class NetworkController {
    @Autowired
    AdminService adminService;
    @Autowired
    ExpressService expressService;
    @Autowired
    NetworkService networkService;
    //根据eid获取快递信息
    @ResponseBody
    @RequestMapping(value = "/getNetworkByNid", method = RequestMethod.POST)
    RespondBean getExpressByEid(@RequestBody Map<String,Object> map){
        return networkService.getNetWorkByNid(map);
    }
    //获取网点内部的快递列表
    @ResponseBody
    @RequestMapping(value = "/getExpressList", method = RequestMethod.POST)
    RespondBean getExpressListByNid(@RequestBody Map<String,Object> map){
        return networkService.getExpressListByNid(map);
    }
    //入库，网点在添加一个快递到自己的仓库里
    @ResponseBody
    @RequestMapping(value = "/addExpress", method = RequestMethod.POST)
    RespondBean addExpress(@RequestBody Map<String,Object> map){
        return networkService.addExpress(map);
    }
    //出库
    @ResponseBody
    @RequestMapping(value = "/deliverExpress", method = RequestMethod.POST)
    RespondBean deliverExpress(@RequestBody Map<String,Object> map){
        return networkService.deliverExpress(map);
    }

    //获取附近网点列表
    @ResponseBody
    @RequestMapping(value = "/getNearNetworkList", method = RequestMethod.POST)
    RespondBean getNearNetworkList(@RequestBody Map<String,Object> map){
        return networkService.getNearNetworkList(map);
    }
    @ResponseBody
    @RequestMapping(value = "/getAllNetworkList", method = RequestMethod.POST)
    RespondBean getAllNetworkList(@RequestBody Map<String,Object> map){
        return networkService.getAllNetworkList(map);
    }

    //添加快递、
    @ResponseBody
    @RequestMapping(value = "/insertExpress", method = RequestMethod.POST)
    RespondBean insertExpressByAdmin(@RequestBody Map<String,Object> map){
        return adminService.insertExpressByAdmin(map);
    }
    //删除快递、
    @ResponseBody
    @RequestMapping(value = "/deleteExpress", method = RequestMethod.POST)
    RespondBean deleteExpress(@RequestBody Map<String,Object> map){
        return adminService.deleteExpress(map);
    }

    //获取所有网点列表
    @ResponseBody
    @RequestMapping(value = "/getNetworkList", method = RequestMethod.POST)
    RespondBean getNetworkList(@RequestBody Map<String,Object> map){
        return networkService.getNetworkList(map);
    }

    //添加网点
    @ResponseBody
    @RequestMapping(value = "/insertNetwork", method = RequestMethod.POST)
    RespondBean insertNetwork(@RequestBody Map<String,Object> map){
        return networkService.insertNetwork(map);
    }

    //删除网点
    @ResponseBody
    @RequestMapping(value = "/deleteNetwork", method = RequestMethod.POST)
    RespondBean deleteNetwork(@RequestBody Map<String,Object> map){
        return networkService.deleteNetwork(map);
    }

    //获取人员列表
    @ResponseBody
    @RequestMapping(value = "/getWorkerList", method = RequestMethod.POST)
    RespondBean getWorkerList(@RequestBody Map<String,Object> map){
        return networkService.getWorkerList(map);
    }

    //获取人员信息
    @ResponseBody
    @RequestMapping(value = "/getWorkerByWid", method = RequestMethod.POST)
    RespondBean getWorkerByWid(@RequestBody Map<String,Object> map){
        return networkService.getWorkerByWid(map);
    }

    //更新人员信息
    @ResponseBody
    @RequestMapping(value = "/updateWorkerInfo", method = RequestMethod.POST)
    RespondBean updateWorkerInfo(@RequestBody Map<String,Object> map){
        return networkService.updateWorkerInfo(map);
    }

    //添加人员
    @ResponseBody
    @RequestMapping(value = "/insertWorker", method = RequestMethod.POST)
    RespondBean insertWorker(@RequestBody Map<String,Object> map){
        return networkService.insertWorker(map);
    }

    //删除人员
    @ResponseBody
    @RequestMapping(value = "/deleteWorker", method = RequestMethod.POST)
    RespondBean deleteWorker(@RequestBody Map<String,Object> map){
        return networkService.deleteWorker(map);
    }
    //人员调度，更改nid
    @ResponseBody
    @RequestMapping(value = "/updateWorkerNid", method = RequestMethod.POST)
    RespondBean updateWorkerNid(@RequestBody Map<String,Object> map){
        return networkService.updateWorkerNid(map);
    }
    //获取所有成员列表
    @ResponseBody
    @RequestMapping(value = "/getAllWorkerList", method = RequestMethod.POST)
    RespondBean getAllWorkerList(@RequestBody Map<String,Object> map){
        return networkService.getAllWorkerList(map);
    }
}
