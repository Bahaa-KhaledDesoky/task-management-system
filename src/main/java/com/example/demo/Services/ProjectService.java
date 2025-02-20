package com.example.demo.Services;

import com.example.demo.Dtos.ProjectDto;
import com.example.demo.model.AppUser;
import com.example.demo.model.Project;

import java.util.List;

public interface ProjectService {
    public List<Project> allProjects();
    public void addProject(ProjectDto projectDto);
    public ProjectDto GetProject(Integer id);
    public Project GetRealProject(Integer id);
    public void deleteProject(Integer id);
    public void updateProject(Integer id,ProjectDto projectDto);
    public boolean assignUserToProject(Project project, AppUser user);
    public boolean deleteUserfromProject(Project project, AppUser user);
    public Project mapProject(ProjectDto projectDto);
    public ProjectDto mapProjectDto(Project project);
}
