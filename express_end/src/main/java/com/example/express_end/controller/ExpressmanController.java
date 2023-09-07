package com.example.express_end.controller;


import com.example.express_end.Respond.RespondBean;
import com.example.express_end.Respond.RespondEnum;
import com.example.express_end.entity.Request;
import com.example.express_end.service.ExpressService;
import com.example.express_end.service.NetworkService;
import com.example.express_end.service.RequestService;
import com.example.express_end.service.expressServices.ExpressmanService;
import com.example.express_end.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

@Validated
@Controller
@RequestMapping("/expressman")
public class ExpressmanController {
    @Autowired
    ExpressmanService expressmanService;
    @Autowired
    RequestService requestService;
    @Autowired
    NetworkService networkService;

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public RespondBean login(@RequestBody @Valid LoginForm loginForm){
        return expressmanService.login(loginForm);
    }

    @ResponseBody
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    RespondBean register(HttpServletRequest request, @RequestBody @Valid RegisterForm registerForm){
        return expressmanService.register(request,registerForm);
    }

    @ResponseBody
    @RequestMapping(value = "/register2", method = RequestMethod.POST)
    RespondBean register( @RequestBody @Valid RegisterForm registerForm){
        return expressmanService.register2(registerForm);
    }

    @RequestMapping(value = "/updateInfo", method = RequestMethod.POST)
    @ResponseBody
    RespondBean updateUserInfo(@RequestBody @Valid EUpdateForm updateForm){
        return expressmanService.updateUserInfo(updateForm);
    }
    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    @ResponseBody
    RespondBean updatePassword(HttpServletRequest request,@RequestBody Map<String,Object> map){
        return expressmanService.updatePassword(request,map);
    }
    @RequestMapping(value = "/updatePassword2", method = RequestMethod.POST)
    @ResponseBody
    RespondBean updatePassword2(@RequestBody Map<String,Object> map){
        return expressmanService.updatePassword2(map);
    }

    @RequestMapping(value = "/getExpressmanByMid", method = RequestMethod.POST)
    @ResponseBody
    RespondBean getUserById(@RequestBody Map<String,Object> map){
        return expressmanService.getUserById(map);
    }

    @RequestMapping(value = "/authExpressman", method = RequestMethod.POST)
    @ResponseBody
    RespondBean authUser(@RequestBody @Valid EAuthForm authForm){
        return expressmanService.authExpressman(authForm);
    }

    @RequestMapping(value = "/updateLocation", method = RequestMethod.POST)
    @ResponseBody
    RespondBean updateLocation(@RequestBody Map<String, Object> map){
        return expressmanService.updateLocation(map);
    }

    //获取某个网点的待揽件列表
    @ResponseBody
    @RequestMapping(value = "/getWaitSendExpressByNid", method = RequestMethod.POST)
    RespondBean getWaitSendExpress(@RequestBody Map<String,Object> map){
        return expressmanService.getWaitSendExpress(map);
    }


    //获取附近待揽件列表
    @ResponseBody
    @RequestMapping(value = "/getNearWaitSendExpressList", method = RequestMethod.POST)
    RespondBean getNearWaitSendExpress(@RequestBody Map<String,Object> map){
        return expressmanService.getNearWaitSendExpress(map);
    }



    //获取自己处理过的快递的列表
    @ResponseBody
    @RequestMapping(value = "/getExpressList", method = RequestMethod.POST)
    RespondBean getExpressList(@RequestBody Map<String,Object> map){
        return expressmanService.getExpressList(map);
    }

    @ResponseBody
    @RequestMapping(value = "/getExpressListByName", method = RequestMethod.POST)
    RespondBean getExpressListByName(@RequestBody Map<String,Object> map){return expressmanService.getExpressListByName(map);}

    //揽件

    @ResponseBody
    @RequestMapping(value = "/canvassExpress", method = RequestMethod.POST)
    RespondBean canvassExpress(@RequestBody Map<String,Object> map){
        return expressmanService.canvassExpress(map);
    }


    //由快递员创建新的快递
    @ResponseBody
    @RequestMapping(value = "/insertExpress", method = RequestMethod.POST)
    RespondBean insertExpress(@RequestBody Map<String,Object> map){
        return expressmanService.insertExpress(map);
    }

    //redis
    //快递员接受上门请求
    @ResponseBody
    @RequestMapping(value = "/acceptRequest", method = RequestMethod.POST)

    RespondBean acceptRequest(@RequestBody Map<String,Object> map){
        return requestService.acceptUserRequest(map);
    }


    //获取附近网点列表
    @ResponseBody
    @RequestMapping(value = "/getNearNetworkList", method = RequestMethod.POST)
    RespondBean getNearNetworkList(@RequestBody Map<String,Object> map){
        return networkService.getNearNetworkList(map);
    }

    //快递员获取自身接收的订单
    @ResponseBody
    @RequestMapping(value = "/getAcceptRequest",method = RequestMethod.POST)
    RespondBean getAcceptRequest(@RequestBody Map<String ,Object> map){
        return requestService.getExpressAcceptRequest(map);
    }
    //快递员查看当前待接受用户请求
    @ResponseBody
    @RequestMapping(value = "/getUserRequest",method = RequestMethod.POST)
    RespondBean getUserRequest(@RequestBody Map<String,Object> map){
        return requestService.getUserRequest(map);
    }

    //快递员完成上门取件请求
    @ResponseBody
    @RequestMapping(value = "/finishUserRequest",method = RequestMethod.POST)
    RespondBean finishUserRequest(@RequestBody Map <String,Object> map){
        return requestService.finishUserRequest(map);
    }



}
