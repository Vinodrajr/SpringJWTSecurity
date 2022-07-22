package com.cl.springsecurityjwt.service;

import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cl.springsecurityjwt.dao.UserDao;
import com.cl.springsecurityjwt.dto.User;

@Service
public class UserService implements UserDetailsService {
	@Autowired
	UserDao dao;

	@Autowired
	BCryptPasswordEncoder encoder;

	public User saveUser(User user) {
		user.setPassword(encoder.encode(user.getPassword()));
		return dao.saveUser(user);
	}

	public Optional<User> findByUsername(String username) {
		return dao.findByUsername(username);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<User> optional = findByUsername(username);
		if (optional.isEmpty()) {
			throw new UsernameNotFoundException("Username " + username + " is not found in Database");
		}
		//
		User user = optional.get();
		return new org.springframework.security.core.userdetails.User(username, user.getPassword(),
				user.getRole().stream().map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList()));
	}
}
