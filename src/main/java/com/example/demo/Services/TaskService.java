package com.example.demo.Services;

import com.example.demo.Dtos.TaskDto;
import com.example.demo.model.AppUser;
import com.example.demo.model.Task;

import java.util.List;

public interface TaskService {
    public List<TaskDto> getAllTask();
    public List<TaskDto> getAllTaskOfUser(AppUser user);
    public List<Task> getAllRealTaskOfUser(AppUser user);
    public boolean taskExistforUser(Integer id,AppUser user);
    public void creatTask(TaskDto taskDto);
    public boolean addTask(Task task);
    public void deleteTask(Integer id);
    public void updateTask(Integer id,TaskDto taskDto);
    public TaskDto getTaskById(Integer id);
    public Task getRealTaskById(Integer id);
    public Task mapDtoToTask(TaskDto taskDto);
    public TaskDto mapTaskToDto(Task task);

}
