package com.example.MGN_API_test.controller.dto.request;

import com.example.MGN_API_test.model.entity.Acc;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MgniRequest {
    private String id;
    //    private LocalDateTime time;
    private String type;
    private String cm_No;
    private List<Acc> accList;
    private String kac_type;
    private String bank_No;
    private String CCY;
    private String pv_type;
    private String bicacc_No;
    private String i_type;
    private String p_reason;
    private BigDecimal amt;
    private String ct_name;
    private String ct_tel;
    private String status;
}
