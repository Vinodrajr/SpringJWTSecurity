package com.cl.springsecurityjwt.util;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {

	@Value("{app.sercret}")
	private String sercret;

	// generate Token
	public String generateToken(String subject) {

		return Jwts.builder().setSubject(subject).setIssuer("vinod").setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(5400000)))
				.signWith(SignatureAlgorithm.HS512, sercret.getBytes()).compact();
	}

	// claims

	public Claims getClaims(String token) {

		return Jwts.parser().setSigningKey(sercret.getBytes()).parseClaimsJws(token).getBody();
	}

	// Read Exp Date from the given token
	public Date getExpDate(String token) {
		return getClaims(token).getExpiration();
	}

	// read subject(user) from the token
	public String getUsername(String token) {
		return getClaims(token).getSubject();
	}

	// Validate the Token if exp or not
	public boolean isTokenExp(String token) {
		Date date = getExpDate(token);
		return date.before(new Date(System.currentTimeMillis()));
	}

	// validate username in token and database are same and in valid token not exp
	// token
	public boolean validateToken(String token, String username) {
		String tokenusername = getUsername(token);
		return (username.equals(tokenusername) && !isTokenExp(token));

	}

}
