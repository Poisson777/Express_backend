package com.example.express_end.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.express_end.Respond.RespondBean;
import com.example.express_end.Respond.RespondEnum;
import com.example.express_end.entity.Expressman;
import com.example.express_end.entity.Network;
import com.example.express_end.entity.User;
import com.example.express_end.mapper.ChinaMapper;
import com.example.express_end.mapper.FaceMapper;
import com.example.express_end.mapper.GlobalMapper;
import com.example.express_end.utils.SendEmail;
import com.example.express_end.vo.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class GlobalService {
    @Autowired
    GlobalMapper globalMapper;
    @Autowired
    ChinaMapper chinaMapper;
    @Autowired
    FaceMapper faceMapper;
    //注册发送邮箱
    public RespondBean sendEmail(HttpServletRequest request, Email email) {
        if(email.getRole().equals("user")){
            if(0!=globalMapper.isExistUserEmail(email.getEmail())){//邮箱是否被使用过
                return RespondBean.error(RespondEnum.EMAIL_ERROR);
            }
        }
        else if(email.getRole().equals("expressman")){
            if(0!=globalMapper.isExistExpressEmail(email.getEmail())){//邮箱是否被使用过
                return RespondBean.error(RespondEnum.EMAIL_ERROR);
            }
        }
        else if(email.getRole().equals("admin")) {
            if(0!=globalMapper.isExistNetworkEmail(email.getEmail())){//邮箱是否被使用过
                return RespondBean.error(RespondEnum.EMAIL_ERROR);
            }
        }
        else {
            return RespondBean.error(RespondEnum.ROLE_ERROR);
        }
        SendEmail sendEmail=new SendEmail();
        Random random=new Random();
        StringBuilder code= new StringBuilder(String.valueOf(random.nextInt(999999)));
        int temp_length=6-code.length();
        if(code.length()!=6)
        {
            for (int i=0;i<temp_length;i++) {
                code.insert(0, "0");
            }
        }
        request.getSession().setAttribute("code", code.toString());
        request.getSession().setAttribute("email",email);//将数据存进session中
        request.getSession().setMaxInactiveInterval(300);
        sendEmail.sendMail(email.getEmail(),"<div style=\"width:100%;text-align:center\"><span style=\"color:blue;font-size:50px;\">"+code+"</span></div>");
        return RespondBean.success(code.toString());
    }
    public RespondBean sendEmailForgetPassword(HttpServletRequest request, Map<String,Object>  map) {

        SendEmail sendEmail=new SendEmail();
        Random random=new Random();
        StringBuilder code= new StringBuilder(String.valueOf(random.nextInt(999999)));
        int temp_length=6-code.length();
        String email=map.get("email").toString();
        if(code.length()!=6)
        {
            for (int i=0;i<temp_length;i++) {
                code.insert(0, "0");
            }
        }
        request.getSession().setAttribute("code2", code.toString());
        request.getSession().setAttribute("email2",email);//将数据存进session中
        request.getSession().setMaxInactiveInterval(300);
        sendEmail.sendMail(email,"<div style=\"width:100%;text-align:center\"><span style=\"color:blue;font-size:50px;\">"+code+"</span></div>");
        return RespondBean.success(code.toString());
    }
    public RespondBean getProvinceList() {
        return RespondBean.success(chinaMapper.getListByPid(0));
    }

    public RespondBean getCityList(Map<String,Object> map) {
        Integer pid=(Integer)map.get("pid");
        return RespondBean.success(chinaMapper.getListByPid(pid));
    }

    public RespondBean getAreaList(Map<String,Object> map) {
        Integer pid=(Integer)map.get("pid");
        return RespondBean.success(chinaMapper.getListByPid(pid));
    }

    public RespondBean getFaceList(Map<String, Object> map) {
        map.putIfAbsent("page_num", 1);
        map.putIfAbsent("page_size", 20);
        map.put("begin",((int)map.get("page_num")-1)*(int)map.get("page_size"));
        Map<String,Object> map1=new HashMap<>();
        map1.put("count",faceMapper.geFaceListCount(map));
        map1.put("data",faceMapper.geFaceList(map));
        return RespondBean.success(map1);
    }
}
