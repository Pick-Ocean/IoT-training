package com.ali.amqp.controller;

import com.ali.amqp.pojo.Data;
import com.ali.amqp.service.Dataservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/data")
public class DataController {
    @Autowired
    Dataservice dataservice;
    @RequestMapping("")
    public ModelAndView selectall(){
        List<Data> dataList = dataservice.selectall();
        ModelAndView model=new ModelAndView();
        model.addObject("datalist",dataList);
        model.setViewName("index");
        return model;
    }
}
