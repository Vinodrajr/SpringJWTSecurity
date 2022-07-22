package com.cl.springsecurityjwt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cl.springsecurityjwt.dto.User;

public interface UserRepo extends JpaRepository<User, Integer> {
	
	public Optional<User> findByUsername(String username);

}
