package com.example.dutylogbackend.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dutylogbackend.ResourceNotFoundException;
import com.example.dutylogbackend.model.TodoTask;
import com.example.dutylogbackend.repository.TodoTaskRepository;

@RestController
@RequestMapping("/tasks")
public class TodoTaskController {
	@Autowired
	private TodoTaskRepository todoTaskRepository;
	
	@GetMapping
	public List<TodoTask> getTodoTasks() {
		return todoTaskRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public TodoTask getTodoTaskById(@PathVariable Long id) {
		return todoTaskRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("TodoTask not found with ID=" + id));
	}
	
	@PostMapping
	public TodoTask createTodoTask(@Valid @RequestBody TodoTask todoTask) {
		return todoTaskRepository.save(todoTask);
	}
	
	@PutMapping("/{id}")
	public TodoTask updateTodoTask(@PathVariable Long id, @RequestBody TodoTask todoTaskRequest) {
		return todoTaskRepository.findById(id).map(todoTask -> {
			String description = todoTaskRequest.getDescription();
			if(description != null) {
				todoTask.setDescription(description);
			}
			
			todoTask.setCompleted(todoTaskRequest.isCompleted());
			return todoTaskRepository.save(todoTask);
		}).orElseThrow(() -> new ResourceNotFoundException("TodoTask not found with ID=" + id));
	}
}
