package com.example.msdealer.dto.request;


import com.example.msdealer.entity.DealerEntity;
import com.example.msdealer.entity.ProductCategoryEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequestDto {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Brand is required")
    private String brand;

    @NotBlank(message = "Description is required")
    private String description;

    @Positive(message = "Quantity must be a positive number")
    private int quantity;

    @Positive(message = "Amount must be a positive number")
    private double amount;

    @NotNull(message = "Category ID is required")
    private Long category_id;
}
