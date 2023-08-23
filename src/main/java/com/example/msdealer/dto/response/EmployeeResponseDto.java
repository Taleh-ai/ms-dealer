package com.example.msdealer.dto.response;

import com.example.msdealer.annotation.ValidPassword;
import com.example.msdealer.dto.enumeration.Roles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

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
