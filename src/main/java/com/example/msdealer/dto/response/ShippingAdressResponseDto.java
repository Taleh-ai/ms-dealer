package com.example.msdealer.dto.response;

import lombok.Data;


@Data
public class ShippingAdressResponseDto {

    private Long addressId;
    private String country;
    private String city;
    private String street;
    private int homeNo;
    private Long customerId;
    private String adressPurpose;
}
