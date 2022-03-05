package com.rohit.todo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rohit.todo.dto.UserDTO;
import com.rohit.todo.entity.User;
import com.rohit.todo.exception.ToDoException;
import com.rohit.todo.repository.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDTO authenticateUser(String emailId, String password) throws ToDoException {
		UserDTO userDTO = null;
		//retrieving User data from repository
		Optional<User> optionalUser = userRepository.findById(emailId.toLowerCase());
		User user = optionalUser.orElseThrow(() -> new ToDoException("Service.User_NOT_FOUND"));
		//comparing entered password with password stored in DB
		if (!password.equals(user.getPassword()))
			throw new ToDoException("UserService.INVALID_CREDENTIALS");
		return null;
	}

	@Override
	public String registerNewUser(UserDTO userDTO) throws ToDoException {
		String registeredWithEmailId = null;
		//check whether specified email id is already in use by other User
		boolean isEmailNotAvailable = userRepository.findById(userDTO.getEmailId().toLowerCase()).isEmpty();
		//check whether specified phone no. is already in use by other User
		boolean isPhoneNumberNotAvailable = userRepository.findByPhoneNumber(userDTO.getPhoneNumber()).isEmpty();
		if (isEmailNotAvailable) {
			if (isPhoneNumberNotAvailable) {
				User user = new User();
				user.setEmailId(userDTO.getEmailId().toLowerCase());
				user.setName(userDTO.getName());
				user.setPassword(userDTO.getPassword());
				user.setPhoneNumber(userDTO.getPhoneNumber());
				userRepository.save(user);
				registeredWithEmailId = user.getEmailId();
			} else {
				throw new ToDoException("UserService.PHONE_NUMBER_ALREADY_IN_USE");
			}
		} else {
			throw new ToDoException("UserService.EMAIL_ID_ALREADY_IN_USE");
		}
		return registeredWithEmailId;
	}

	@Override
	public void updateProfile(UserDTO userDTO) throws ToDoException {
		User newUser = null;
		//retrieving all list of Users from repository, whose phone number same as received phone number
		List<User> users = userRepository.findByPhoneNumber(userDTO.getPhoneNumber());
		if (!users.isEmpty()) {
			//take first User object from the list if list is not-Empty
			newUser = users.get(0);
		} else {
			//if list is empty
			//retrieve User details by emailId
			Optional<User> optionalUser = userRepository.findById(userDTO.getEmailId().toLowerCase());
			User User = optionalUser.orElseThrow(() -> new ToDoException("Service.User_NOT_FOUND"));

			User.setName(userDTO.getName());
			User.setPhoneNumber(userDTO.getPhoneNumber());
			return;
		}
		// if list is not-Empty
		// compare emailId of first User object from list with emailLd of received User  
		if (!newUser.getEmailId().equalsIgnoreCase(userDTO.getEmailId())) {
			throw new ToDoException("UserService.PHONE_NUMBER_ALREADY_IN_USE");
		} else {
			Optional<User> optionalUser = userRepository.findById(userDTO.getEmailId().toLowerCase());
			User user = optionalUser.orElseThrow(() -> new ToDoException("Service.User_NOT_FOUND"));
			user.setName(userDTO.getName());
			user.setPhoneNumber(userDTO.getPhoneNumber());
		}
		
	}

}
