package com.example.demo.Services;

import com.example.demo.Dtos.ProjectDto;
import com.example.demo.Repository.ProjectRepository;
import com.example.demo.model.AppUser;
import com.example.demo.model.Project;
import com.example.demo.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class ProjectServiceImp implements ProjectService {
    private final ProjectRepository projectRepository;
    private final UserServiceImp userServiceImp ;
    public List<Project> allProjects(){
      return   projectRepository.findAll();
    }
    public void addProject(ProjectDto projectDto){
        Project project=mapProject(projectDto);
        projectRepository.save(project);
    }
    public ProjectDto GetProject(Integer id){
        Project project=  projectRepository.findById(id).get();
        ProjectDto projectDto=mapProjectDto(project);
        return projectDto;
    }

    @Override
    public Project GetRealProject(Integer id) {
       return projectRepository.findById(id).get();
    }

    public void deleteProject(Integer id)
    {
        Project project=projectRepository.findById(id).get();
        projectRepository.delete(project);
    }

    @Override
    public void updateProject(Integer id, ProjectDto projectDto) {
        Project project=projectRepository.findById(id).get();

        project.setProjectname(projectDto.getProjectname());
        project.setDescription(projectDto.getDescription());
        project.setCreatedAt(projectDto.getCreatedAt());
        project.setDeadLine(projectDto.getDeadLine());
        projectRepository.save(project);
    }

    @Override
    public boolean assignUserToProject(Project project, AppUser user) {
        try {
            if (userServiceImp.projectExistInUser(project,user))
                return false;
            user.getWorkOn().add(project);
            userServiceImp.AddUser(user);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }

    }

    @Override
    public boolean deleteUserfromProject(Project project, AppUser user) {
        try {
            if (!userServiceImp.projectExistInUser(project,user))
                return false;
            user.getRoles().remove(project);
            userServiceImp.AddUser(user);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    public Project mapProject(ProjectDto projectDto)
    {
        return Project.builder()
                .projectOwner(userServiceImp.findUserById(projectDto.getProjectOwner()).get())
                .projectname(projectDto.getProjectname())
                .description(projectDto.getDescription())
                .createdAt(projectDto.getCreatedAt())
                .deadLine(projectDto.getDeadLine())
                .build();
    }
    public ProjectDto mapProjectDto(Project project)
    {
        return ProjectDto.builder()
                .projectOwner(project.getProjectOwner().getId())
                .projectname(project.getProjectname())
                .description(project.getDescription())
                .createdAt(project.getCreatedAt())
                .deadLine(project.getDeadLine())
                .build();
    }

}
