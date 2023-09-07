package com.example.express_end.controller;

import com.example.express_end.Respond.RespondBean;
import com.example.express_end.service.ExpressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/express")
public class ExpressController {
    @Autowired
    ExpressService expressService;
    //根据eid获取快递信息
    @ResponseBody
    @RequestMapping(value = "/getExpressByEid", method = RequestMethod.POST)
    RespondBean getExpressByEid(@RequestBody Map<String,Object> map){
        return expressService.getExpressByEid(map);
    }
    @ResponseBody
    @RequestMapping(value = "/setState", method = RequestMethod.POST)
    RespondBean setState(@RequestBody Map<String,Object> map){
        return expressService.setState(map);
    }


}
