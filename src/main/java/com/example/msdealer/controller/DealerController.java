package com.example.msdealer.controller;

import com.example.msdealer.dto.request.DealerRequestDTO;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/dealer")
@SecurityRequirement(name = "BearerAuth")
public class DealerController {
    private final DealerServiceImpl dealerService;

    @PutMapping
    public ResponseEntity<SuccessDetails<String>> updateDealer(@RequestBody DealerRequestDTO dealerRequestDTO) throws MethodArgumentNotValidException {
        dealerService.updateDealer(dealerRequestDTO);
        return ResponseEntity.ok(new SuccessDetails<>("Dealer info updated successfully!", HttpStatus.OK.value(), true));
    }

    @DeleteMapping
    public ResponseEntity<SuccessDetails<String>> deleteDealer() throws MethodArgumentNotValidException {
        dealerService.deleteDealer();
        return ResponseEntity.ok(new SuccessDetails<>("Dealer deleted successfully!", HttpStatus.OK.value(), true));
    }

    @GetMapping
    public ResponseEntity<SuccessDetails<DealerResponseDto>> getDealer() throws MethodArgumentNotValidException {
        return ResponseEntity.ok(new SuccessDetails<>(dealerService.getDealer(), HttpStatus.OK.value(), true));
    }
}