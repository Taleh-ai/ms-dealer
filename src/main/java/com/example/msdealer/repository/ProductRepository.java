package com.example.msdealer.repository;

import com.example.msdealer.dto.request.ProductFilterRequestDto;
import com.example.msdealer.entity.DealerEntity;
import com.example.msdealer.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestBody;

import java.awt.print.Pageable;
import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity,Long> {

    List<ProductEntity> findAllByDealerEntity(DealerEntity dealerEntity);



}

