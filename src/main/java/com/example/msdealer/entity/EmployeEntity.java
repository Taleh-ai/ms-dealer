package com.example.msdealer.entity;

import com.example.msdealer.dto.enumeration.Roles;
import com.example.msdealer.util.AuditableEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "employees")
@Builder
public class EmployeEntity  {
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
    @JsonIgnore
    DealerEntity dealerEntity;
    @CreationTimestamp
    @Column(name = "creation_date")
    private LocalDateTime creationDate;
    @UpdateTimestamp
    @Column(name = "update_date")
    private LocalDateTime updateDate;

}
