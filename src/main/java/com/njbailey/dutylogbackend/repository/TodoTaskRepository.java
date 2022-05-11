package com.njbailey.dutylogbackend.repository;

import com.njbailey.dutylogbackend.model.TodoTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Date;

@Repository
public interface TodoTaskRepository extends JpaRepository<TodoTask, Long> {
    Collection<TodoTask> findTodoTasksByDate(Date date);
}
