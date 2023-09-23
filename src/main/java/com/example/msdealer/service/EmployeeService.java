package com.example.msdealer.service;

import com.example.msdealer.dto.request.ChangePasswordRequest;
import com.example.msdealer.dto.request.DealerRequestDTO;
import com.example.msdealer.dto.request.EmployeeRequestDTO;
import com.example.msdealer.dto.response.DealerResponseDto;
import com.example.msdealer.dto.response.EmployeeResponseDto;
import com.example.msdealer.exception.MethodArgumentNotValidException;

import java.util.List;

public interface EmployeeService {

    public void updateEmployee(Long id,EmployeeRequestDTO employeeRequestDTO) throws MethodArgumentNotValidException;
    public void deleteEmployee(Long id);
    EmployeeResponseDto getEmployee(Long id);

    public List<EmployeeResponseDto> getEmployees();
    public void changePassword(ChangePasswordRequest request) throws MethodArgumentNotValidException;
}
