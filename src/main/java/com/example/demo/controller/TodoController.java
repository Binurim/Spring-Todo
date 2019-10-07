package com.example.demo.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.example.demo.service.TodoServiceImpl;
import com.example.demo.model.Todo;
import com.example.demo.model.TodoInit;


@RestController
@RequestMapping("/")
public class TodoController {
	
	@Autowired
	TodoServiceImpl todoService;

    
    @GetMapping("/user")
    public Principal user(Principal principal) {
    return principal;
    }
    
    @PostMapping("/todos")
	public ResponseEntity<Object> addEvent( @RequestBody TodoInit todoInit) throws IOException {
    	return new ResponseEntity<>(todoService.addEvent(todoInit), HttpStatus.CREATED);
	}

	@GetMapping("/todos")
	public ResponseEntity<Object> viewAllEvents() throws IOException {
		List<Todo> eventList = todoService.viewAllEvents();

		if (eventList.isEmpty()) {
			return new ResponseEntity<>("No Upcoming Events", HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(eventList, HttpStatus.OK);
	}

	@GetMapping("/todos/{id}")
	public ResponseEntity<Object> viewTaskById(@PathVariable("id") String id) throws IOException {
		return new ResponseEntity<>(todoService.viewEventById(id), HttpStatus.OK);
	}

	@DeleteMapping("/todos/{id}")
	public ResponseEntity<Object> deleteTask(@PathVariable("id") String id) throws IOException {
		todoService.deleteEvent(id);
		return new ResponseEntity<>("Event deleted successfully", HttpStatus.OK);
	}

	@PutMapping("/todos/{id}")
	public ResponseEntity<Object> updateTask(@PathVariable("id") String id,
			 @RequestBody TodoInit todoInit) throws IOException {
		return new ResponseEntity<>(todoService.updateEvent(id, todoInit), HttpStatus.OK);
	}

}