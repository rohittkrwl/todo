package com.rohit.todo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rohit.todo.dto.ToDoListDTO;
import com.rohit.todo.entity.ToDoList;
import com.rohit.todo.entity.User;
import com.rohit.todo.exception.ToDoException;
import com.rohit.todo.repository.ToDoListRepository;
import com.rohit.todo.repository.UserRepository;

@Service
@Transactional
public class ToDoListServiceImpl implements ToDoListService{
	
	@Autowired
	private ToDoListRepository listRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public List<ToDoListDTO> getList(String emailId) throws ToDoException {
		
		Optional<User> optional = userRepository.findById(emailId);
		User user = optional.orElseThrow(()->new ToDoException("Service.User_NOT_FOUND"));
		List<ToDoList> toDoList = user.getToDoList();
		if(toDoList == null || toDoList.isEmpty())
			throw new ToDoException("Service.NO_TO_DO_LIST_PRESENT");
		return toDoList.stream().map(toDo ->
				{
					ToDoListDTO listDTO = new ToDoListDTO();
					listDTO.setListId(toDo.getListId());
					listDTO.setListName(toDo.getListName());
					listDTO.setListItems(toDo.getListItems());
					return listDTO;
				})
						.collect(Collectors.toList());
		
	}

	@Override
	public Integer updateList(ToDoListDTO toDoList) throws ToDoException {
		Optional<ToDoList> optional = listRepository.findById(toDoList.getListId());
		ToDoList updateToDoList = optional.orElseThrow(()->new ToDoException("Service.TO_DO_LIST_NOT_FOUND"));
		updateToDoList.setListName(toDoList.getListName());
		updateToDoList.setListItems(toDoList.getListItems());
		return updateToDoList.getListId();
	}

	@Override
	public void deleteList(Integer listId, String emailId) throws ToDoException {
		Optional<User> optional = userRepository.findById(emailId);
		User user = optional.orElseThrow(()->new ToDoException("Service.User_NOT_FOUND"));
		List<ToDoList> toDoList = user.getToDoList();
		if(toDoList == null || toDoList.isEmpty())
			throw new ToDoException("Service.NO_TO_DO_LIST_PRESENT");
		for(ToDoList toDo : toDoList) {
			if(toDo.getListId().equals(listId)) {
				toDoList.remove(toDo);
				break;
			}
		}
		user.setToDoList(toDoList);
		Optional<ToDoList> optional1 = listRepository.findById(listId);
		optional1.orElseThrow(()->new ToDoException("Service.TO_DO_LIST_NOT_FOUND"));
		listRepository.deleteById(listId);
	}

	@Override
	public String createList(ToDoListDTO toDoListDTO, String emailId) throws ToDoException {
		Optional<User> optional = userRepository.findById(emailId);
		User user = optional.orElseThrow(()->new ToDoException("Service.User_NOT_FOUND"));
		List<ToDoList> toDoList = user.getToDoList();
		ToDoList toDo = new ToDoList();
		toDo.setListName(toDoListDTO.getListName());
		toDo.setListItems(toDoListDTO.getListItems());
		toDoList.add(toDo);
		user.setToDoList(toDoList);
		return toDoListDTO.getListName();
	}

}
