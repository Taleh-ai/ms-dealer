package com.example.msdealer.controller;


import com.example.msdealer.dto.mapper.CustomerMapper;
import com.example.msdealer.securityconfig.JwtTokenUtil;
import com.example.msdealer.service.impl.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
public class JwtAuthenticationController {


	private final AuthenticationManager authenticationManager;


	private final JwtTokenUtil jwtTokenUtil;
	private final MailService mailService;

	private final UserDetailsService jwtInMemoryUserDetailsService;

	private final PasswordEncoder passwordEncoder;

	private final CustomerRepository customerRepo;
	private final CustomerMapper mapper;
	@RequestMapping(value = "/signin", method = RequestMethod.POST)
	public String  signIn(@RequestBody JwtRequest request)
			throws Exception {

		authenticate(request.getEmail(), request.getPassword());
		final UserDetails userDetails = jwtInMemoryUserDetailsService
				.loadUserByUsername(request.getEmail());

		final String token = jwtTokenUtil.generateToken(userDetails);
		AfterSignInResponseDto signInResponseDto = AfterSignInResponseDto.builder()
				.token(token)
				.email(request.getEmail())
				.password(request.getPassword())
				.role(request.getRole())
				.build();

		return  signInResponseDto.toString();
	}

	@RequestMapping(value = "/signup",method = RequestMethod.POST)
	public ResponseEntity<?> signUp (@RequestBody CustomerRequestDto dto) throws MessagingException, IOException {

		CustomerEntity entity = customerRepo.findCustomerEntitiesByEmail(dto.getEmail());
		if (entity == null) {
			CustomerEntity userEntity = mapper.fromDto(dto);
			customerRepo.save(userEntity);
			mailService.mailSender(dto.getEmail());
			return ResponseEntity.ok("You signed!");

		}else
			return ResponseEntity.ok("This account already exist in our DB!");

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
