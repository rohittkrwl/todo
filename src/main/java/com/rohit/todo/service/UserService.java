package com.rohit.todo.service;

import com.rohit.todo.dto.UserDTO;
import com.rohit.todo.exception.ToDoException;

public interface UserService {
	
	UserDTO authenticateUser(String emailId, String password) throws ToDoException;

	String registerNewUser(UserDTO UserDTO) throws ToDoException;

	void updateProfile(UserDTO UserDTO) throws ToDoException;
}
