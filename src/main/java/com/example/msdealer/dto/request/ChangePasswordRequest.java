package com.example.msdealer.dto.request;

import com.example.msdealer.annotation.ValidPassword;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangePasswordRequest {


    private String username;
    @NotEmpty(message = "The password should not be empty!")
    @Size(min = 8,message = "The password must be at least 8 digits")
    @ValidPassword
    private String oldPassword;

    @NotEmpty(message = "The password should not be empty!")
    @Size(min = 8,message = "The password must be at least 8 digits")
    @ValidPassword
    private String newPassword;
}