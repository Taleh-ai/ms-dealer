package com.example.msdealer.repository;

import com.example.msdealer.entity.EmployeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<EmployeEntity,Long> {
    boolean existsByEmail(String email);
    EmployeEntity findEmployeEntityByEmail(String email);
}
