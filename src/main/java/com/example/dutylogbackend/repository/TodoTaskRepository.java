package com.example.dutylogbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.dutylogbackend.model.TodoTask;

@Repository
public interface TodoTaskRepository extends JpaRepository<TodoTask, Long> {
}
