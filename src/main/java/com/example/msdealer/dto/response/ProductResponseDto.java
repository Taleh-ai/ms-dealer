package com.example.msdealer.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponseDto {
    private Long id;
    private String name;
    private String brand;
    private String description;
    private Double amount;
    private String category;
    private String  dealer;
    private Long dealerId;
    private String stockSituation;
    private LocalDateTime creationDate;
    private LocalDateTime updateDate;
}
