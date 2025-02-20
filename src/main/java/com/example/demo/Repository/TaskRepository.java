package com.example.demo.Repository;

import com.example.demo.model.Role;
import com.example.demo.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task,Integer> {
    @Query("SELECT t FROM Task t WHERE t.assignTo.id = :id")
    List<Task> findByUserAssignTo(@Param("id") Integer userId);
}
