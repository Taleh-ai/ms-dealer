package com.example.msdealer.repository;

import com.example.msdealer.entity.DealerEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DealerRepository extends JpaRepository<DealerEntity,Long> {

    DealerEntity findDealerEntityByEmail(String email);
    boolean existsByEmail(String email);
}
