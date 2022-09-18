package com.example.scoresystemv2.mapper;

import com.example.scoresystemv2.bean.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {

    @Select("select * from user where address = #{address}")
    User getfromAddress(@Param("address")String address);

    @Insert("insert into user(address,isAdmin,name,num,age,url) values(#{address},#{isAdmin},#{name},#{num},#{age},#{url})")
    void insert(User user);

    @Update("update user SET `isAdmin`=#{isAdmin},`name`=#{name},`num`=#{num},`age`=#{age},`url`=#{url}  where address = #{address}")
    void updata(User user);

}
