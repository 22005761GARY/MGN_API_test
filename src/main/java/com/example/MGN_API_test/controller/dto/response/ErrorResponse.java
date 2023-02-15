package com.example.MGN_API_test.controller.dto.response;

import com.example.MGN_API_test.controller.TransController;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.MethodArgumentNotValidException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private List<Map<String, String>> fieldError;
    @Value("${log4j}")
    private static final Logger logger = LogManager.getLogger(ErrorResponse.class);
    public ErrorResponse(MethodArgumentNotValidException e){

        this.fieldError = new ArrayList<>();
        e.getBindingResult().getFieldErrors().forEach(fieldError -> {
            Map<String, String> fieldMap = new HashMap<>();
            fieldMap.put("fields", fieldError.getField());
            fieldMap.put("code", fieldError.getCode());
            fieldMap.put("message", fieldError.getDefaultMessage());

            logger.info("fields: " + fieldError.getField());
            logger.error("Error Code: " + fieldError.getCode());

            this.fieldError.add(fieldMap);
        });

    }

    public ErrorResponse(Exception e){
        this.fieldError = new ArrayList<>();
        Map<String, String> fieldMap = new HashMap<>();
        fieldMap.put("Message", e.getMessage());
        fieldError.add(fieldMap);
    }
}
