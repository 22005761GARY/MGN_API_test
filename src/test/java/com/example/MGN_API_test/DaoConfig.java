package com.example.MGN_API_test;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.persistence.Entity;

@Configuration
//掃描和發現指定package及其subpackage中的Entity定義
@EntityScan(basePackages = {"com.example.MGN_API_test.model.entity"})
//掃描和發現指定package及其sub package中的Repository定義
@EnableJpaRepositories(basePackages = {"com.example.MGN_API_test.model"})
public class DaoConfig {
}
