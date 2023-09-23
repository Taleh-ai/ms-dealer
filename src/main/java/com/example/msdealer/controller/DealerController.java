package com.example.msdealer.controller;

import com.example.msdealer.dto.request.ChangePasswordRequest;
import com.example.msdealer.dto.request.DealerRequestDTO;
import com.example.msdealer.dto.request.DealerUpdateRequestDTO;
import com.example.msdealer.dto.response.DealerResponseDto;
import com.example.msdealer.exception.MethodArgumentNotValidException;
import com.example.msdealer.exception.handler.SuccessDetails;
import com.example.msdealer.service.impl.DealerServiceImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/dealer")
@SecurityRequirement(name = "Bearer Authentication")
public class DealerController {
    private final DealerServiceImpl dealerService;

    @PutMapping
    public ResponseEntity<SuccessDetails<String>> updateDealer(@Valid @RequestBody DealerUpdateRequestDTO dealerRequestDTO) throws MethodArgumentNotValidException {
        dealerService.updateDealer(dealerRequestDTO);
        return ResponseEntity.ok(new SuccessDetails<>("Dealer info updated successfully!", HttpStatus.OK.value(), true));
    }

    @DeleteMapping
    public ResponseEntity<SuccessDetails<String>> deleteDealer() throws MethodArgumentNotValidException {
        dealerService.deleteDealer();
        return ResponseEntity.ok(new SuccessDetails<>("Dealer deleted successfully!", HttpStatus.OK.value(), true));
    }

    @GetMapping
    public ResponseEntity<SuccessDetails<DealerResponseDto>> getDealer() {
        ResponseEntity<DealerResponseDto> dealerResponse = dealerService.getDealer();

        if (dealerResponse.getStatusCode() == HttpStatus.OK) {
            // User has permission and dealer is found, return a success response
            SuccessDetails<DealerResponseDto> successDetails = new SuccessDetails<>(
                    dealerResponse.getBody(),
                    HttpStatus.OK.value(),
                    true
            );
            return ResponseEntity.ok(successDetails);
        } else if (dealerResponse.getStatusCode() == HttpStatus.FORBIDDEN) {
            // User doesn't have permission, return a Forbidden response
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } else if (dealerResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
            // Dealer not found, return a Not Found response
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // Handle other status codes if necessary
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }


    @PutMapping("/password-update")
    public ResponseEntity<SuccessDetails<String>> changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest) throws MethodArgumentNotValidException {
        dealerService.changePassword(changePasswordRequest);
        return ResponseEntity.ok(new SuccessDetails<>("Password changed  successfully!", HttpStatus.OK.value(), true));
    }
}