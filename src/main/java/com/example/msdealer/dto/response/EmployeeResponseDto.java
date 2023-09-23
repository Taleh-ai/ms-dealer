package com.example.msdealer.dto.response;

import com.example.msdealer.dto.enumeration.Roles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeResponseDto {
    private Long id;
    private String name;
    private String surname;
    private Roles role;
    private String email;
    private String password;
    private Long dealerId;
    protected String createdBy;
    protected LocalDateTime createdDate;
    protected String lastModifiedBy;
    protected LocalDateTime lastModifiedDate;
}
