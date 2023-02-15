package com.example.MGN_API_test.config;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.Queue;

@Configuration
public class ActivemqConfig {
    @Value("${requestQueue}")
    private String requestQueue;
    @Value("${responseQueue}")
    private String responseQueue;

    @Bean
    public Queue responseQueue() {
        return new ActiveMQQueue(responseQueue);
    }
    @Bean
    public Queue requestQueue() {
        return new ActiveMQQueue(requestQueue);
    }
}
