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
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/")
@SecurityRequirement(name = "Bearer Authentication")
public class DealerController {
private final DealerServiceImpl dealerService;

@PutMapping("dealer")
    public ResponseEntity<SuccessDetails<String>> updateDealer(@RequestBody DealerRequestDTO dealerRequestDTO) throws MethodArgumentNotValidException {
    dealerService.updateDealer(dealerRequestDTO);
    return ResponseEntity.ok(new SuccessDetails<>("Dealer infos update Successfully!", HttpStatus.OK.value(),true));
}

    @DeleteMapping("dealer/{id}")
    public ResponseEntity<SuccessDetails<String>> deleteDealer() throws MethodArgumentNotValidException {
        dealerService.deleteDealer();
        return ResponseEntity.ok(new SuccessDetails<>("Dealer delete  Successfully!", HttpStatus.OK.value(),true));
    }
    @GetMapping("dealer")
    public ResponseEntity<SuccessDetails<DealerResponseDto>> getDealer() throws MethodArgumentNotValidException {
        return ResponseEntity.ok(new SuccessDetails<>(dealerService.getDealer(),HttpStatus.OK.value(),true));
    }

}
