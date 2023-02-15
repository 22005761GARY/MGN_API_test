package com.example.MGN_API_test.service;

import com.example.MGN_API_test.controller.dto.request.NewMgniRequest;
import com.example.MGN_API_test.controller.dto.request.MgniRequest;
import com.example.MGN_API_test.controller.dto.response.GetMgniResponse;
import com.example.MGN_API_test.controller.dto.response.MgniResponse;
import com.example.MGN_API_test.controller.dto.response.StatusResponse;
import com.example.MGN_API_test.model.CashiRepository;
import com.example.MGN_API_test.model.entity.Acc;
import com.example.MGN_API_test.model.entity.Cashi;
import com.example.MGN_API_test.model.entity.Mgni;
import com.example.MGN_API_test.model.MgniRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
public class MgniService {

    @Autowired
    private MgniRepository mgniRepository;
    @Autowired
    private CashiRepository cashiRepository;

    public MgniResponse createData(NewMgniRequest request) throws Exception {

        DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        Mgni mgni = new Mgni();
        mgni.setId("MGI" + date.format(LocalDateTime.now()));
        if (mgniRepository.findById(mgni.getId()).isPresent()) {
            throw new Exception("已有相同ID的資料");
        }
        CreateMgniInfo(mgni, request);
        for (Acc acc : request.getAccList()) {
            Cashi cashi = new Cashi();
            cashi.setCashi_mgni_id(mgni.getId());
            cashi.setCashi_acc_no(acc.getAcc_No());
            cashi.setCashi_ccy(mgni.getCCY());
            cashi.setAmt(acc.getAmt());
//            mgni.getCashiList().add(cashi);
            cashiRepository.save(cashi);
        }
        mgniRepository.save(mgni);

        return new MgniResponse(mgni);
    }

    public GetMgniResponse getAllData() throws Exception {
        checkMgniExist();
        GetMgniResponse response = new GetMgniResponse();
        response.setMgniList(mgniRepository.findAll(Sort.by("id").descending()));
        return response;
    }

    public MgniResponse updateData(String id, NewMgniRequest request) throws Exception {
        if (mgniRepository.findByPK(id) == null) {
            throw new Exception("No data found");
        }
        Mgni mgni = mgniRepository.findByPK(id);
        CreateMgniInfo(mgni, request);
        mgniRepository.save(mgni);
        return new MgniResponse(mgni);
    }

    public StatusResponse deleteDataBySpec(MgniRequest request) throws Exception {
        List<Predicate> predicateList = new ArrayList<>();

        Specification<Mgni> spec = (root, query, criteriaBuilder) -> {
            Path<String> id = root.get("id");//Path物件中使用get接收的參數是在實體模型類(Mgni)中指定的屬性
            Path<String> kac_type = root.get("kac_type");
            Path<String> ccy = root.get("CCY");
            if (request.getId() != null) {
                predicateList.add(criteriaBuilder.equal(id, request.getId()));
            }

            if (request.getKac_type() != null) {
                predicateList.add(criteriaBuilder.equal(kac_type, request.getKac_type()));
            }

            if (request.getCCY() != null) {
                predicateList.add(criteriaBuilder.equal(ccy, request.getCCY()));
            }

            Predicate and = criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
            return and;
        };
        if (mgniRepository.findAll(spec).isEmpty()) {
            throw new Exception("Data not found");
        }
        if (predicateList.isEmpty()) {
            throw new Exception("Please enter spec before delete data");
        }

        List<Mgni> mgniList;
        mgniList = mgniRepository.findAll(spec);
        int n = mgniList.size();
        mgniRepository.deleteAll(mgniList);
        return new StatusResponse("Delete Success!!, " + n + "筆資料已被刪除");
    }

    public List<Mgni> getDataBySpec(MgniRequest request) throws Exception {

        List<Predicate> predicateList = new ArrayList<>();

        //root: 定義查詢子句的型態(String, int , ...)
        Specification<Mgni> specification = (root, query, criteriaBuilder) -> {
            Path<String> id = root.get("id");//Path物件中使用get接收的參數是在實體模型類(Mgni)中指定的屬性
            Path<String> kac_type = root.get("kac_type");
            Path<String> ccy = root.get("CCY");
            if (request.getId() != null) {
                predicateList.add(criteriaBuilder.equal(id, request.getId()));
            }

            if (request.getKac_type() != null) {
                predicateList.add(criteriaBuilder.equal(kac_type, request.getKac_type()));
            }

            if (request.getCCY() != null) {
                predicateList.add(criteriaBuilder.equal(ccy, request.getCCY()));
            }
            Predicate and = criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
            return and;
        };
//        Sort.by("id").descending()
//        Sort sort =Sort.by(Sort.Direction.ASC,"id");
        if (mgniRepository.findAll(specification).isEmpty()) {
            throw new Exception("Data not found");
        }
        return mgniRepository.findAll(specification, Sort.by(Sort.Direction.DESC, "id"));
    }

    public Page<Mgni> getDataBySpecAndPage(MgniRequest request) throws Exception {
        Specification<Mgni> specification = (root, query, criteriaBuilder) -> {
            Path<String> id = root.get("id");
            Path<String> kac_type = root.get("kac_type");
            Path<String> ccy = root.get("CCY");


            List<Predicate> predicateList = new ArrayList<>();

            if (request.getId() != null) {
                predicateList.add(criteriaBuilder.equal(id, request.getId()));
            }

            if (request.getKac_type() != null) {
                predicateList.add(criteriaBuilder.equal(kac_type, request.getKac_type()));
            }

            if (request.getCCY() != null) {
                predicateList.add(criteriaBuilder.equal(ccy, request.getCCY()));
            }
            Predicate and = criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
            return and;
        };
        //指定頁數, 規定每頁大小
        Pageable pageable = PageRequest.of(0, 2, Sort.Direction.DESC, "id");
        if (mgniRepository.findAll(specification).isEmpty()) {
            throw new Exception("Data not found");
        }
        return mgniRepository.findAll(specification, pageable);
    }
    //=========================================================

    public void checkMgniExist() throws Exception {
        if (mgniRepository.findAll().isEmpty()) {
            throw new Exception("沒有符合條件的資料");
        }
    }

    public Mgni CreateMgniInfo(Mgni mgni, NewMgniRequest request) throws Exception {

        mgni.setTime(LocalDateTime.now());
        mgni.setType(request.getType());
        mgni.setCm_No(request.getCm_No());
        mgni.setKac_type(request.getKac_type());
        mgni.setBank_No(request.getBank_No());
        mgni.setCCY(request.getCCY());
        mgni.setPv_type(request.getPv_type());
        mgni.setBicacc_No(request.getBicacc_No());
        mgni.setI_type(request.getI_type());
        mgni.setP_reason(request.getP_reason());
        mgni.setAmt(request.getAmt());
        mgni.setCt_name(request.getCt_name());
        mgni.setCt_tel(request.getCt_tel());
        mgni.setStatus(request.getStatus());
        mgni.setU_time(LocalDateTime.now());

        return mgni;
    }

}
