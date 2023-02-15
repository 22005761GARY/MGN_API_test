package com.example.MGN_API_test.service;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumerService {

    @JmsListener(destination = "request.queue")
    public void requestListener(String request){
        System.out.println("The message send from QUEUE - request.queue : " + request);
    }

    @JmsListener(destination = "response.queue")
    public void responseLister(String response){
        System.out.println("The message send from QUEUE - response.queue : " + response);

    }
}
