package com.example.msdealer.mapper;

import com.example.msdealer.dto.request.EmployeeRequestDTO;
import com.example.msdealer.dto.response.EmployeeResponseDto;
import com.example.msdealer.entity.EmployeEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
@Component
public class EmployeeMapper {
    public EmployeEntity fromDto(EmployeeRequestDTO employeeRequestDTO) {
     return    EmployeEntity.builder()
                .name(employeeRequestDTO.getName())
                .surname(employeeRequestDTO.getSurname())
                .role(employeeRequestDTO.getRole())
                .email(employeeRequestDTO.getEmail())
                .build();
    }

    public EmployeeResponseDto toDto(EmployeEntity entity){
     return    EmployeeResponseDto.builder()
                .name(entity.getName())
                .surname(entity.getSurname())
                .role(entity.getRole())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .dealerId(entity.getDealerEntity().getId())
                .id(entity.getId())
             .createdBy(entity.getCreatedBy())
             .createdDate(entity.getCreatedDate())
             .lastModifiedBy(entity.getLastModifiedBy())
             .lastModifiedDate(entity.getLastModifiedDate())
                .build();
    }
    public List<EmployeeResponseDto> toDtoList(List<EmployeEntity> employeEntityList){
        EmployeeMapper employeeMapper = new EmployeeMapper();
     return    employeEntityList.stream().map(employeeMapper::toDto).collect(Collectors.toList());
    }
}
