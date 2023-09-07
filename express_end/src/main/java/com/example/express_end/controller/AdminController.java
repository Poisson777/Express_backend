package com.example.express_end.controller;

import com.example.express_end.Respond.RespondBean;
import com.example.express_end.entity.User;
import com.example.express_end.mapper.LogMapper;
import com.example.express_end.service.AdminService;
import com.example.express_end.service.ExpressService;
import com.example.express_end.service.NetworkService;
import com.example.express_end.service.UserService;
import com.example.express_end.service.expressServices.ExpressmanService;
import com.example.express_end.utils.WebLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    ExpressService expressService;
    @Autowired
    ExpressmanService expressmanService;
    @Autowired
    NetworkService networkService;
    @Autowired
    UserService userService;
    @Autowired
    AdminService adminService;
    @Autowired

    //获取用户列表
    @ResponseBody
    @RequestMapping(value = "/getUserList", method = RequestMethod.POST)
    RespondBean getUserList(@RequestBody Map<String,Object> map){
        return adminService.getUserList(map);
    }
    @ResponseBody
    @RequestMapping(value = "/getUserListByName", method = RequestMethod.POST)
    RespondBean getUserListByName(@RequestBody Map<String,Object> map){
        return adminService.getUserListByName(map);
    }
    //获取快递员列表
    @ResponseBody
    @RequestMapping(value = "/getExpressmanList", method = RequestMethod.POST)
    RespondBean getExpressmanList(@RequestBody Map<String,Object> map){
        return adminService.getExpressmanList(map);
    }
    @ResponseBody
    @RequestMapping(value = "/getExpressmanListByName", method = RequestMethod.POST)
    RespondBean getExpressmanListByName(@RequestBody Map<String,Object> map){
        return adminService.getExpressmanListByName(map);
    }
    //获取快递列表
    @ResponseBody
    @RequestMapping(value = "/getExpressList", method = RequestMethod.POST)
    RespondBean getExpressList(@RequestBody Map<String,Object> map){
        return adminService.getExpressList(map);
    }
    //获取网点列表
    @ResponseBody
    @RequestMapping(value = "/getNetworkList", method = RequestMethod.POST)
    RespondBean getNetworkList(@RequestBody Map<String,Object> map){
        return adminService.getNetworkList(map);
    }
    //获取网点管理员列表
    @ResponseBody
    @RequestMapping(value = "/getNetworkAdminList", method = RequestMethod.POST)
    RespondBean getNetworkAdminList(@RequestBody Map<String,Object> map){
        return adminService.getNetworkAdminList(map);
    }
    //获取所有管理员列表
    @ResponseBody
    @RequestMapping(value = "/getAllAdminList", method = RequestMethod.POST)
    RespondBean getAllAdminList(@RequestBody Map<String,Object> map){
        return adminService.getAllAdminList(map);
    }
    @ResponseBody
    @RequestMapping(value = "/getAdminListByName", method = RequestMethod.POST)
    RespondBean getAdminListByName(@RequestBody Map<String,Object> map){
        return adminService.getAllAdminListByName(map);
    }

    //获取超级管理员列表
    @ResponseBody
    @RequestMapping(value = "/getSuperAdminList", method = RequestMethod.POST)
    RespondBean getSuperAdminList(@RequestBody Map<String,Object> map){
        return adminService.getSuperAdminList(map);
    }
    //获取网点列表
    @ResponseBody
    @RequestMapping(value = "/getNetworkListByLocation", method = RequestMethod.POST)
    RespondBean getLocationNetworkList(@RequestBody Map<String,Object> map){
        return adminService.getLocationNetworkList(map);
    }
    //获取日志列表
    @ResponseBody
    @RequestMapping(value = "/getLogList", method = RequestMethod.POST)
    RespondBean getLogList(@RequestBody Map<String,Object> map){
        return adminService.getLogList(map);
    }
    //添加用户
    @WebLog(type="新增",info = "新增了一个用户")
    @ResponseBody
    @RequestMapping(value = "/insertUser", method = RequestMethod.POST)
    RespondBean insertUserByAdmin(@RequestBody Map<String,Object> map){
        return adminService.insertUserByAdmin(map);
    }
    //修改用户信息
    @WebLog(type="修改",info = "修改了用户信息")
    @ResponseBody
    @RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST)
    RespondBean updateUserInfoByAdmin(@RequestBody Map<String,Object> map){
        return adminService.updateUserInfoByAdmin(map);
    }
    //删除用户
    @WebLog(type="删除",info = "删除了用户")
    @ResponseBody
    @RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
    RespondBean deleteUserByAdmin(@RequestBody Map<String,Object> map){
        return adminService.deleteUserByAdmin(map);
    }
    //添加快递员
    @WebLog(type="新增",info = "新增了一个快递员")
    @ResponseBody
    @RequestMapping(value = "/insertExpressman", method = RequestMethod.POST)
    RespondBean insertExpressmanByAdmin(@RequestBody Map<String,Object> map){
        return adminService.insertExpressmanByAdmin(map);
    }
    //修改快递员信息
    @WebLog(type="修改",info = "修改了快递员信息")
    @ResponseBody
    @RequestMapping(value = "/updateExpressmanInfo", method = RequestMethod.POST)
    RespondBean updateExpressmanInfoByAdmin(@RequestBody Map<String,Object> map){
        return adminService.updateExpressmanInfoByAdmin(map);
    }
    //删除快递员
    @WebLog(type="删除",info = "删除了一个快递员")
    @ResponseBody
    @RequestMapping(value = "/deleteExpressman", method = RequestMethod.POST)
    RespondBean deleteExpressmanByAdmin(@RequestBody Map<String,Object> map){
        return adminService.deleteExpressmanByAdmin(map);
    }

    //添加快递
    @WebLog(type="新增",info = "新增了一个快递件")
    @ResponseBody
    @RequestMapping(value = "/insertExpress", method = RequestMethod.POST)
    RespondBean insertExpressByAdmin(@RequestBody Map<String,Object> map){
        return adminService.insertExpressByAdmin(map);
    }
    //删除快递
    @WebLog(type="删除",info = "删除了一个快递件")
    @ResponseBody
    @RequestMapping(value = "/deleteExpress", method = RequestMethod.POST)
    RespondBean deleteExpress(@RequestBody Map<String,Object> map){
        return adminService.deleteExpress(map);
    }
    //管理员新增网点
    @WebLog(type="新增",info = "新增了一个网点")
    @ResponseBody
    @RequestMapping(value = "/addNetwork",method = RequestMethod.POST)
    RespondBean addNetworkByAdmin(@RequestBody Map<String, Object> map){
        return adminService.addNetworkByAdmin(map);
    }
    //管理员删除网点
    @WebLog(type="删除",info = "删除了网点")
    @ResponseBody
    @RequestMapping(value = "/deleteNetwork",method = RequestMethod.POST)
    RespondBean deleteNetworkByAdmin(@RequestBody Map<String, Object> map){
        return adminService.deleteNetworkByAdmin(map);
    }

    //管理员增加网点管理员
    @WebLog(type="新增",info = "新增了一个网点管理员")
    @ResponseBody
    @RequestMapping(value = "/addNetworkAdmin" , method = RequestMethod.POST)
    RespondBean addNetworkAdminByAdmin(@RequestBody Map<String ,Object> map){
        return adminService.addNetworkAdminByAdmin(map);
    }
    //管理员删除网点管理员
    @WebLog(type="删除",info = "删除了网点管理员")
    @ResponseBody
    @RequestMapping(value = "/deleteNetworkAdmin",method = RequestMethod.POST)
    RespondBean deleNetworkAdmin(@RequestBody Map<String, Object> map){
        return adminService.deleteNetworkAdminByAdmin(map);
    }

    //网点管理员注册
    @ResponseBody
    @RequestMapping(value = "/networkAdminRegister",method = RequestMethod.POST)
    RespondBean AdminRegister(HttpServletRequest request,@RequestBody Map<String, Object> map){
        return userService.AdminRegister(request, map);
    }
    //网点管理员注册
    @ResponseBody
    @RequestMapping(value = "/networkAdminRegister2",method = RequestMethod.POST)
    RespondBean AdminRegister2(@RequestBody Map<String, Object> map){
        return userService.AdminRegister2( map);
    }

    //管理员登录
    @ResponseBody
    @RequestMapping(value = "/adminLogin", method = RequestMethod.POST)
    RespondBean AdminLogin(HttpServletRequest request, @RequestBody Map<String, Object> map){
        return userService.AdminLogin(request, map);
    }
    //修改密码
    @RequestMapping(value = "/networkAdminUpdatePassword", method = RequestMethod.POST)
    @ResponseBody
    RespondBean networkAdminUpdatePassword(HttpServletRequest request,@RequestBody Map<String, Object> map){
        return userService.networkAdminUpdatePassword(request,map);
    }
    @RequestMapping(value = "/networkAdminUpdatePassword2", method = RequestMethod.POST)
    @ResponseBody
    RespondBean networkAdminUpdatePassword2(@RequestBody Map<String, Object> map){
        return userService.networkAdminUpdatePassword2(map);
    }
    @RequestMapping(value = "/getNetworkNidList", method = RequestMethod.POST)
    @ResponseBody
    RespondBean getNetworkNidList(@RequestBody Map<String, Object> map){
        return adminService.getNetworkNidList(map);
    }

    @RequestMapping(value = "/reshapeExpress", method = RequestMethod.POST)
    @ResponseBody
    RespondBean reshapeExpress(@RequestBody Map<String, Object> map){
        return adminService.reshapeExpress(map);
    }
}
