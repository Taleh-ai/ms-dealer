package com.example.msdealer.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "products")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private String name;
    private String brand;
    private String  description;
    private int quantity;
    private double amount;
    @ManyToOne
    @JoinColumn(name = "category_id")
    ProductCategoryEntity productCategoryEntity;
    @ManyToOne
    @JoinColumn(name = "dealer_id")
    DealerEntity dealerEntity;

}
