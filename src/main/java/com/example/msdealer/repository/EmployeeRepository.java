package com.example.msdealer.repository;

import com.example.msdealer.entity.DealerEntity;
import com.example.msdealer.entity.EmployeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<EmployeEntity,Long> {
    boolean existsByEmail(String email);
    EmployeEntity findEmployeEntityByEmail(String email);
    List<EmployeEntity> findEmployeEntitiesByDealerEntity(DealerEntity dealerEntity);
}
