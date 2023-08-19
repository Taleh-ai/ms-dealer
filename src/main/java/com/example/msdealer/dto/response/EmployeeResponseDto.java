package com.example.msdealer.dto.response;

import com.example.msdealer.annotation.ValidPassword;
import com.example.msdealer.dto.enumeration.Roles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

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
}
