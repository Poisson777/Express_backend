package com.example.express_end.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.express_end.Respond.RespondBean;
import com.example.express_end.entity.User;
import com.example.express_end.vo.AuthForm;
import com.example.express_end.vo.UpdateForm;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.validation.ObjectError;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

//@Repository
@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Update({"update user set nickname=#{nickname},info=#{info},face=#{face} where uid=#{uid}"})
    Integer updateUserInfo(UpdateForm updateForm);

    @Update({"update user set phone=#{phone},id_card=#{id_card},real_name=#{real_name} where uid=#{uid}"})
    Integer authUser(AuthForm authForm);
    @Update({"update user set location=#{location} where uid=#{uid}"})
    Integer updateLocation(Map<String, Object> map);

    //修改密码
    @Update({"update user set password=#{password} where email=#{email}"})
    Integer updatePassword(Map<String, Object> map);

    //由管理员获取所有用户列表
    @Select({"select * from user order by uid desc limit #{begin},#{page_size}"})
    ArrayList<User> getUserList(Map<String,Object> map);
    @Select({"select count(*) from user"})
    Integer getUserListCount(Map<String,Object> map);

    @Select({"select * from user where real_name like '%${real_name}%' order by uid desc limit #{begin},#{page_size}"})
    ArrayList<User> getUserListByName(Map<String,Object> map);
    @Select({"select count(*) from user where real_name like '%${real_name}%'"})
    Integer getUserListByNameCount(Map<String,Object> map);
    //管理员添加用户
    @Options(useGeneratedKeys = true, keyProperty = "uid", keyColumn = "uid")
    @Insert({"insert into user (username,email,password,nickname,face) values (#{username},#{email},#{password},#{nickname},'1.jpg')"})
    Integer insertUserByAdmin(Map<String,Object> map);
    //管理员修改用户
    @Update({"update user set info=#{info},nickname=#{nickname},face=#{face} where uid=#{uid}"})
    Integer updateUserInfoByAdmin(Map<String,Object> map);
    //管理员删除用户
    @Delete({"delete from user where uid=#{uid}"})
    Integer deleteUserByAdmin(Map<String,Object> map);

    //获取用户id
    @Select({"select uid from user"})
    ArrayList<Integer> getUserIdList();

    @Select({"select * from admin where username=#{username} and password=#{password}"})
    Map<String,Object> findAdmin(Map<String,Object> map);
    @Select({"select count(*) from admin where username=#{username}"})
    Integer findUsername(Map<String,Object> map);

    @Options(useGeneratedKeys = true,keyProperty = "aid",keyColumn = "aid")
    @Insert({"insert into admin (username,email,password,nickname,nid,face) values (#{username},#{email},#{password},#{nickname},#{nid},#{face})"})
    Integer insertNetworkAdminByAdmin(Map<String,Object> map);
    @Update({"update admin set password=#{password} where email=#{email}"})
    Integer networkAdminUpdatePassword(Map<String, Object> map);

    @Select({"select user.uid,user.real_name,user.phone,user.email from user where user.phone like '%${phone}%' limit #{begin},#{page_size}"})
    ArrayList<Map<String,Object>> getUidByPhoneLike(Map<String,Object> map);
    @Select({"select count(*) from user where user.phone like '%${phone}%'"})
    Integer getUidByPhoneLikeCount(Map<String,Object> map);
    @Select({"select user.uid,user.real_name,user.phone,user.email from user where user.phone=#{phone} limit 1"})
    Map<String,Object> getUidByPhone(Map<String,Object> map);

}
