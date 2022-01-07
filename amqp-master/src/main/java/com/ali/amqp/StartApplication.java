package com.ali.amqp;


import com.ali.amqp.subscribe.ampq;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @author beauhou
 * @version 1.0
 * @date 2020/8/9 0009 19:27
 */
@SpringBootApplication
public class StartApplication {
    public static void main(String[] args) {
        SpringApplication.run(StartApplication.class,args);

        try {
            ampq.start();
//            AmqpJavaClient.subscribe();
        } catch (Exception e) {

            e.printStackTrace();
            System.err.println("阿里云订阅初始化失败");
        }
    }
}
