package com.example.MGN_API_test.service;

import com.example.MGN_API_test.config.JmsConfig;
import com.example.MGN_API_test.controller.dto.response.GetMgniResponse;
import com.example.MGN_API_test.model.MgniRepository;
import com.example.MGN_API_test.model.entity.Mgni;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.domain.Sort;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.ObjectMessage;
import java.util.List;

@Service
public class SendService {

    private JmsTemplate jmsTemplate;

    @Autowired
    public SendService(JmsTemplate jmsTemplate){
        this.jmsTemplate = jmsTemplate;
    }

    @Autowired
    private MgniRepository mgniRepository;
    public void sendMessage(String message){
        jmsTemplate.convertAndSend("Test",message);
    }

    public void getAllData() throws Exception {
        checkMgniExist();
        List<Mgni> mgniList = mgniRepository.findAll(Sort.by("id").descending());
        jmsTemplate.convertAndSend("Test", mgniList.toString());
    }

    public void checkMgniExist() throws Exception {
        if (mgniRepository.findAll().isEmpty()) {
            throw new Exception("沒有符合條件的資料");
        }
    }
}
