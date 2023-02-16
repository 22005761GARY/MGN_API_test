package com.example.MGN_API_test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class SendResponseService {

    private JmsTemplate jmsTemplate;

    @Autowired
    public SendResponseService(JmsTemplate jmsTemplate){
        this.jmsTemplate = jmsTemplate;
    }

    public void sendResponse(String response){
        jmsTemplate.convertAndSend("response.queue",response);
    }

//    public void getAllData() throws Exception {
//        checkMgniExist();
//        ObjectMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
//        List<Mgni> mgniList = mgniRepository.findAll(Sort.by("id").descending());
//        String response = objectMapper.writeValueAsString(mgniList);
//        jmsTemplate.convertAndSend("Test", response);
//    }
//
//
//    public void checkMgniExist() throws Exception {
//        if (mgniRepository.findAll().isEmpty()) {
//            throw new Exception("沒有符合條件的資料");
//        }
//    }
}
