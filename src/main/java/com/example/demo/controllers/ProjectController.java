package com.example.demo.controllers;

import com.example.demo.Dtos.ActivityLogsDto;
import com.example.demo.Dtos.ProjectDto;
import com.example.demo.Services.ActivityLogsServicelmp;
import com.example.demo.Services.ProjectServiceImp;
import com.example.demo.Services.UserServiceImp;
import com.example.demo.model.AppUser;
import com.example.demo.model.Project;
import com.example.demo.security.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectServiceImp projectService;
    private final UserServiceImp userServiceImp;
    private final JwtUtils jwtUtils;
    private final ActivityLogsServicelmp activityLogsServicelmp;
    @GetMapping
    @PreAuthorize("hasRole('ProjectsManager') or hasRole('ADMIN')")
    public List<Project> allProjects()
    {
     return projectService.allProjects();
    }
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    @PreAuthorize("hasRole('ProjectsManager') or hasRole('ADMIN')")
    public void addProject(HttpServletRequest httpServletRequest, @RequestBody ProjectDto projectDto)
    {
        projectService.addProject(projectDto);
        AppUser user=jwtUtils.getUserfromRequest(httpServletRequest);
        ActivityLogsDto activityLogsDto=new ActivityLogsDto(
                "add "+projectDto.getProjectname()+" project",
                "project",
                null ,
                LocalDateTime.now(),
                user.getId());
        activityLogsServicelmp.addLog(activityLogsDto);
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ProjectsManager') or hasRole('ADMIN')")
    public ProjectDto GetProject(@PathVariable Integer id)
    {
        return projectService.GetProject(id);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ProjectsManager') or hasRole('ADMIN')")
    public void deleteProject(HttpServletRequest httpServletRequest,@PathVariable Integer id)
    {
        projectService.deleteProject(id);
        AppUser user=jwtUtils.getUserfromRequest(httpServletRequest);
        ActivityLogsDto activityLogsDto=new ActivityLogsDto(
                "delete project with id "+id,
                "project",
                id ,
                LocalDateTime.now(),
                user.getId());
        activityLogsServicelmp.addLog(activityLogsDto);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ProjectsManager') or hasRole('ADMIN')")
    public void updateProject(HttpServletRequest httpServletRequest,@PathVariable Integer id,@RequestBody ProjectDto projectDto)
    {
        projectService.updateProject(id,projectDto);
        AppUser user=jwtUtils.getUserfromRequest(httpServletRequest);
        ActivityLogsDto activityLogsDto=new ActivityLogsDto(
                "update "+projectDto.getProjectname()+" project",
                "project",
                id ,
                LocalDateTime.now(),
                user.getId());
        activityLogsServicelmp.addLog(activityLogsDto);
    }
    @PostMapping("/addusertoproject/{projectid}/{username}")
    @PreAuthorize("hasRole('ProjectsManager') or hasRole('ADMIN')")
    public ResponseEntity<?> AssignUserToProject(HttpServletRequest httpServletRequest,@PathVariable Integer project_id,@PathVariable String username)
    {
        try {
            Project project=projectService.GetRealProject(project_id);
            AppUser user=userServiceImp.findUserByUserName(username);
            projectService.assignUserToProject(project,user);

            AppUser request_user=jwtUtils.getUserfromRequest(httpServletRequest);
            ActivityLogsDto activityLogsDto=new ActivityLogsDto(
                    "Assign User To Project "+project.getProjectname(),
                    "project",
                    project_id ,
                    LocalDateTime.now(),
                    request_user.getId());
            activityLogsServicelmp.addLog(activityLogsDto);

            return ResponseEntity.ok(user);
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("bad request");
        }

    }
    @DeleteMapping("/deleteUserfromProject/{projectid}/{username}")
    @PreAuthorize("hasRole('ProjectsManager') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteUserFromProject(HttpServletRequest httpServletRequest,@PathVariable Integer project_id,@PathVariable String username)
    {
        try {
            Project project=projectService.GetRealProject(project_id);
            AppUser user=userServiceImp.findUserByUserName(username);
            projectService.deleteUserfromProject(project,user);

            AppUser request_user=jwtUtils.getUserfromRequest(httpServletRequest);
            ActivityLogsDto activityLogsDto=new ActivityLogsDto(
                    "delete User From Project "+project.getProjectname(),
                    "project",
                    project_id ,
                    LocalDateTime.now(),
                    request_user.getId());
            activityLogsServicelmp.addLog(activityLogsDto);

            return ResponseEntity.ok(user);
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("bad request");
        }

    }
}