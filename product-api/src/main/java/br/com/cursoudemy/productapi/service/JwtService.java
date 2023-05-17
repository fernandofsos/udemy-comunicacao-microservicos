package br.com.cursoudemy.productapi.service;

import org.springframework.stereotype.Service;

import br.com.cursoudemy.productapi.dto.JwtResponse;
import br.com.cursoudemy.productapi.exception.AuthenticationException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import static org.springframework.util.ObjectUtils.isEmpty;

import org.apache.logging.log4j.util.Strings;

import lombok.Data;

@Data
@Service
public class JwtService {

	private static final String EMPTY_SPACE = " ";
	private static final Integer TOKEN_INDEX = 1;

	@Value("${app-config.secrets.api-secret}")
	private String apiSecret;

	public void validateAuthorization(String token) {

		var acessToken = extractToken(token);

		try {
			var claims = Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(apiSecret.getBytes())).build()
					.parseClaimsJws(acessToken).getBody();
			var name = JwtResponse.getUser(claims).getName();
			var user = JwtResponse.getUser(claims);

			if (isEmpty(user) || isEmpty(user.getId())) {
				throw new AuthenticationException("The user is not valid.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new AuthenticationException("Errer while traying to proccess the Access Token.");
		}
	}

	private String extractToken(String token) {
		if (isEmpty(token)) {
			throw new AuthenticationException("The access Token was not informed.");
		}

		if (token.contains(EMPTY_SPACE)) {
			return token.split(EMPTY_SPACE)[TOKEN_INDEX];
		}

		return token;
	}
}
