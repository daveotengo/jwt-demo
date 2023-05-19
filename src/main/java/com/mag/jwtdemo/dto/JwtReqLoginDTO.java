package com.mag.jwtdemo.dto;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtReqLoginDTO {

	private String username;
	private String password;
	
	  
}
