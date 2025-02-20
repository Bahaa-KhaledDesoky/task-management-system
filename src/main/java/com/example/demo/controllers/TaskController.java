package com.example.demo.controllers;

import com.example.demo.Dtos.ActivityLogsDto;
import com.example.demo.Dtos.TaskDto;
import com.example.demo.Dtos.TaskDtoforUserUpdate;
import com.example.demo.Services.ActivityLogsServicelmp;
import com.example.demo.Services.TaskService;
import com.example.demo.Services.TaskServiceImp;
import com.example.demo.Services.UserServiceImp;
import com.example.demo.model.AppUser;
import com.example.demo.model.Task;
import com.example.demo.security.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpRequest;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskServiceImp taskService;
    private final UserServiceImp userServiceImp;
    private final JwtUtils jwtUtils;
    private final ActivityLogsServicelmp activityLogsServicelmp;
    @GetMapping
    @PreAuthorize("hasRole('TasksManager') or hasRole('ADMIN')")
    public List<TaskDto> getAllTask()
    {
        return taskService.getAllTask();
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('TasksManager') or hasRole('ADMIN')")
    public TaskDto getTask(@PathVariable Integer id)
    {
        return taskService.getTaskById(id);
    }
    @GetMapping("/taskofuser")
    @PreAuthorize("hasRole('TasksManager') or hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> getTaskOfUser(HttpServletRequest request)
    {
        try {
            String jwt =jwtUtils.getJwFromHeader(request);
            String username=jwtUtils.extractUsername(jwt);
            AppUser user=userServiceImp.findUserByUserName(username);
            return ResponseEntity.ok(taskService.getAllTaskOfUser(user));
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }

    }
    @PostMapping("/userupdatetask/{taskid}")
    @PreAuthorize("hasRole('TasksManager') or hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> updateTaskOfUser(HttpServletRequest request, @PathVariable Integer taskid, @RequestBody TaskDtoforUserUpdate taskUpdate)
    {
        try {
            String jwt =jwtUtils.getJwFromHeader(request);
            String username=jwtUtils.extractUsername(jwt);
            AppUser user=userServiceImp.findUserByUserName(username);
            if (!taskService.taskExistforUser(taskid,user))
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("cant access this task");
            Task task=taskService.getRealTaskById(taskid);
            task.setStatus(taskUpdate.getStatus());
            taskService.addTask(task);

            AppUser request_user=jwtUtils.getUserfromRequest(request);
            ActivityLogsDto activityLogsDto=new ActivityLogsDto(
                    "User update task "+task.getTitle(),
                    "task",
                    taskid ,
                    LocalDateTime.now(),
                    request_user.getId());
            activityLogsServicelmp.addLog(activityLogsDto);


            return ResponseEntity.ok(task);
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }

    }
    @PostMapping
    @PreAuthorize("hasRole('TasksManager') or hasRole('ADMIN')")
    public void creatTask(HttpServletRequest httpServletRequest,@RequestBody TaskDto taskDto) {
        taskService.creatTask(taskDto);

        AppUser request_user=jwtUtils.getUserfromRequest(httpServletRequest);
        ActivityLogsDto activityLogsDto=new ActivityLogsDto(
                "creat task "+taskDto.getTitle(),
                "task",
                null ,
                LocalDateTime.now(),
                request_user.getId());
        activityLogsServicelmp.addLog(activityLogsDto);

    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('TasksManager') or hasRole('ADMIN')")
    public void deleteTask(HttpServletRequest httpServletRequest,@PathVariable Integer id)
    {

        TaskDto task=taskService.getTaskById(id);
        taskService.deleteTask(id);

        AppUser request_user=jwtUtils.getUserfromRequest(httpServletRequest);
        ActivityLogsDto activityLogsDto=new ActivityLogsDto(
                "delete task "+task.getTitle(),
                "task",
                id ,
                LocalDateTime.now(),
                request_user.getId());
        activityLogsServicelmp.addLog(activityLogsDto);

    }
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('TasksManager') or hasRole('ADMIN')")
    public void updateTask(HttpServletRequest httpServletRequest,@PathVariable Integer id,@RequestBody TaskDto taskDto)
    {
        taskService.updateTask(id,taskDto);
        AppUser request_user=jwtUtils.getUserfromRequest(httpServletRequest);
        ActivityLogsDto activityLogsDto=new ActivityLogsDto(
                "update task "+taskDto.getTitle(),
                "task",
                id ,
                LocalDateTime.now(),
                request_user.getId());
        activityLogsServicelmp.addLog(activityLogsDto);

    }
}
