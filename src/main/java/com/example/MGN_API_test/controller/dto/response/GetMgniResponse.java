package com.example.MGN_API_test.controller.dto.response;

import com.example.MGN_API_test.model.entity.Mgni;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "status")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetMgniResponse {
    List<Mgni> mgniList;
}
