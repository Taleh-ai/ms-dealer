package com.example.msdealer.service;

import com.example.msdealer.dto.request.ChangePasswordRequest;
import com.example.msdealer.dto.request.DealerUpdateRequestDTO;
import com.example.msdealer.dto.response.DealerResponseDto;
import com.example.msdealer.exception.MethodArgumentNotValidException;
import org.springframework.http.ResponseEntity;

public interface DealerService {
    public void updateDealer(DealerUpdateRequestDTO dealerRequestDTO) throws MethodArgumentNotValidException;
    public void deleteDealer();
    public ResponseEntity<DealerResponseDto> getDealer();
    public String changePassword(ChangePasswordRequest request) throws MethodArgumentNotValidException;
}
