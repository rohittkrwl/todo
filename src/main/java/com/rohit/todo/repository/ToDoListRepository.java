package com.rohit.todo.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.rohit.todo.entity.ToDoList;

public interface ToDoListRepository extends CrudRepository<ToDoList,Integer>{

	Optional<ToDoList> findByName(String listName);

}
