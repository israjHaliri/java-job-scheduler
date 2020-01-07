package com.haliri.israj.javajobscheduler.repository;

import com.haliri.israj.javajobscheduler.entity.Task;
import com.haliri.israj.javajobscheduler.entity.TaskType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, String> {
    Task findFirstByType(TaskType type);

    List<Task> findTasksByType(TaskType type);
}