package com.kollu.customer.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kollu.customer.security.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	
	 User findByUserName(String username);

}
