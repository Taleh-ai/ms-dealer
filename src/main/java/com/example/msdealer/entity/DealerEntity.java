package com.example.msdealer.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "dealers")
public class DealerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String company_name;
    private String category;
    private String name;
    private String location;
    private String contactNumber;
    private String email;
    private String password;
    private String role;
    @OneToMany
    List<UserEntity> userEntity;
}
