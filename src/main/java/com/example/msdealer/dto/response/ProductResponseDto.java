package com.example.msdealer.dto.response;


import com.example.msdealer.entity.DealerEntity;
import com.example.msdealer.entity.ProductCategoryEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponseDto {
    private Long id;
    private String name;
    private String brand;
    private String description;
    private int quantity;
    private double amount;
    private Long category_id;
    private Long dealer_id;
}
