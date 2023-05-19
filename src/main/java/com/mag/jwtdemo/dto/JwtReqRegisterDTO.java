package com.mag.jwtdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtReqRegisterDTO {

	private String username;
	private String password;

	private String email;

	private String firstname;

	private String lastname;

}
