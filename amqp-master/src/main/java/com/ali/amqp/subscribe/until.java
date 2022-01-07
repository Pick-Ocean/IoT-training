package com.ali.amqp.subscribe;

import com.ali.amqp.dao.DataDao;
import com.ali.amqp.pojo.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 曾瑞楷
 * @Date: 2022/01/04/21:25
 * @Description:
 */
@Component
public class until {

    @Autowired
    DataDao dataDao;
    private static until until;

    public static void insert(Data data) {
        until.dataDao.adddata(data);
    }

    @PostConstruct
    public void init() {
        until = this;
        until.dataDao = this.dataDao;
    }
}
