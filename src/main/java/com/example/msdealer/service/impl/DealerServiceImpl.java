package com.example.msdealer.service.impl;

import com.example.msdealer.dto.request.ChangePasswordRequest;
import com.example.msdealer.dto.response.AfterSignInResponseDto;
import com.example.msdealer.mapper.DealerMapper;
import com.example.msdealer.dto.request.DealerRequestDTO;
import com.example.msdealer.dto.response.DealerResponseDto;
import com.example.msdealer.entity.DealerEntity;
import com.example.msdealer.exception.MethodArgumentNotValidException;
import com.example.msdealer.repository.DealerRepository;
import com.example.msdealer.securityconfig.JwtTokenUtil;
import com.example.msdealer.service.DealerService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DealerServiceImpl implements DealerService {
    private final DealerRepository dealerRepository;

    private final DealerMapper dealerMapper;
    private static final Logger logger = LoggerFactory.getLogger(DealerServiceImpl.class);



    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final JwtUserDetailsService jwtUserDetailsService;
    private final PasswordEncoder passwordEncoder;
    @Override
    public void updateDealer(DealerRequestDTO dealerRequestDTO) throws MethodArgumentNotValidException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        DealerEntity dealerEntity = dealerRepository.getDealerEntityByEmail(userDetails.getUsername());

        // Log the update action
        logger.info("Updating dealer with email: {}", userDetails.getUsername());

        dealerEntity.setCompanyName(dealerRequestDTO.getCompanyName());
        dealerEntity.setContactNumber(dealerRequestDTO.getContactNumber());
        dealerEntity.setCategory(dealerRequestDTO.getCategory());
        dealerEntity.setLocation(dealerRequestDTO.getLocation());
        dealerEntity.setEmail(dealerRequestDTO.getEmail());

        dealerRepository.save(dealerEntity);
    }

    @Override
    public void deleteDealer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // Log the delete action
        logger.info("Deleting dealer with email: {}", userDetails.getUsername());

        dealerRepository.deleteById(dealerRepository.getDealerEntityByEmail(userDetails.getUsername()).getId());
        if (userDetails != null) {
            SecurityContextHolder.getContext().setAuthentication(null);
        }
    }

    @Override
    public DealerResponseDto getDealer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // Log the retrieval action
        logger.info("Retrieving dealer with email: {}", userDetails.getUsername());

        return dealerMapper.toDto(dealerRepository.getDealerEntityByEmail(userDetails.getUsername()));
    }

    public String changePassword(ChangePasswordRequest request) throws MethodArgumentNotValidException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getOldPassword())
        );

        DealerEntity user = dealerRepository.getDealerEntityByEmail(request.getUsername());

        // Log the password change action
        logger.info("Changing password for user with email: {}", request.getUsername());

        if (passwordEncoder.matches(request.getOldPassword(), request.getNewPassword())){
            user.setPassword(request.getNewPassword());
            dealerRepository.save(user);
        } else {
            // Log the invalid credentials
            logger.error("Invalid credentials for user with email: {}", request.getUsername());
            throw new MethodArgumentNotValidException("Invalid credentials");
        }

        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(request.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);

        AfterSignInResponseDto signInResponseDto = AfterSignInResponseDto.builder()
                .token(token)
                .email(user.getEmail())
                .password(user.getPassword())
                .role(String.valueOf(user.getRole()))
                .build();

        // Log the password change success
        logger.info("Password changed successfully for user with email: {}", request.getUsername());

        return signInResponseDto.toString();
    }
}