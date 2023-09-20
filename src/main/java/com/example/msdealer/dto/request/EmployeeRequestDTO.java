package com.example.msdealer.dto.request;

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
public class EmployeeRequestDTO {
    @NotEmpty(message = "The Name should not be empty!")
    private String name;
    @NotEmpty(message = "The Surname should not be empty!")
    private String surname;

    private Roles role;

    @NotEmpty(message = "The email should not be empty!")
    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String email;

    @NotEmpty(message = "The password should not be empty!")
    @Size(min = 8,message = "The password must be at least 8 digits")
    @ValidPassword
    private String password;

}