package com.example.msdealer.controller;


import com.example.msdealer.dto.enumeration.Roles;
import com.example.msdealer.mapper.DealerMapper;
import com.example.msdealer.mapper.EmployeeMapper;
import com.example.msdealer.dto.request.DealerRequestDTO;
import com.example.msdealer.dto.request.EmployeeRequestDTO;
import com.example.msdealer.dto.response.AfterSignInResponseDto;
import com.example.msdealer.entity.DealerEntity;
import com.example.msdealer.entity.EmployeEntity;
import com.example.msdealer.dto.request.JwtRequest;
import com.example.msdealer.repository.DealerRepository;
import com.example.msdealer.repository.EmployeeRepository;
import com.example.msdealer.securityconfig.JwtTokenUtil;
import com.example.msdealer.service.impl.MailService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Objects;

@RestController
@CrossOrigin
@RequestMapping("v1/")
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationController {

	private final AuthenticationManager authenticationManager;
	private final JwtTokenUtil jwtTokenUtil;
	private final MailService mailService;
	private final UserDetailsService jwtInMemoryUserDetailsService;
	private final PasswordEncoder passwordEncoder;
	private final DealerRepository dealerRepository;
	private final EmployeeRepository employeeRepository;
	private final DealerMapper dealerMapper;
	private final EmployeeMapper employeeMapper;

	@RequestMapping(value = "/signin", method = RequestMethod.POST)
	public String signIn(@RequestBody JwtRequest request) throws Exception {
		// Authenticate and generate token
		try {
			authenticate(request.getEmail(), request.getPassword());
			UserDetails userDetails = jwtInMemoryUserDetailsService.loadUserByUsername(request.getEmail());
			String token = jwtTokenUtil.generateToken(userDetails);

			// Log successful sign-in
			log.info("User '{}' signed in successfully.", request.getEmail());

			AfterSignInResponseDto signInResponseDto = AfterSignInResponseDto.builder()
					.token(token)
					.email(request.getEmail())
					.password(request.getPassword())
					.role(request.getRole())
					.build();

			return signInResponseDto.toString();
		} catch (Exception e) {
			// Log authentication failure
			log.error("Authentication failed for user '{}': {}", request.getEmail(), e.getMessage());
			throw e;
		}
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public ResponseEntity<?> signUp(@RequestBody DealerRequestDTO dto) throws MessagingException, IOException {
		// Check if account exists and save
		if (!dealerRepository.existsByEmail(dto.getEmail())) {
			DealerEntity dealerEntity = dealerMapper.fromDto(dto);
			dealerRepository.save(dealerEntity);

			// Log successful sign-up
			log.info("User '{}' signed up successfully.", dto.getEmail());

			//mailService.mailSender(dto.getEmail());
			return ResponseEntity.ok("You signed!");
		} else {
			// Log account existence
			log.warn("Account with email '{}' already exists.", dto.getEmail());
			return ResponseEntity.ok("This account already exists in our DB!");
		}
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@SecurityRequirement(name = "Bearer Authentication")
	public ResponseEntity<?> registerEmployee(@RequestBody EmployeeRequestDTO employeeRequestDTO)
			throws MessagingException, IOException {
		EmployeEntity entity = employeeRepository.findEmployeEntityByEmail(employeeRequestDTO.getEmail());
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		DealerEntity dealerEntity = dealerRepository.findDealerEntityByEmail(userDetails.getUsername());
		if (entity == null) {
			EmployeEntity employeEntity = employeeMapper.fromDto(employeeRequestDTO);
			employeEntity.setDealerEntity(dealerEntity);
			employeEntity.setPassword(passwordEncoder.encode(employeeRequestDTO.getPassword()));
			employeeRepository.save(employeEntity);
			mailService.mailSender(employeeRequestDTO.getEmail());

			// Log successful employee registration
			log.info("Employee '{}' registered successfully.", employeeRequestDTO.getEmail());

			return ResponseEntity.ok("You signed!");
		} else {
			// Log account existence
			log.warn("Employee account with email '{}' already exists.", employeeRequestDTO.getEmail());
			return ResponseEntity.ok("This account already exists in our DB!");
		}
	}

	private void authenticate(String username, String password) throws Exception {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}