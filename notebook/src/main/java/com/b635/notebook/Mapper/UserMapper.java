package com.b635.notebook.Mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
  @Select("select userName from user where userName=#{userName}")
     String selectUserName(String userName);

  @Select("select userPassword from user where userName=#{userName}")
     String selectUserPassword(String userName);

  @Insert("insert into user(userName,userPassword)values(#{userName},#{userPassword})")
     void addUser(String userName,String userPassword);
}
