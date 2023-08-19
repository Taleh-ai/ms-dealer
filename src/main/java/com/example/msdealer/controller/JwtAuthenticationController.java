package com.example.msdealer.controller;


import com.example.msdealer.dto.mapper.DealerMapper;
import com.example.msdealer.dto.mapper.EmployeeMapper;
import com.example.msdealer.dto.request.DealerRequestDTO;
import com.example.msdealer.dto.request.EmployeeRequestDTO;
import com.example.msdealer.dto.response.AfterSignInResponseDto;
import com.example.msdealer.entity.DealerEntity;
import com.example.msdealer.entity.EmployeEntity;
import com.example.msdealer.model.JwtRequest;
import com.example.msdealer.repository.DealerRepository;
import com.example.msdealer.repository.EmployeeRepository;
import com.example.msdealer.securityconfig.JwtTokenUtil;
import com.example.msdealer.service.impl.MailService;
import lombok.RequiredArgsConstructor;
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
public class JwtAuthenticationController {


	private final AuthenticationManager authenticationManager;
	private final JwtTokenUtil jwtTokenUtil;
	private final MailService mailService;
	private final UserDetailsService jwtInMemoryUserDetailsService;

    private final DealerRepository  dealerRepository;
    private final EmployeeRepository employeeRepository;
	private final DealerMapper dealerMapper;
	private final EmployeeMapper employeeMapper;


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
	public ResponseEntity<?> signUp (@RequestBody DealerRequestDTO dto) throws MessagingException, IOException {
		dealerRepository.findDealerEntityByEmail(dto.getEmail());
		if (dealerRepository.existsByEmail(dto.getEmail())) {
			DealerEntity userEntity = dealerMapper.fromDto(dto);
			dealerRepository.save(userEntity);
			mailService.mailSender(dto.getEmail());
			return ResponseEntity.ok("You signed!");

		}else
			return ResponseEntity.ok("This account already exist in our DB!");

	}

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public ResponseEntity<?> registerEmployee (@RequestBody EmployeeRequestDTO employeeRequestDTO) throws MessagingException, IOException {
		EmployeEntity entity = employeeRepository.findEmployeEntityByEmail(employeeRequestDTO.getEmail());
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		DealerEntity dealerEntity = (DealerEntity) userDetails;
        if (entity == null) {
			EmployeEntity employeEntity = employeeMapper.fromDto(employeeRequestDTO);
			employeEntity.setDealerEntity(dealerEntity);
            employeeRepository.save(employeEntity);
            mailService.mailSender(employeeRequestDTO.getEmail());
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
