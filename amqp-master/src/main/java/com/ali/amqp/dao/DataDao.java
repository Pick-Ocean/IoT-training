package com.ali.amqp.dao;

import com.ali.amqp.pojo.Data;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface DataDao {
    @Insert("insert into data(type,value,time) values(#{type},#{value},#{time})")
    void adddata(Data data);
    @Select("select * from data")
    List<Data> selectall();
}
