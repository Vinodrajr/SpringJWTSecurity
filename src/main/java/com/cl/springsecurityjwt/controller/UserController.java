package com.cl.springsecurityjwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cl.springsecurityjwt.dto.User;
import com.cl.springsecurityjwt.dto.UserRequest;
import com.cl.springsecurityjwt.dto.UserResponse;
import com.cl.springsecurityjwt.service.UserService;
import com.cl.springsecurityjwt.util.JwtUtil;

@RestController
public class UserController {
	@Autowired
	UserService service;
	@Autowired
	JwtUtil jwtUtil;

	@Autowired
	AuthenticationManager manager;

	@PostMapping("/save")
	public User saveUser(@RequestBody User user) {
		return service.saveUser(user);
	}

	@PostMapping("/login")
	public UserResponse loginUser(@RequestBody UserRequest request) {

		manager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		String token = jwtUtil.generateToken(request.getUsername());
		return new UserResponse(token, "Sucessfully generated token");
	}

	@PostMapping("/welcome")
	public String welcome() {
		return "Welcome";
	}

}
