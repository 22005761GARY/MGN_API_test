package com.example.MGN_API_test.model;

import com.example.MGN_API_test.model.entity.CompositePK;
import com.example.MGN_API_test.model.entity.Cashi;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CashiRepository extends JpaRepository<Cashi, CompositePK> {
}
