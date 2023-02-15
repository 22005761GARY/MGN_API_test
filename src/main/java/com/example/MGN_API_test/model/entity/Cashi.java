package com.example.MGN_API_test.model.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "cashi")
@IdClass(CompositePK.class)
public class Cashi {
    @Id
    @Column(name = "cashi_mgni_id")
    private String cashi_mgni_id;
    @Id
    @Column(name = "cashi_acc_no")
    private String cashi_acc_no;
    @Id
    @Column(name = "cashi_ccy")
    private String cashi_ccy;
    @Column(name = "cashi_amt")
    private BigDecimal amt;

}
