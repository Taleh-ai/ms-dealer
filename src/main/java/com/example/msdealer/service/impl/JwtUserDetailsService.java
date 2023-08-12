package com.example.msdealer.service.impl;

import com.example.mscustomers.entity.CustomerEntity;
import com.example.mscustomers.repository.CustomerRepository;
import com.example.msdealer.repository.DealerRepository;
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CustomerEntity user = repository.findCustomerEntitiesByEmail(username);

        if (user != null) {
            return new User(user.getEmail(), user.getPassword(),
                    new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }

}