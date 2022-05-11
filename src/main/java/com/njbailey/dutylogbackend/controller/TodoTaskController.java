package com.njbailey.dutylogbackend.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.njbailey.dutylogbackend.ResourceNotFoundException;
import com.njbailey.dutylogbackend.model.TodoTask;
import com.njbailey.dutylogbackend.model.User;
import com.njbailey.dutylogbackend.repository.TodoTaskRepository;
import com.njbailey.dutylogbackend.repository.UserRepository;
import com.njbailey.dutylogbackend.security.JwtTokenUtil;
import com.njbailey.dutylogbackend.security.JwtUser;
import com.njbailey.dutylogbackend.security.controller.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Generated;
import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/tasks")
public class TodoTaskController {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private TodoTaskRepository todoTaskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @GetMapping
    public List<TodoTask> getTodoTasks() {
        return todoTaskRepository.findAll();
    }

    @GetMapping("/{dateString}")
    public Collection<TodoTask> getTodoTasksByDate(@PathVariable String dateString) {
        try {
            return todoTaskRepository.findTodoTasksByDate(DATE_FORMAT.parse(dateString));
        } catch (ParseException e) {
            return new ArrayList();
        }
    }

    @PostMapping
    public TodoTask createTodoTask(@Valid @RequestBody TodoTask todoTask) {
        return todoTaskRepository.save(todoTask);
    }

    @PutMapping("/{id}")
    public TodoTask updateTodoTask(@PathVariable Long id, @RequestBody TodoTask todoTaskRequest, @RequestHeader("${jwt.header}") String token) {
        System.out.println(token);

        DecodedJWT decodedJWT = jwtTokenUtil.getDecodedJWT(token).orElseThrow(() -> new AuthenticationException("Not authenticated."));
        User user = userRepository.findByUsername(decodedJWT.getSubject()).orElseThrow(() -> new AuthenticationException("No user found. Incident has been reported."));

        return todoTaskRepository.findById(id).map(todoTask -> {
            String description = todoTaskRequest.getDescription();
            if (description != null) {
                todoTask.setDescription(description);
            }

            boolean isCompleted = todoTaskRequest.isCompleted();
            User completedBy = isCompleted ? user : null;
            
            todoTask.setCompletedBy(completedBy);
	        todoTask.setCompleted(isCompleted);
            
            return todoTaskRepository.save(todoTask);
        }).orElseThrow(() -> new ResourceNotFoundException("TodoTask not found with ID=" + id));
    }
}
