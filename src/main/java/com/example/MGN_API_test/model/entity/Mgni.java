package com.example.MGN_API_test.model.entity;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "mgni")
public class Mgni {

    @Id
    @Column(name = "mgni_id")
    private String id;
    @Column(name = "mgni_time")
    private LocalDateTime time;
    @Column(name = "mgni_type")
    private String type;
    @Column(name = "mgni_cm_no")
    private String cm_No;
    @Column(name = "mgni_kac_type")
    private String kac_type;
    @Column(name = "mgni_bank_no")
    private String bank_No;
    @Column(name = "mgni_ccy")
    private String CCY;
    @Column(name = "mgni_pv_type")
    private String pv_type;
    @Column(name = "mgni_bicacc_no")
    private String bicacc_No;
    @Column(name = "mgni_i_type")
    private String i_type;
    @Column(name = "mgni_p_reason")
    private String p_reason;
    @Column(name = "mgni_amt")
    private BigDecimal amt;
    @Column(name = "mgni_ct_name")
    private String ct_name;
    @Column(name = "mgni_ct_tel")
    private String ct_tel;
    @Column(name = "mgni_status")
    private String status;
    @Column(name = "mgni_u_time")
    private LocalDateTime u_time;

    @OneToMany(mappedBy = "cashi_mgni_id", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JoinColumn(name = "cashi_mgni_id")
    private List<Cashi> cashiList;
}
