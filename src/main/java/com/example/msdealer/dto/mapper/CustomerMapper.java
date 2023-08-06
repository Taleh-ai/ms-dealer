package com.example.msdealer.dto.mapper;

import com.example.mscustomers.dto.request.CustomerRequestDto;
import com.example.mscustomers.dto.response.CustomerResponseDto;
import com.example.mscustomers.entity.CustomerEntity;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Mapper
@Component
@RequiredArgsConstructor
public class CustomerMapper {
    private final PasswordEncoder passwordEncoder;

    public CustomerEntity fromDto(CustomerRequestDto dto){
       return CustomerEntity.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .gender(dto.getGender())
                .phoneNumber(dto.getPhone())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role("Customer")
                .build();
    }

    public CustomerResponseDto toDto(CustomerEntity entity){
        return CustomerResponseDto.builder()
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .gender(String.valueOf(entity.getGender()))
                .phoneNumber(entity.getPhoneNumber())
                .email(entity.getEmail())
                .CustomerId(entity.getId())
                .build();
    }
}
