package com.example.msdealer.dto.request;

import com.example.msdealer.entity.DealerEntity;
import com.example.msdealer.entity.ProductCategoryEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductFilterRequestDto {
    private String name;
    private String brand;
    private Integer minQuantity;
    private Integer maxQuantity;
    private Double minAmount;
    private Double maxAmount;
    private Long categoryId;
    private Long dealerId;
}
