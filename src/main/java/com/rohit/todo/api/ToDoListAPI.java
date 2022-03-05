package com.rohit.todo.api;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rohit.todo.dto.ToDoListDTO;
import com.rohit.todo.exception.ToDoException;
import com.rohit.todo.service.ToDoListService;


@RestController
@CrossOrigin
@RequestMapping(value="/ToDoList-api")
@Validated
public class ToDoListAPI {

	@Autowired
	private ToDoListService toDoListService;
	
	@Autowired
	private Environment environment;
	
	@GetMapping
	(value="/get")
	public ResponseEntity<List<ToDoListDTO>> getToDoLists(@RequestParam String emailId) throws ToDoException{
		List<ToDoListDTO> cards = null;
		cards = toDoListService.getList(emailId);
		return new ResponseEntity<>(cards,HttpStatus.OK);
	}
	
	@PutMapping(value="/put")
	public ResponseEntity<String> updateToDoList(@Valid @RequestBody ToDoListDTO toDoListDTO) throws ToDoException, NoSuchAlgorithmException{
		Integer id = toDoListService.updateList(toDoListDTO);
		String message = environment.getProperty("ToDoListAPI.UPDATE_CARD_SUCCESS")+id;
		return new ResponseEntity<>(message,HttpStatus.OK);
	}
	
	@PostMapping(value="/post")
	public ResponseEntity<String> addToDoList(@Valid @RequestBody ToDoListDTO toDoListDTO,@RequestParam String emailId) throws ToDoException, NoSuchAlgorithmException{
		String listName = toDoListService.createList(toDoListDTO,emailId);
		String message = environment.getProperty("ToDoListAPI.ADD_CARD_SUCCESS")+ listName;
		return new ResponseEntity<>(message,HttpStatus.CREATED);
	}
	
	@DeleteMapping(value="/delete/{toDoListId}")
	public ResponseEntity<String> deleteToDoList(@PathVariable Integer toDoListId,@RequestParam String emailId) throws ToDoException{
		toDoListService.deleteList(toDoListId,emailId);
		String message = environment.getProperty("ToDoListAPI.DELETE_CARD_SUCCESS");
		return new ResponseEntity<>(message,HttpStatus.OK);
	}
}
