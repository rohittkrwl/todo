package com.rohit.todo.service;

import java.util.List;

import com.rohit.todo.dto.ToDoListDTO;
import com.rohit.todo.exception.ToDoException;

public interface ToDoListService {

	public List<ToDoListDTO> getList(String emailId) throws ToDoException;
	public void deleteList(Integer listId, String emailId) throws ToDoException;
	public String createList(ToDoListDTO toDoListDTO, String emailId) throws ToDoException;
	public Integer updateList(ToDoListDTO toDoListDTO) throws ToDoException;
}
