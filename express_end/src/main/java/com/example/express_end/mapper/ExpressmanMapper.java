package com.example.express_end.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.express_end.entity.Express;
import com.example.express_end.entity.Expressman;
import com.example.express_end.entity.User;
import com.example.express_end.vo.AuthForm;
import com.example.express_end.vo.EAuthForm;
import com.example.express_end.vo.EUpdateForm;
import com.example.express_end.vo.UpdateForm;
import io.netty.handler.codec.serialization.ObjectEncoder;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;
import java.util.Map;

@Mapper
public interface ExpressmanMapper extends BaseMapper<Expressman> {
    @Update({"update expressman set nickname=#{nickname},info=#{info},face=#{face} where mid=#{mid}"})
    Integer updateExpressmanInfo(EUpdateForm updateForm);

    @Update({"update expressman set phone=#{phone},id_card=#{id_card},real_name=#{real_name} where mid=#{mid}"})
    Integer authExpressman(EAuthForm authForm);

    @Update({"update expressman set location=#{location} where mid=#{mid}"})
    Integer updateLocation(Map<String, Object> map);

    //修改密码
    @Update({"update expressman set password=#{password} where email=#{email}"})
    Integer updatePassword(Map<String, Object> map);

    //由管理员获取所有快递员列表
    @Select({"select * from expressman order by mid desc limit #{begin},#{page_size}"})
    ArrayList<Expressman> getUserList(Map<String,Object> map);

    @Select({"select count(*) from expressman"})
    Integer getUserListCount(Map<String,Object> map);
    @Select({"select * from expressman where real_name like '%${real_name}%' order by mid desc limit #{begin},#{page_size}"})
    ArrayList<Expressman> getUserListByName(Map<String,Object> map);

    @Select({"select count(*) from expressman where real_name like '%${real_name}%'"})
    Integer getUserListByNameCount(Map<String,Object> map);

    //管理员添加用户
    @Options(useGeneratedKeys = true, keyProperty = "mid", keyColumn = "mid")
    @Insert({"insert into expressman (username,email,password,nickname,face) values (#{username},#{email},#{password},#{nickname},'1.jpg')"})
    Integer insertExpressman(Map<String,Object> map);
    //管理员修改用户
    @Update({"update expressman set info=#{info},nickname=#{nickname},face=#{face} where mid=#{mid}"})
    Integer updateExpressmanInfoByAdmin(Map<String,Object> map);
    //管理员删除用户
    @Delete({"delete from expressman where mid=#{mid}"})
    Integer deleteExpressmanByAdmin(Map<String,Object> map);

    @Select({"select mid from expressman"})
    ArrayList<Integer> getEidList();

    @Select({"select * from expressman where mid=#{mid}"})
    Expressman selectExpressmanById(Map<String,Object> map);


}
