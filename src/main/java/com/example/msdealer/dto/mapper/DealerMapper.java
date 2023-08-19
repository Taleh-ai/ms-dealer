package com.example.msdealer.dto.mapper;

import com.example.msdealer.dto.enumeration.Roles;
import com.example.msdealer.dto.request.DealerRequestDTO;
import com.example.msdealer.dto.response.DealerResponseDto;
import com.example.msdealer.entity.DealerEntity;
import lombok.RequiredArgsConstructor;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Mapper
@Component
@RequiredArgsConstructor
public class DealerMapper {
    private final PasswordEncoder passwordEncoder;
    public DealerEntity fromDto(DealerRequestDTO dto){
        return DealerEntity.builder()
                .company_name(dto.getCompanyName())
                .name(dto.getName())
                .contactNumber(dto.getContactNumber())
                .category(dto.getCategory())
                .location(dto.getLocation())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(Roles.valueOf("Admin"))
                .build();
    }

    public DealerResponseDto toDto(DealerEntity entity){
        return DealerResponseDto.builder()
                .companyName(entity.getCompany_name())
                .id(entity.getId())
                .name(entity.getName())
                .contactNumber(entity.getContactNumber())
                .category(entity.getCategory())
                .location(entity.getLocation())
                .email(entity.getEmail())
                .role(entity.getRole())
                .build();
    }
}
