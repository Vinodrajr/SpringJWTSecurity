package com.cl.springsecurityjwt.dao;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cl.springsecurityjwt.dto.User;
import com.cl.springsecurityjwt.repository.UserRepo;

@Repository
public class UserDao {

	@Autowired
	UserRepo repo;

	public User saveUser(User user) {
		return repo.save(user);
	}

	public Optional<User> findByUsername(String username) {
		return repo.findByUsername(username);
	}

}
