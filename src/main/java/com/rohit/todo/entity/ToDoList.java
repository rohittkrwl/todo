package com.rohit.todo.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;

@Entity
@Table(name="LIST_NAME")
public class ToDoList {
	
	@Id
	@Column(name="LIST_ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer listId;

	@Column(name="LIST_NAME")
	private String listName;
	
	@Column(name="LIST_ITEMS")
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
