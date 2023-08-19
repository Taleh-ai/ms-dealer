package com.example.msdealer.dto.mapper;

import com.example.msdealer.dto.enumeration.Roles;
import com.example.msdealer.dto.request.DealerRequestDTO;
import com.example.msdealer.dto.request.EmployeeRequestDTO;
import com.example.msdealer.dto.response.DealerResponseDto;
import com.example.msdealer.dto.response.EmployeeResponseDto;
import com.example.msdealer.entity.DealerEntity;
import com.example.msdealer.entity.EmployeEntity;
import lombok.RequiredArgsConstructor;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Mapper
@RequiredArgsConstructor
public class EmployeeMapper {
    private final PasswordEncoder passwordEncoder;
    public EmployeEntity fromDto(EmployeeRequestDTO dto){
        return EmployeEntity.builder()
                .surname(dto.getSurname())
                .name(dto.getName())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(dto.getRole())
                .build();
    }

    public EmployeeResponseDto toDto(EmployeEntity entity){
        return EmployeeResponseDto.builder()
                .surname(entity.getSurname())
                .name(entity.getName())
                .email(entity.getEmail())
                .role(entity.getRole())
                .dealerId(entity.getDealerEntity().getId())
                .id(entity.getId())
                .build();
    }
}
