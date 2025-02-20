package com.example.demo.Repository;

import com.example.demo.model.ActivityLogs;
import com.example.demo.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ActivityLogsRepository extends JpaRepository<ActivityLogs,Integer> {
    @Query("SELECT a FROM ActivityLogs a WHERE a.logOwner.id = :id")
    List<ActivityLogs> findLogsOfUser(@Param("id") Integer userId);
}
