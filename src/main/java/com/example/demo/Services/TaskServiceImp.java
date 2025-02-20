package com.example.demo.Services;

import com.example.demo.Dtos.TaskDto;
import com.example.demo.Repository.ProjectRepository;
import com.example.demo.Repository.TaskRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.model.AppUser;
import com.example.demo.model.Project;
import com.example.demo.model.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImp implements TaskService{

    private final TaskRepository taskRepository;

    private final UserServiceImp userServiceImp;
    private final ProjectServiceImp projectServiceImp;
    @Override
    public List<TaskDto> getAllTask() {
        List<Task> tasks= taskRepository.findAll();
        return tasks.stream().map((task)->mapTaskToDto(task)).collect(Collectors.toList());
    }

    @Override
    public List<TaskDto> getAllTaskOfUser(AppUser user) {
         return taskRepository.findByUserAssignTo(user.getId())
                .stream().map(task -> mapTaskToDto(task))
                .collect(Collectors.toList());
    }

    @Override
    public List<Task> getAllRealTaskOfUser(AppUser user) {
        return  taskRepository.findByUserAssignTo(user.getId())
                .stream()
                .collect(Collectors.toList());
    }

    @Override
    public boolean taskExistforUser(Integer id, AppUser user) {
        Task task=taskRepository.findById(id).get();
        return getAllRealTaskOfUser(user).contains(task);
    }

    @Override
    public void creatTask(TaskDto taskDto) {
        Task task=mapDtoToTask(taskDto);
        taskRepository.save(task);
    }

    @Override
    public boolean addTask(Task task) {
        try {
            taskRepository.save(task);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    @Override
    public void deleteTask(Integer id) {
        taskRepository.deleteById(id);
    }

    @Override
    public void updateTask(Integer id, TaskDto taskDto) {
        Task task =taskRepository.findById(id).get();

        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setPriority(taskDto.getPriority());
        task.setStatus(taskDto.getStatus());
        task.setProject(projectServiceImp.GetRealProject(taskDto.getProject()));
        task.setAssignTo(userServiceImp.findUserById(taskDto.getAssignTo()).get());
        taskRepository.save(task);

    }

    @Override
    public TaskDto getTaskById(Integer id) {
        Task task=taskRepository.findById(id).get();
        return mapTaskToDto(task);
    }
    @Override
    public Task getRealTaskById(Integer id) {
        return taskRepository.findById(id).get();

    }

    @Override
    public Task mapDtoToTask(TaskDto taskDto) {
        try {
            AppUser user =userServiceImp.findUserById(taskDto.getAssignTo()).get();
            Project project =projectServiceImp.GetRealProject(taskDto.getProject());
            if(user==null||project==null)
            {
                return null;
            }
            return Task.builder()
                    .title(taskDto.getTitle())
                    .description(taskDto.getDescription())
                    .priority(taskDto.getPriority())
                    .assignTo(userServiceImp.findUserById(taskDto.getAssignTo()).get())
                    .project(projectServiceImp.GetRealProject(taskDto.getProject()))
                    .status(taskDto.getStatus())
                    .build();
        }
        catch (Exception e)
        {
            return null;
        }

    }

    @Override
    public TaskDto mapTaskToDto(Task task) {
       return TaskDto.builder()
                .title(task.getTitle())
                .description(task.getDescription())
                .priority(task.getPriority())
                .status(task.getStatus())
                .assignTo(task.getAssignTo().getId())
                .project(task.getProject().getId())
                .build();
    }
}
