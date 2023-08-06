package com.example.msdealer.dto.mapper;

import com.example.mscustomers.dto.request.ShippingAdressRequestDto;
import com.example.mscustomers.dto.response.ShippingAdressResponseDto;
import com.example.mscustomers.entity.ShippingAdressEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
@Component

public class ShippingAdressMapper {



    public ShippingAdressEntity fromDto(ShippingAdressRequestDto dto){
        ShippingAdressEntity entity = new ShippingAdressEntity();
        entity.setCity(dto.getCity());
        entity.setAdressPurpose(dto.getAdressPurpose());
        entity.setCountry(dto.getCountry());
        entity.setStreet(dto.getStreet());
        entity.setHomeNo(dto.getHomeNo());
        return entity;
    }

    public ShippingAdressResponseDto toDto(ShippingAdressEntity entity){
        ShippingAdressResponseDto dto = new ShippingAdressResponseDto();
        dto.setAddressId(entity.getAddressId());
        dto.setCity(entity.getCity());
        dto.setAdressPurpose(entity.getAdressPurpose());
        dto.setCountry(entity.getCountry());
        dto.setStreet(dto.getStreet());
        dto.setCustomerId(entity.getCustomerEntity().getId());
        dto.setHomeNo(dto.getHomeNo());
        return dto;
    }

    public List<ShippingAdressResponseDto> toDtoList(List<ShippingAdressEntity> shippingAdressEntityList){
        ShippingAdressMapper shippingAdressMapper = new ShippingAdressMapper();
       return shippingAdressEntityList.stream().map(n->shippingAdressMapper.toDto(n)).collect(Collectors.toList());
    }
}
