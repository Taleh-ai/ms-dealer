package com.example.msdealer.service;

import com.example.msdealer.dto.request.DealerRequestDTO;

public interface DealerService {
    public void updateDealer(DealerRequestDTO dealerRequestDTO);
    public void deleteDealer();
}
