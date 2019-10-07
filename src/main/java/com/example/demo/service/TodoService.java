package com.example.demo.service;

import java.io.IOException;
import java.util.List;

import com.example.demo.model.Todo;
import com.example.demo.model.TodoInit;
import com.google.api.services.calendar.model.Event;

public interface TodoService {

	List<Todo> viewAllEvents() throws IOException;
	Event addEvent(TodoInit todoInit) throws IOException;
	Todo viewEventById(String eventId) throws IOException;
	Event updateEvent(String eventId, TodoInit todoInit) throws IOException;
	void deleteEvent(String eventId) throws IOException;
	
}