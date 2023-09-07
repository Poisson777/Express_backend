package com.example.express_end.controller;


import com.example.express_end.Respond.RespondBean;
import com.example.express_end.service.GlobalService;
import com.example.express_end.vo.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

@Validated
@Controller
@RequestMapping("/global")
public class GlobalController {
    @Autowired
    GlobalService globalService;
    @RequestMapping("/sendEmail")
    @ResponseBody
    public RespondBean sendEmail(HttpServletRequest request, @RequestBody @Valid Email email){
        return globalService.sendEmail(request,email);
    }
    @RequestMapping("/forgetPassword/sendEmail")
    @ResponseBody
    public RespondBean forgetPasswordSendEmail(HttpServletRequest request, @RequestBody Map<String,Object> map){
        return globalService.sendEmailForgetPassword(request,map);
    }
    @RequestMapping("/getProvinceList")
    @ResponseBody
    public RespondBean getProvinceList(){
        return globalService.getProvinceList();
    }
    @RequestMapping("/getCityList")
    @ResponseBody
    public RespondBean getCityList(@RequestBody Map<String, Object> map){
        return globalService.getCityList(map);
    }
    @RequestMapping("/getAreaList")
    @ResponseBody
    public RespondBean getAreaList( @RequestBody Map<String, Object> map){
        return globalService.getAreaList(map);
    }
    @RequestMapping("/getFaceList")
    @ResponseBody
    public RespondBean getFaceList( @RequestBody Map<String, Object> map){
        return globalService.getFaceList(map);
    }
}
