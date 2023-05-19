package com.mag.jwtdemo.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.mag.jwtdemo.entity.User;
import com.mag.jwtdemo.repository.UserRepository;

@Configuration
public class ApplicationConfig {
	@Autowired
    private  UserRepository repository;
	@Autowired
	@Lazy
	private PasswordEncoder passwordEncoder;
	
    @Bean
    PasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder();
	}
	
	@Configuration
	public class AppConfiguration {
        @Bean
        ModelMapper modelMapper() {
	        return new ModelMapper();
	    }
	}

    @Bean
    AuthenticationProvider authenticationProvider() {
	    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	    authProvider.setUserDetailsService(userDetailsService());
	    authProvider.setPasswordEncoder(passwordEncoder());
	    return authProvider;
	  }
//    
    @Bean
    UserDetailsService userDetailsService() {
      return username -> repository.findByUsername(username)
          .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }


    

}
