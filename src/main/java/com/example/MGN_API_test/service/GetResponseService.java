package com.example.MGN_API_test.service;

import com.example.MGN_API_test.controller.dto.request.MgniRequest;
import com.example.MGN_API_test.controller.dto.request.NewMgniRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

//import java.util.logging.LogManager;
//import java.util.logging.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class GetResponseService {

    @Autowired
    private MgniService mgniService;
    private static ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = LogManager.getLogger(GetResponseService.class);

    private JmsTemplate jmsTemplate;

    @Autowired
    public GetResponseService(JmsTemplate jmsTemplate){
        this.jmsTemplate = jmsTemplate;
    }
    public String getResponse(String request) throws Exception {
        JSONObject jsonObject = new JSONObject(request);
        jmsTemplate.convertAndSend("request.queue", request);
        String requestType = jsonObject.getString("requestType");
        JSONObject requestBodyJson = jsonObject.getJSONObject("requestBody");
        String requestBody = JSONObject.valueToString(requestBodyJson);
//        logger.info(requestBody);
        String response = "";
        switch (requestType){
            case "Select":
                response = mgniService.getDataBySpec(objectMapper.readValue(requestBody, MgniRequest.class)).toString();
                jmsTemplate.convertAndSend("response.queue", response);
                logger.info(response);
                break;
            case "Create":
                response = mgniService.createData(objectMapper.readValue(requestBody, NewMgniRequest.class)).toString();
                jmsTemplate.convertAndSend("response.queue", response);
                logger.info(response);
                break;
            case "Delete":
                response = mgniService.deleteDataBySpec(objectMapper.readValue(requestBody, MgniRequest.class)).toString();
                jmsTemplate.convertAndSend("response.queue", response);
                logger.info(response);
                break;
            case "Update":
                String key = jsonObject.getString("key");
                response = mgniService.updateData(key, objectMapper.readValue(requestBody, NewMgniRequest.class)).toString();
                jmsTemplate.convertAndSend("response.queue", response);
                logger.info(response);
                break;
            default:
                response = "Please Enter a CRUD request!!";
                logger.error("Bad Request");
                break;
        }

        return response;
    }

//    private static String toString(Object object) throws Exception {
//        objectMapper.findAndRegisterModules();
//        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
//    }

}
