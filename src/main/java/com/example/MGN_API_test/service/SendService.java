package com.example.MGN_API_test.service;

import com.example.MGN_API_test.config.JmsConfig;
import com.example.MGN_API_test.controller.dto.request.MgniRequest;
import com.example.MGN_API_test.controller.dto.response.GetMgniResponse;
import com.example.MGN_API_test.model.MgniRepository;
import com.example.MGN_API_test.model.entity.Mgni;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.ObjectMessage;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
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
    public void sendRequest(String request){
        jmsTemplate.convertAndSend("request.queue",request);
    }

    public void getAllData() throws Exception {
        checkMgniExist();
        ObjectMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
        List<Mgni> mgniList = mgniRepository.findAll(Sort.by("id").descending());
        String response = objectMapper.writeValueAsString(mgniList);
        jmsTemplate.convertAndSend("Test", response);
    }


    public void checkMgniExist() throws Exception {
        if (mgniRepository.findAll().isEmpty()) {
            throw new Exception("沒有符合條件的資料");
        }
    }
}
