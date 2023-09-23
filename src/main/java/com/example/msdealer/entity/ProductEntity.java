package com.example.msdealer.entity;


import com.example.msdealer.util.AuditableEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "products")
@Builder
public class ProductEntity  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private String name;
    private String brand;
    private String  description;
    private Integer quantity;
    private Double price;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    ProductCategoryEntity productCategoryEntity;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dealer_id")
    DealerEntity dealerEntity;
    @CreationTimestamp
    @Column(name = "creation_date")
    private LocalDateTime creationDate;
    @UpdateTimestamp
    @Column(name = "update_date")
    private LocalDateTime updateDate;

}
