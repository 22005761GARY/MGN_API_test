package com.example.MGN_API_test.controller;

import com.example.MGN_API_test.controller.dto.request.NewMgniRequest;
import com.example.MGN_API_test.controller.dto.request.MgniRequest;
import com.example.MGN_API_test.controller.dto.response.MgniResponse;
import com.example.MGN_API_test.controller.dto.response.GetMgniResponse;
import com.example.MGN_API_test.controller.dto.response.StatusResponse;
import com.example.MGN_API_test.model.entity.Mgni;
import com.example.MGN_API_test.service.MgniService;
import com.example.MGN_API_test.service.SendService;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value ="/api/v1/mgni")

public class TransController {

    @Autowired
    private MgniService mgniService;
    @Autowired
    private SendService sendService;

                              //指定回傳類型
    @GetMapping(value = "/get", produces = {"application/xml", "application/json"})
    public GetMgniResponse getAllData() throws Exception{
        return mgniService.getAllData();
    }

    @PostMapping(value = "/create")
    public MgniResponse createData(@RequestBody @Valid NewMgniRequest request) throws Exception{
        MgniResponse response = this.mgniService.createData(request);
        return response;
    }

    @PostMapping(value = "/update/{id}")
    public MgniResponse updateData(@PathVariable @Length String id, @RequestBody @Valid NewMgniRequest request) throws Exception {
        MgniResponse response = this.mgniService.updateData(id, request);
        return response;
    }

    @GetMapping("/spec")
    public List<Mgni> getDataBySpec(@RequestBody @Valid MgniRequest request) throws Exception{
        return this.mgniService.getDataBySpec(request);
    }

    @GetMapping("/specAndpage")
    public Page<Mgni> getDataBySpecAndPage(@RequestBody @Valid MgniRequest request) throws Exception{
        return this.mgniService.getDataBySpecAndPage(request);
    }
    @DeleteMapping("/DeleteBySpec")
    public StatusResponse deleteDataBySpec(@RequestBody MgniRequest request) throws Exception{
        return this.mgniService.deleteDataBySpec(request);
    }

    @PostMapping("/send")
    public String sendMessage(@RequestBody String message){
        this.sendService.sendMessage(message);
        return message;
    }
    @GetMapping("/send/AllMgni")
    public String sendAllMgni() throws Exception {
        this.sendService.getAllData();
        return "Message send.";
    }
}
