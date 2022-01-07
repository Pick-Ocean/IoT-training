package com.ali.amqp.dao;

import com.ali.amqp.pojo.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserDao {
    @Insert("insert into user(username,password,phonenumber) values(#{username},#{password},#{phonenumber})")
    void InsertUser(User user);

    @Select("SELECT * FROM user WHERE name = #{name}")
    User findByName(String name);

    @Update("UPDATE user SET username=#{username},password={password},phonenumber={phonenumber} WHERE id=#{id}")
    void update(User user);

    @Delete("DELETE FROM user WHERE id =#{id}")
    void delete(String id);
}
