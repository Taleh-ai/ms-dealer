package com.example.msdealer.dto.response;

import com.example.msdealer.annotation.ValidPassword;
import com.example.msdealer.dto.enumeration.Roles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DealerResponseDto {
    private Long id;
    private String companyName;
    private String category;
    private String name;
    private String location;
    private String contactNumber;
    private String email;
    private String password;
    private Roles role;
    private Date creationDate;
    private Date updateDate;
}
