package com.mag.jwtdemo.controller;

import java.util.ArrayList;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mag.jwtdemo.dto.JwtReqLoginDTO;
import com.mag.jwtdemo.dto.JwtReqRegisterDTO;
import com.mag.jwtdemo.dto.JwtResDTO;
import com.mag.jwtdemo.repository.UserRepository;
import com.mag.jwtdemo.service.UserService;
import com.mag.jwtdemo.utils.JWTUtility;
import com.mag.jwtdemo.entity.*;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class WelcomeController {
	@Autowired
	private JWTUtility jwtUtility;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private  UserRepository repository;
	@Autowired
    private  ModelMapper modelMapper;

	@GetMapping("/")
	public String welcome() {
		return "Welcome to Mag";
	}

	@PostMapping("/authenticate")
	public JwtResDTO authenticate(@RequestBody JwtReqLoginDTO jwtRequest) throws Exception {

		log.info("auth entered");

		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
		

		final UserDetails userDetails = userService.loadUserByUsername(jwtRequest.getUsername());

		final String token = jwtUtility.generateToken(userDetails);

		return new JwtResDTO(token);
	}
	
	@PostMapping("/register")
	public JwtResDTO register(@RequestBody JwtReqRegisterDTO request) throws Exception {

		log.info("auth entered");

		User user = new User();
		
        modelMapper.map(request, user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.ADMIN);
        log.info("user: {}",user);
        repository.save(user);
        
        
        
		final String token = jwtUtility.generateToken(user);

		return new JwtResDTO(token);
	}
}
