package com.example.msdealer.entity;

import com.example.msdealer.dto.enumeration.Roles;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "employies")
@Builder
public class EmployeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    String surname;
    @Enumerated(EnumType.STRING)
    Roles role;
    String email;
    String password;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dealer_id" ,referencedColumnName = "id")
    DealerEntity dealerEntity;
}
