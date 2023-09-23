package com.example.msdealer.controller;

import com.example.msdealer.dto.request.ChangePasswordRequest;
import com.example.msdealer.dto.request.EmployeeRequestDTO;
import com.example.msdealer.dto.response.EmployeeResponseDto;
import com.example.msdealer.exception.MethodArgumentNotValidException;
import com.example.msdealer.exception.handler.SuccessDetails;
import com.example.msdealer.service.EmployeeService;
import com.example.msdealer.service.impl.EmployeeServiceImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/employee")
@SecurityRequirement(name = "Bearer Authentication")
public class EmployeeController {

    private  final EmployeeServiceImpl employeeService;

    @PutMapping("{id}")
    public ResponseEntity<SuccessDetails<String>> updateEmployee(@PathVariable(name = "id") Long id,@Valid @RequestBody EmployeeRequestDTO employeeRequestDTO) throws MethodArgumentNotValidException {
        employeeService.updateEmployee(id,employeeRequestDTO);
        return ResponseEntity.ok(new SuccessDetails<>("Employee infos updated Successfully!", HttpStatus.OK.value(),true));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<SuccessDetails<String>> deleteEmployee(@PathVariable(name = "id") Long id) throws MethodArgumentNotValidException {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok(new SuccessDetails<>("Employee deleted  Successfully!", HttpStatus.OK.value(),true));
    }
    @GetMapping("{id}")
    public ResponseEntity<SuccessDetails<EmployeeResponseDto>> getEmployee(@PathVariable(name = "id") Long id) throws MethodArgumentNotValidException {
        return ResponseEntity.ok(new SuccessDetails<>(employeeService.getEmployee(id),HttpStatus.OK.value(),true));
    }

    @GetMapping
    public ResponseEntity<SuccessDetails<List<EmployeeResponseDto>>> getEmployees() throws MethodArgumentNotValidException {
        return ResponseEntity.ok(new SuccessDetails<>(employeeService.getEmployees(),HttpStatus.OK.value(),true));
    }

    @PutMapping("/password-update")
    public ResponseEntity<SuccessDetails<String>> changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest) throws MethodArgumentNotValidException {
        employeeService.changePassword(changePasswordRequest);
        return ResponseEntity.ok(new SuccessDetails<>("Password changed  successfully!", HttpStatus.OK.value(), true));
    }

}
