package com.example.msdealer.service.impl;


import com.example.msdealer.repository.DealerRepository;
import com.example.msdealer.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {
    private final DealerRepository dealerRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (dealerRepository.existsByEmail(username)) {
            return new User(dealerRepository.getDealerEntityByEmail(username).getEmail(), dealerRepository.getDealerEntityByEmail(username).getPassword(),
                    new ArrayList<>());
        } else if(employeeRepository.existsByEmail(username)){
            return new User(employeeRepository.findEmployeEntityByEmail(username).getEmail(), employeeRepository.findEmployeEntityByEmail(username).getPassword(),
                    new ArrayList<>());
        }
        else {
            throw new UsernameNotFoundException("User not found with email: " + username);
        }
    }

}