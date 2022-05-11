package com.njbailey.dutylogbackend.controller;

import com.njbailey.dutylogbackend.ResourceNotFoundException;
import com.njbailey.dutylogbackend.model.TodoTask;
import com.njbailey.dutylogbackend.repository.TodoTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
            if (description != null) {
                todoTask.setDescription(description);
            }

            todoTask.setCompleted(todoTaskRequest.isCompleted());
            return todoTaskRepository.save(todoTask);
        }).orElseThrow(() -> new ResourceNotFoundException("TodoTask not found with ID=" + id));
    }
}
