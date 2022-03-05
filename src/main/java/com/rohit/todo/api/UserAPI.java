package com.rohit.todo.api;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rohit.todo.dto.UserDTO;
import com.rohit.todo.exception.ToDoException;
import com.rohit.todo.service.UserService;

@CrossOrigin
@RestController
@RequestMapping(value = "/user-api")
@Validated
public class UserAPI {

	@Autowired
	private UserService userService;

	@Autowired
	private Environment environment;

	static Log logger = LogFactory.getLog(UserAPI.class);
	
	@PostMapping(value = "/login")
	public ResponseEntity<UserDTO> authenticateUser(@Valid @RequestBody UserDTO userDTO)
			throws ToDoException {

		logger.info("User TRYING TO LOGIN, VALIDATING CREDENTIALS. User EMAIL ID: " + userDTO.getEmailId());
		UserDTO userDTOFromDB = userService.authenticateUser(userDTO.getEmailId(),
				userDTO.getPassword());
		logger.info("User LOGIN SUCCESS, User EMAIL : " + userDTOFromDB.getEmailId());
		return new ResponseEntity<>(userDTOFromDB, HttpStatus.OK);
	}

	@PostMapping(value = "/Users")
	public ResponseEntity<String> registerUser(@Valid @RequestBody UserDTO userDTO) throws ToDoException {

		logger.info("User TRYING TO REGISTER. User EMAIL ID: " + userDTO.getEmailId());
		String registeredWithEmailID = userService.registerNewUser(userDTO);
		registeredWithEmailID = environment.getProperty("UserAPI.User_REGISTRATION_SUCCESS")
				+ registeredWithEmailID;
		return new ResponseEntity<>(registeredWithEmailID, HttpStatus.OK);
	}

	@PutMapping(value = "/Users")
	public ResponseEntity<String> updateUserProfile(@Valid @RequestBody UserDTO userDTO)
			throws ToDoException {
		userService.updateProfile(userDTO);
		String modificationSuccessMsg = environment.getProperty("UserAPI.User_DETAILS_UPDATION_SUCCESS");
		return new ResponseEntity<>(modificationSuccessMsg, HttpStatus.OK);

	}

}
