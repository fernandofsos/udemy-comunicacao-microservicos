package br.com.cursoudemy.productapi.dto;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {
	
	private Integer id;
	private String name;
	private String email; 
	
	public static JwtResponse getUser(Claims jwtClaims ) {
		
		try {
			return new ObjectMapper().convertValue(jwtClaims.get("authUser"), JwtResponse.class);
							
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
