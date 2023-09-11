package com.example.msdealer.service.impl;

import com.example.msdealer.mapper.EmployeeMapper;
import com.example.msdealer.dto.request.EmployeeRequestDTO;
import com.example.msdealer.dto.response.EmployeeResponseDto;
import com.example.msdealer.entity.DealerEntity;
import com.example.msdealer.entity.EmployeEntity;
import com.example.msdealer.exception.MethodArgumentNotValidException;
import com.example.msdealer.repository.DealerRepository;
import com.example.msdealer.repository.EmployeeRepository;
import com.example.msdealer.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmployeeMapper employeeMapper;
    private final DealerRepository dealerRepository;
    @Override
    public void updateEmployee(Long id,EmployeeRequestDTO employeeRequestDTO) throws MethodArgumentNotValidException {
     EmployeEntity employeEntity= employeeRepository.getById(id);
     employeEntity.setEmail(employeeRequestDTO.getEmail());
     employeEntity.setRole(employeeRequestDTO.getRole());
     employeEntity.setName(employeeRequestDTO.getName());
     employeEntity.setSurname(employeeRequestDTO.getSurname());
        if (!passwordEncoder.matches(employeeRequestDTO.getPassword(),employeEntity.getPassword())) {
            employeEntity.setPassword(passwordEncoder.encode(employeeRequestDTO.getPassword()));
        }else {
            throw new MethodArgumentNotValidException("New password can't be same with old password");
        }
        employeeRepository.save(employeEntity);
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public EmployeeResponseDto getEmployee(Long id) {
        return employeeMapper.toDto(employeeRepository.getById(id));
    }

    @Override
    public List<EmployeeResponseDto> getEmployees() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        DealerEntity dealerEntity = dealerRepository.findDealerEntityByEmail(userDetails.getUsername());
        List<EmployeEntity> employeEntityList = employeeRepository.findEmployeEntitiesByDealerEntity(dealerEntity);
        return employeeMapper.toDtoList(employeEntityList);
    }
}
