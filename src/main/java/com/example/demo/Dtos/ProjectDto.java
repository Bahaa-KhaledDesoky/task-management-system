package com.example.demo.Dtos;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ProjectDto {

    private String projectname;
    private String description;
    private Date createdAt;
    private Date deadLine;
    private Integer projectOwner ;

}
