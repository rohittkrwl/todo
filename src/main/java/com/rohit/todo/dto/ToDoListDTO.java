package com.rohit.todo.dto;

import java.util.List;

public class ToDoListDTO {
	
	private Integer listId;
	private String listName;
	private List<String> listItems;
	
	public Integer getListId() {
		return listId;
	}
	public void setListId(Integer listId) {
		this.listId = listId;
	}
	public String getListName() {
		return listName;
	}
	public void setListName(String listName) {
		this.listName = listName;
	}
	public List<String> getListItems() {
		return listItems;
	}
	public void setListItems(List<String> listItems) {
		this.listItems = listItems;
	}	

}
