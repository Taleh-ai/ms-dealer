package com.example.msdealer.service.impl;

import com.example.msdealer.dto.request.ChangePasswordRequest;
import com.example.msdealer.dto.response.AfterSignInResponseDto;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
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

    @Override
    public void changePassword(ChangePasswordRequest request) throws MethodArgumentNotValidException {

            EmployeEntity user = employeeRepository.findEmployeEntityByEmail(request.getUsername());

            // Log the password change action
            logger.info("Changing password for user with email: {}", request.getUsername());

            if (passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
                // If the old password matches, update the password
                user.setPassword(passwordEncoder.encode(request.getNewPassword())); // Encode and set the new password
                employeeRepository.save(user);


            } else {
                // Log the invalid credentials
                logger.error("Invalid credentials for user with email: {}", request.getUsername());
                throw new MethodArgumentNotValidException("Invalid credentials");
            }
        }
    }


