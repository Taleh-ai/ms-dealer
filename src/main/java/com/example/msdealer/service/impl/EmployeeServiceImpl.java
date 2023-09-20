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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Override
    public void updateEmployee(Long id, EmployeeRequestDTO employeeRequestDTO) throws MethodArgumentNotValidException {
        logger.info("Updating employee with ID: {}", id);
        EmployeEntity employeEntity = employeeRepository.getById(id);

        employeEntity.setEmail(employeeRequestDTO.getEmail());
        employeEntity.setRole(employeeRequestDTO.getRole());
        employeEntity.setName(employeeRequestDTO.getName());
        employeEntity.setSurname(employeeRequestDTO.getSurname());

        if (!passwordEncoder.matches(employeeRequestDTO.getPassword(), employeEntity.getPassword())) {
            employeEntity.setPassword(passwordEncoder.encode(employeeRequestDTO.getPassword()));
        } else {
            logger.error("New password can't be same with old password for employee ID: {}", id);
            throw new MethodArgumentNotValidException("New password can't be same with old password");
        }

        employeeRepository.save(employeEntity);
        logger.info("Employee with ID {} updated successfully", id);
    }

    @Override
    public void deleteEmployee(Long id) {
        logger.info("Deleting employee with ID: {}", id);
        employeeRepository.deleteById(id);
        logger.info("Employee with ID {} deleted successfully", id);
    }

    @Override
    public EmployeeResponseDto getEmployee(Long id) {
        logger.info("Fetching employee with ID: {}", id);
        EmployeEntity employeeEntity = employeeRepository.getById(id);
        logger.info("Employee with ID {} fetched successfully", id);
        return employeeMapper.toDto(employeeEntity);
    }

    @Override
    public List<EmployeeResponseDto> getEmployees() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        DealerEntity dealerEntity = dealerRepository.getDealerEntityByEmail(userDetails.getUsername());

        logger.info("Fetching employees for dealer with ID: {}", dealerEntity.getId());
        List<EmployeEntity> employeEntityList = employeeRepository.findEmployeEntitiesByDealerEntity(dealerEntity);

        logger.info("Fetched {} employees for dealer with ID: {}", employeEntityList.size(), dealerEntity.getId());
        return employeeMapper.toDtoList(employeEntityList);
    }
}

