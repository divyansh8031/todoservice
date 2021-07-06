package com.devu.rest.webservices.restfulwebservices.todo;
 
import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;




@CrossOrigin(origins="http://localhost:4200")
@RestController
public class TodoResource {
	 
	@Autowired
	private TodoHardcodedService todoService;
	
	@GetMapping("/users/{username}/todos")
	public ResponseEntity <List<Todo>> getAllTodos( @PathVariable String username)
	{
		List<Todo> list=todoService.findAll();
		if(list.size()<=0)
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		return ResponseEntity.of(Optional.of(list));
		
	}
	@GetMapping("/users/{username}/todos/{id}")
	public ResponseEntity<Todo> getTodo( @PathVariable String username,@PathVariable long id)
	{
		Todo todo=todoService.findById(id);
		if(todo==null)
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.of(Optional.of(todo));
		
		
	}
	
	@DeleteMapping("/users/{username}/todos/{id}")
	public ResponseEntity<Void> deleteTodo(
			@PathVariable String username,@PathVariable long id)
	{
			Todo todo=todoService.deleteById(id);
			if(todo!=null)
			{
				return ResponseEntity.noContent().build();
			}
				return ResponseEntity.notFound().build();
	}
	 @PutMapping("/users/{username}/todos/{id}")
			 public ResponseEntity<Todo> updateTodo(
					 @PathVariable String username,
						@PathVariable long id , @RequestBody Todo todo) {
		 try
		 {
		 
		 		Todo todoUpdated=null;
		 		todoUpdated =todoService.save(todo);
				return new ResponseEntity<Todo>(todo,HttpStatus.OK);
				}
		 catch(Exception e)
		 {
			 	e.printStackTrace();
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		 }
	 }
	 @PostMapping("/users/{username}/todos")
	 public  ResponseEntity<Todo> updateTodo(
			 @PathVariable String username, @RequestBody Todo todo) {
		 Todo createdTodo=null;
		try
		{
		 createdTodo =todoService.save(todo);
		 return ResponseEntity.of(Optional.of(createdTodo));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		//Location
		//Get current resource url
		///{id}
//		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
//				.path("/{id}").buildAndExpand(createdTodo.getId()).toUri();
//		return  ResponseEntity.created(uri).build();
//		}

}}
