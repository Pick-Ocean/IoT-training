package com.ali.amqp.controller;

import com.ali.amqp.device.Impl;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class DeviceController {
    String accessKeyId= "LTAI5t8Kp2F9oD38sUEM3zKn";
    String accessKeySecret="4ZaVqIpV6lWNKu73JLYI4r9QsEitVJ";
    @Autowired
    Impl impl;

    //新增设备
    @RequestMapping("/input")
    public String inputDevice(String DeviceName,ModelAndView modelAndView) throws Exception {
        System.out.println(DeviceName);
        impl.input(accessKeyId,accessKeySecret,DeviceName);
        return "forward:/query";

    }

    //删除设备
    @RequestMapping("/delete")
    public String deleteDevice( String DeviceName) throws Exception {
        System.err.println(DeviceName);
        impl.delete(accessKeyId,accessKeySecret,DeviceName);
        return "forward:/query";
    }

    //查询设备
    @RequestMapping("/query")
    public ModelAndView queryDevice(ModelAndView modelAndView)throws Exception {
        List<String> query = impl.query(accessKeyId, accessKeySecret);
        modelAndView.addObject("devicelist",query);
        modelAndView.setViewName("device");
        return modelAndView;
    }
}


