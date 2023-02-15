package com.example.MGN_API_test.model;

import com.example.MGN_API_test.model.entity.CompositePK;
import com.example.MGN_API_test.model.entity.Mgni;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface MgniRepository extends JpaRepository<Mgni, String>, JpaSpecificationExecutor<Mgni> {

    @Query(value = "Select * From mgni where mgni_id = ?1", nativeQuery = true)
    Mgni findByPK(String id);
}
