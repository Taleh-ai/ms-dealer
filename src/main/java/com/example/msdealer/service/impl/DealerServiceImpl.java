package com.example.msdealer.service.impl;

import com.example.msdealer.mapper.DealerMapper;
import com.example.msdealer.dto.request.DealerRequestDTO;
import com.example.msdealer.dto.response.DealerResponseDto;
import com.example.msdealer.entity.DealerEntity;
import com.example.msdealer.exception.MethodArgumentNotValidException;
import com.example.msdealer.repository.DealerRepository;
import com.example.msdealer.service.DealerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DealerServiceImpl implements DealerService {
    private final DealerRepository dealerRepository;
    private final PasswordEncoder passwordEncoder;
    private final DealerMapper dealerMapper;
    @Override
    public void updateDealer(DealerRequestDTO dealerRequestDTO) throws MethodArgumentNotValidException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        DealerEntity dealerEntity = dealerRepository.findDealerEntityByEmail(userDetails.getUsername());

        dealerEntity.setCompanyName(dealerRequestDTO.getCompanyName());
        dealerEntity.setContactNumber(dealerRequestDTO.getContactNumber());
        dealerEntity.setCategory(dealerRequestDTO.getCategory());
        dealerEntity.setLocation(dealerRequestDTO.getLocation());
        dealerEntity.setEmail(dealerRequestDTO.getEmail());

        if (!passwordEncoder.matches(dealerRequestDTO.getPassword(), dealerEntity.getPassword())) {
            dealerEntity.setPassword(passwordEncoder.encode(dealerRequestDTO.getPassword()));
        } else {
            throw new MethodArgumentNotValidException("New password can't be the same as the old password");
        }

        dealerRepository.save(dealerEntity);
    }

    @Override
    public void deleteDealer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        dealerRepository.deleteById(dealerRepository.findDealerEntityByEmail(userDetails.getUsername()).getId());
    }

    @Override
    public DealerResponseDto getDealer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return dealerMapper.toDto(dealerRepository.findDealerEntityByEmail(userDetails.getUsername()));
    }
}
