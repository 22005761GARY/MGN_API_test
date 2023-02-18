package com.example.MGN_API_test.config;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.Queue;
import javax.jms.Topic;

@Configuration
public class ActivemqConfig {
    @Value("${requestQueue}")
    private String requestQueue;
    @Value("${responseQueue}")
    private String responseQueue;
    @Value("${messageTopic}")
    private String messageTopic;


    @Bean //將Bean對象交給Spring做管理
    public Queue responseQueue() {
        return new ActiveMQQueue(responseQueue);
    }
//    @Bean
//    public Queue requestQueue() {
//        return new ActiveMQQueue(requestQueue);
//    }
    @Bean
    public Topic messageTopic() {
        return new ActiveMQTopic(messageTopic);
    }
}
