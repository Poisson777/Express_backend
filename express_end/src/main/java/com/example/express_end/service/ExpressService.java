package com.example.express_end.service;

import com.example.express_end.Respond.RespondBean;
import com.example.express_end.mapper.Express.ExpressMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ExpressService {
    @Autowired
    ExpressMapper expressMapper;
    public RespondBean getExpressByEid(Map<String, Object> map) {
        return RespondBean.success(expressMapper.getExpressByEid(map));
    }

    public RespondBean setState(Map<String, Object> map) {
        expressMapper.setState(map);
        return RespondBean.success();
    }

}
