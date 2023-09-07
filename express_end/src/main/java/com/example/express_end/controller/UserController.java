package com.example.express_end.controller;

import com.example.express_end.Respond.RespondBean;
import com.example.express_end.service.RequestService;
import com.example.express_end.service.UserService;
import com.example.express_end.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.ObjectError;
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
@RequestMapping("/user")
public class UserController {
    @Autowired()
    UserService userService;
    @Autowired
    RequestService requestService;
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public RespondBean login(HttpServletRequest request,@RequestBody @Valid LoginForm loginForm){
        return userService.login(request,loginForm);
    }

    @ResponseBody
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    RespondBean register(HttpServletRequest request,@RequestBody @Valid RegisterForm registerForm){
        return userService.register(request,registerForm);
    }

    @ResponseBody
    @RequestMapping(value = "/register2", method = RequestMethod.POST)
    RespondBean register2(@RequestBody @Valid RegisterForm registerForm){
        return userService.register2(registerForm);
    }


    @RequestMapping(value = "/updateInfo", method = RequestMethod.POST)
    @ResponseBody
    RespondBean updateUserInfo(@RequestBody @Valid UpdateForm updateForm){
        return userService.updateUserInfo(updateForm);
    }
    @RequestMapping(value = "/updatePassword2", method = RequestMethod.POST)
    @ResponseBody
    RespondBean updatePassword2(@RequestBody Map<String,Object> map){
        return userService.updatePassword2(map);
    }
    @RequestMapping(value = "/getUserByUid", method = RequestMethod.POST)
    @ResponseBody
    RespondBean getUserById(@RequestBody Map<String,Object> map){
        return userService.getUserById(map);
    }

    @RequestMapping(value = "/authUser", method = RequestMethod.POST)
    @ResponseBody
    RespondBean authUser(@RequestBody @Valid AuthForm authForm){
        return userService.authUser(authForm);
    }

    @RequestMapping(value = "/updateLocation", method = RequestMethod.POST)
    @ResponseBody
    RespondBean updateLocation(@RequestBody Map<String, Object> map){
        return userService.updateLocation(map);
    }

    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    @ResponseBody
    RespondBean updatePassword(HttpServletRequest request,@RequestBody Map<String, Object> map){
        return userService.updatePassword(request,map);
    }

    //确认收货
    @RequestMapping(value = "/confirmReceipt", method = RequestMethod.POST)
    @ResponseBody
    RespondBean confirmReceipt(@RequestBody Map<String, Object> map){
        return userService.confirmReceipt(map);
    }
    @RequestMapping(value = "/getSendExpressList", method = RequestMethod.POST)
    @ResponseBody
    RespondBean getSendExpressListByUid(@RequestBody  Map<String, Object> map){
        return userService.getSendExpressListByUid(map);
    }
    //获取某用户的收到的或未收获的快递列表
    @RequestMapping(value = "/getReceiptExpressList", method = RequestMethod.POST)
    @ResponseBody
    RespondBean getReceiptExpressListByUid(@RequestBody Map<String, Object> map){
        return userService.getReceiptExpressListByUid(map);
    }

    //redis
    //用户点击上门取件
    @ResponseBody
    @RequestMapping(value = "/addUserRequest", method = RequestMethod.POST)
    RespondBean addUserRequest(@RequestBody Map<String,Object> map){
        return requestService.adduserRequest(map);
    }

    //用户获取自己的上门取件请求
    @ResponseBody
    @RequestMapping(value = "/getUserRequest", method = RequestMethod.POST)
    RespondBean getUserRequest(@RequestBody Map<String ,Object>map){
        return requestService.getRequest(map);
    }

    //用户取消上门取件
    @ResponseBody
    @RequestMapping(value = "/cancelUserRequest",method = RequestMethod.POST)
    RespondBean cancelUserRequest(@RequestBody Map<String ,Object>map){
        return requestService.canceluserRequest(map);
    }

    //用户取消上门取件
    @ResponseBody
    @RequestMapping(value = "/getUidByPhone",method = RequestMethod.POST)
    RespondBean getUidByPhone(@RequestBody Map<String ,Object>map){
        return userService.getUidByPhone(map);
    }
    @ResponseBody
    @RequestMapping(value = "/getUidByPhoneLike",method = RequestMethod.POST)
    RespondBean getUidByPhoneLike(@RequestBody Map<String ,Object>map){
        return userService.getUidByPhoneLike(map);
    }

}
