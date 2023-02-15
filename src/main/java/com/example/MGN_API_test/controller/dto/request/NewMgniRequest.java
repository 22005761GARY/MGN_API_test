package com.example.MGN_API_test.controller.dto.request;

import com.example.MGN_API_test.model.entity.Acc;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewMgniRequest {
//    @NotNull
//    private String id;
//    private LocalDateTime time;
    @NotNull
    private String type;
    @NotNull
    private String cm_No;
    @NotNull
    private List<Acc> accList;
    @NotNull
    private String kac_type;
    @NotNull
    private String bank_No;
    @NotNull
    private String CCY;
    @NotNull
    private String pv_type;
    @NotNull
    @Length(message = "不能操過 {max} 字符", max = 10)
    private String bicacc_No;
    @NotNull
    private String i_type;
    @NotNull
    private String p_reason;
    @NotNull
    private BigDecimal amt;
    @NotBlank(message = "請輸入名稱")
    private String ct_name;
    @NotNull
    private String ct_tel;
    @NotNull
    private String status;
//    private LocalDateTime u_time;
}
