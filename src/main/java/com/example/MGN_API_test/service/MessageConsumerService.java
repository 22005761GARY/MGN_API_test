package com.example.MGN_API_test.service;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumerService {

    @JmsListener(destination = "Test")
    public void listener(String message){
        System.out.println("The message send from QUEUE - Test : " + message);
    }
}
