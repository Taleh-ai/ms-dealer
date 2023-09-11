package com.example.msdealer.mapper;

import com.example.msdealer.dto.enumeration.Roles;
import com.example.msdealer.dto.request.DealerRequestDTO;
import com.example.msdealer.dto.response.DealerResponseDto;
import com.example.msdealer.entity.DealerEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Mapper
@Component
@RequiredArgsConstructor
public class DealerMapper {
    private final PasswordEncoder passwordEncoder;
    public DealerEntity fromDto(DealerRequestDTO dto){
        return DealerEntity.builder()
                .companyName(dto.getCompanyName())
                .contactNumber(dto.getContactNumber())
                .category(dto.getCategory())
                .location(dto.getLocation())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(Roles.ADMIN)
                .build();
    }

    public DealerResponseDto toDto(DealerEntity entity){
        return DealerResponseDto.builder()
                .companyName(entity.getCompanyName())
                .id(entity.getId())
                .contactNumber(entity.getContactNumber())
                .category(entity.getCategory())
                .location(entity.getLocation())
                .email(entity.getEmail())
                .role(entity.getRole())
                .creationDate(entity.getCreationDate())
                .updateDate(entity.getUpdateDate())
                .build();
    }
}
