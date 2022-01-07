package com.ali.amqp.service;

import com.ali.amqp.dao.DataDao;
import com.ali.amqp.pojo.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Dataservice {
    @Autowired
    DataDao dataDao;
    public List<Data> selectall(){
        return  dataDao.selectall();
    }
}
