package com.example.msdealer.controller;

import com.example.msdealer.dto.request.DealerRequestDTO;
import com.example.msdealer.dto.request.EmployeeRequestDTO;
import com.example.msdealer.dto.response.DealerResponseDto;
import com.example.msdealer.dto.response.EmployeeResponseDto;
import com.example.msdealer.exception.MethodArgumentNotValidException;
import com.example.msdealer.handler.SuccessDetails;
import com.example.msdealer.service.EmployeeService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/")
@SecurityRequirement(name = "Bearer Authentication")
public class EmployeeController {

    private  final EmployeeService employeeService;

    @PutMapping("employee/{id}")
    public ResponseEntity<SuccessDetails<String>> updateEmployee(@PathVariable(name = "id") Long id,@RequestBody EmployeeRequestDTO employeeRequestDTO) throws MethodArgumentNotValidException {
        employeeService.updateEmployee(id,employeeRequestDTO);
        return ResponseEntity.ok(new SuccessDetails<>("Employee infos updated Successfully!", HttpStatus.OK.value(),true));
    }

    @DeleteMapping("employee/{id}")
    public ResponseEntity<SuccessDetails<String>> deleteEmployee(@PathVariable(name = "id") Long id) throws MethodArgumentNotValidException {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok(new SuccessDetails<>("Employee deleted  Successfully!", HttpStatus.OK.value(),true));
    }
    @GetMapping("employee/{id}")
    public ResponseEntity<SuccessDetails<EmployeeResponseDto>> getEmployee(@PathVariable(name = "id") Long id) throws MethodArgumentNotValidException {
        return ResponseEntity.ok(new SuccessDetails<>(employeeService.getEmployee(id),HttpStatus.OK.value(),true));
    }

    @GetMapping("employee")
    public ResponseEntity<SuccessDetails<List<EmployeeResponseDto>>> getEmployees() throws MethodArgumentNotValidException {
        return ResponseEntity.ok(new SuccessDetails<>(employeeService.getEmployees(),HttpStatus.OK.value(),true));
    }

}