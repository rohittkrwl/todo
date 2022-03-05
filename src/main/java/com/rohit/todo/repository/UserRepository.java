package com.rohit.todo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.rohit.todo.entity.User;

public interface UserRepository extends CrudRepository<User,String>{

	List<User> findByPhoneNumber(String phoneNumber);

}
