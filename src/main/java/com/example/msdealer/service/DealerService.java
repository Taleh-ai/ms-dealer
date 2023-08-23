package com.example.msdealer.service;

import com.example.msdealer.dto.request.DealerRequestDTO;
import com.example.msdealer.dto.response.DealerResponseDto;
import com.example.msdealer.exception.MethodArgumentNotValidException;

public interface DealerService {
    public void updateDealer(DealerRequestDTO dealerRequestDTO) throws MethodArgumentNotValidException;
    public void deleteDealer();
    public DealerResponseDto getDealer();
}
